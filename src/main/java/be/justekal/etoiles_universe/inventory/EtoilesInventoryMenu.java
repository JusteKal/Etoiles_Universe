package be.justekal.etoiles_universe.inventory;

import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import be.justekal.etoiles_universe.screen.ModMenuTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;

public class EtoilesInventoryMenu extends AbstractContainerMenu {
    private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[]{
        InventoryMenu.EMPTY_ARMOR_SLOT_HELMET,
        InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
        InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
        InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS
    };
    
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{
        EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };
    
    private final Container etoilesInventory;
    private final EtoilesEntity entity;

    // Constructeur pour le client (appelé depuis le réseau)
    public EtoilesInventoryMenu(int containerId, Inventory playerInventory, net.minecraft.network.FriendlyByteBuf extraData) {
        this(containerId, playerInventory, new be.justekal.etoiles_universe.entity.custom.EtoilesInventory(null), 
             (EtoilesEntity) playerInventory.player.level().getEntity(extraData.readInt()));
    }

    // Constructeur pour le serveur (appelé directement)
    public EtoilesInventoryMenu(int containerId, Inventory playerInventory, Container etoilesInventory, EtoilesEntity entity) {
        super(ModMenuTypes.ETOILES_INVENTORY_MENU.get(), containerId);
        this.etoilesInventory = etoilesInventory;
        this.entity = entity;
        
        etoilesInventory.startOpen(playerInventory.player);

        // Slots d'armure (39, 38, 37, 36) - De haut en bas: helmet, chest, legs, boots
        for (int i = 0; i < 4; ++i) {
            final int armorIndex = i;
            final EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
            this.addSlot(new Slot(etoilesInventory, 39 - i, 8, 8 + i * 18) {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }
                
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.canEquip(equipmentSlot, entity);
                }
                
                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[armorIndex]);
                }
            });
        }

        // Slot off hand (40)
        this.addSlot(new Slot(etoilesInventory, 40, 77, 62) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        // Inventaire principal de l'entité - 3 lignes de 9 (slots 9-35)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(etoilesInventory, 9 + col + row * 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar de l'entité (slots 0-8)
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(etoilesInventory, col, 8 + col * 18, 142));
        }

        // Inventaire du joueur (3 lignes)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 174 + row * 18));
            }
        }

        // Hotbar du joueur
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 232));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            
            int entityInvSize = 41; // Taille totale inventaire entité
            
            if (index < entityInvSize) {
                // Du conteneur de l'entité vers l'inventaire du joueur
                if (!this.moveItemStackTo(slotStack, entityInvSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // De l'inventaire du joueur vers le conteneur de l'entité
                // Essayer d'abord les slots d'armure appropriés
                if (slotStack.getItem() instanceof ArmorItem armorItem) {
                    int armorSlot = 39 - armorItem.getEquipmentSlot().getIndex();
                    if (!this.moveItemStackTo(slotStack, armorSlot, armorSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // Sinon dans l'inventaire principal
                    if (!this.moveItemStackTo(slotStack, 5, entityInvSize, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.etoilesInventory.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.etoilesInventory.stopOpen(player);
    }
    
    public EtoilesEntity getEntity() {
        return this.entity;
    }
}
