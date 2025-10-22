package be.justekal.etoiles_universe.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class EtoilesEntity extends TamableAnimal {
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
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 5.0F, 2.0F, false));
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

        // Si déjà tame et clic droit à main nue ou avec un diamant : toggle assis/suit
        if (this.isTame() && this.isOwnedBy(player) && (itemstack.isEmpty() || itemstack.is(Items.DIAMOND))) {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // --- Donner une armure ou une arme ---
        if (this.isTame() && this.isOwnedBy(player) && !itemstack.isEmpty()) {
            // ARMURE (plastron)
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.CHEST, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, itemstack.copyWithCount(1));
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // ARME (main hand)
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.MAINHAND, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, itemstack.copyWithCount(1));
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // CASQUE
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.HEAD, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, itemstack.copyWithCount(1));
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // Pantalon
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.LEGS, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, itemstack.copyWithCount(1));
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            // Bottes
            if (itemstack.getItem().canEquip(itemstack, net.minecraft.world.entity.EquipmentSlot.FEET, this)) {
                ItemStack old = this.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET);
                this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, itemstack.copyWithCount(1));
                if (!player.getAbilities().instabuild) itemstack.shrink(1);
                if (!old.isEmpty()) player.addItem(old);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(player, hand);
    }
    @Override
    public void aiStep() {
        super.aiStep();
        // La pose est gérée côté renderer, donc ici on ne force pas la pose
    }
}