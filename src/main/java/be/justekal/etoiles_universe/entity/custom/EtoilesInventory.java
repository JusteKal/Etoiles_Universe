package be.justekal.etoiles_universe.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EtoilesInventory extends SimpleContainer {
    private final EtoilesEntity entity;
    
    // Slots: 0-4 = équipement (mainhand, offhand, feet, legs, chest, head)
    // Slots 5-31 = inventaire général (27 slots)
    public EtoilesInventory(EtoilesEntity entity) {
        super(32); // 5 équipement + 27 inventaire
        this.entity = entity;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
        // Synchroniser avec les slots d'équipement de l'entité
        if (slot == 0) { // Main hand
            this.entity.setItemSlot(EquipmentSlot.MAINHAND, stack);
        } else if (slot == 1) { // Off hand
            this.entity.setItemSlot(EquipmentSlot.OFFHAND, stack);
        } else if (slot == 2) { // Boots
            this.entity.setItemSlot(EquipmentSlot.FEET, stack);
        } else if (slot == 3) { // Leggings
            this.entity.setItemSlot(EquipmentSlot.LEGS, stack);
        } else if (slot == 4) { // Chestplate
            this.entity.setItemSlot(EquipmentSlot.CHEST, stack);
        } else if (slot == 5) { // Helmet
            this.entity.setItemSlot(EquipmentSlot.HEAD, stack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.entity.isAlive() && this.entity.distanceTo(player) < 8.0F;
    }

    public void readFromNBT(CompoundTag tag) {
        this.fromTag(tag.getList("Items", 10));
        // Synchroniser après le chargement
        syncEquipmentToEntity();
    }

    public void writeToNBT(CompoundTag tag) {
        tag.put("Items", this.createTag());
    }
    
    public void syncEquipmentToEntity() {
        this.entity.setItemSlot(EquipmentSlot.MAINHAND, this.getItem(0));
        this.entity.setItemSlot(EquipmentSlot.OFFHAND, this.getItem(1));
        this.entity.setItemSlot(EquipmentSlot.FEET, this.getItem(2));
        this.entity.setItemSlot(EquipmentSlot.LEGS, this.getItem(3));
        this.entity.setItemSlot(EquipmentSlot.CHEST, this.getItem(4));
        this.entity.setItemSlot(EquipmentSlot.HEAD, this.getItem(5));
    }
}
