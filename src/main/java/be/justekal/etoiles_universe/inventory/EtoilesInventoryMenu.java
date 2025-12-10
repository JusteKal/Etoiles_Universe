package be.justekal.etoiles_universe.inventory;

import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EtoilesInventoryMenu extends AbstractContainerMenu {
    private final Container etoilesInventory;
    private final EtoilesEntity entity;

    public EtoilesInventoryMenu(int containerId, Inventory playerInventory, Container etoilesInventory, EtoilesEntity entity) {
        super(MenuType.GENERIC_9x3, containerId);
        this.etoilesInventory = etoilesInventory;
        this.entity = entity;
        
        etoilesInventory.startOpen(playerInventory.player);

        // Slot 0: Main Hand (position gauche, haut)
        this.addSlot(new Slot(etoilesInventory, 0, 8, 18) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        
        // Slot 1: Off Hand (position droite, haut)
        this.addSlot(new Slot(etoilesInventory, 1, 26, 18) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        
        // Slot 2: Boots
        this.addSlot(new Slot(etoilesInventory, 2, 8, 54) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        
        // Slot 3: Leggings
        this.addSlot(new Slot(etoilesInventory, 3, 8, 36) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        
        // Slot 4: Chestplate
        this.addSlot(new Slot(etoilesInventory, 4, 26, 36) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        
        // Slot 5: Helmet
        this.addSlot(new Slot(etoilesInventory, 5, 26, 54) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        // Inventaire général de l'entité (3 lignes de 7 slots à droite)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 7; ++col) {
                this.addSlot(new Slot(etoilesInventory, 6 + col + row * 7, 62 + col * 18, 18 + row * 18));
            }
        }

        // Inventaire du joueur (3 lignes)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 103 + row * 18 - 18));
            }
        }

        // Hotbar du joueur
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 161 - 18));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            
            if (index < 27) {
                // Du conteneur de l'entité vers l'inventaire du joueur
                if (!this.moveItemStackTo(slotStack, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // De l'inventaire du joueur vers le conteneur de l'entité
                if (!this.moveItemStackTo(slotStack, 6, 27, false)) {
                    return ItemStack.EMPTY;
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
}
