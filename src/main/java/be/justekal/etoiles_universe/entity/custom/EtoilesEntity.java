package be.justekal.etoiles_universe.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkHooks;
import be.justekal.etoiles_universe.inventory.EtoilesInventoryMenu;

public class EtoilesEntity extends TamableAnimal {
    private final EtoilesInventory inventory = new EtoilesInventory(this);
    
    // Indicateur pour la pose assise
    public boolean isReallySitting() {
        return this.isOrderedToSit();
    }
    public EtoilesEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Override
    public EtoilesEntity getBreedOffspring(ServerLevel serverLevel, net.minecraft.world.entity.AgeableMob ageableMob) {
        return (EtoilesEntity) this.getType().create(serverLevel);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 8.0F, 4.0F, true)); // Distance max: 16 blocs, distance min: 4 blocs
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2D, true)); // AJOUT ICI
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // Tame avec un diamant
        if (!this.isTame() && itemstack.is(Items.DIAMOND)) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (!this.level().isClientSide) {
                if (this.random.nextInt(3) == 0) { // 1 chance sur 3
                    this.tame(player);
                    this.setOrderedToSit(true); // S'assoit après tame
                    this.level().broadcastEntityEvent(this, (byte)7); // coeur
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6); // fumée
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // SHIFT + CLIC : Ouvrir l'inventaire (PRIORITÉ ABSOLUE pour les entités apprivoisées)
        if (this.isTame() && this.isOwnedBy(player) && player.isShiftKeyDown()) {
            if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("container.etoiles_universe.etoiles_inventory");
                    }
                    
                    @Override
                    public net.minecraft.world.inventory.AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory playerInventory, Player player) {
                        return new EtoilesInventoryMenu(containerId, playerInventory, inventory, EtoilesEntity.this);
                    }
                }, buf -> buf.writeInt(this.getId()));
            }
            return InteractionResult.SUCCESS;
        }

        // --- Donner une armure ou une arme (SEULEMENT si pas shift) ---
        if (this.isTame() && this.isOwnedBy(player) && !itemstack.isEmpty() && !player.isShiftKeyDown()) {
            // ARMURE (plastron)
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.CHEST, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, itemstack.copyWithCount(1));
                this.inventory.setItem(38, itemstack.copyWithCount(1)); // Sync inventory - slot 38
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // ARME (main hand)
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.MAINHAND, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, itemstack.copyWithCount(1));
                this.inventory.setItem(0, itemstack.copyWithCount(1)); // Sync inventory - slot 0 (hotbar)
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // CASQUE
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.HEAD, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, itemstack.copyWithCount(1));
                this.inventory.setItem(39, itemstack.copyWithCount(1)); // Sync inventory - slot 39
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // Pantalon
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.LEGS, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, itemstack.copyWithCount(1));
                this.inventory.setItem(37, itemstack.copyWithCount(1)); // Sync inventory - slot 37
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // Bottes
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.FEET, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, itemstack.copyWithCount(1));
                this.inventory.setItem(36, itemstack.copyWithCount(1)); // Sync inventory - slot 36
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        // Si déjà tame et clic droit à main nue ou avec un diamant : toggle assis/suit
        if (this.isTame() && this.isOwnedBy(player) && (itemstack.isEmpty() || itemstack.is(Items.DIAMOND)) && !player.isShiftKeyDown()) {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }
    
    @Override
    public void baseTick() {
        super.baseTick();
        // Force la pose après tous les autres systèmes
        if (!this.level().isClientSide) {
            if (this.isOrderedToSit()) {
                this.setPose(net.minecraft.world.entity.Pose.SITTING);
            } else {
                this.setPose(net.minecraft.world.entity.Pose.STANDING);
            }
        }
    }
    
    @Override
    public void aiStep() {
        super.aiStep();
        // Arrête la navigation quand assis
        if (this.isOrderedToSit()) {
            this.getNavigation().stop();
        }
        // Force aussi côté client
        if (this.level().isClientSide) {
            if (this.isOrderedToSit()) {
                this.setPose(net.minecraft.world.entity.Pose.SITTING);
            }
        }
    }

    @Override
    public boolean isInSittingPose() {
        return this.isOrderedToSit();
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.inventory.writeToNBT(tag);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.inventory.readFromNBT(tag);
    }
    
    public EtoilesInventory getInventory() {
        return this.inventory;
    }
}