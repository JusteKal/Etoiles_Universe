package be.justekal.etoiles_universe.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EtoilesInventory extends SimpleContainer {
    private final EtoilesEntity entity;
    
    // Layout exact comme joueur:
    // Slots 0-35: Inventaire principal (9 hotbar + 27 main inventory)
    // Slots 36-39: Armor (boots, leggings, chest, head)
    // Slot 40: Off hand
    public EtoilesInventory(EtoilesEntity entity) {
        super(41); // 36 inventory + 4 armor + 1 offhand
        this.entity = entity;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack oldStack = this.getItem(slot);
        super.setItem(slot, stack);
        
        // Synchroniser avec les slots d'équipement de l'entité
        if (entity != null) {
            if (slot == 36) { // Boots
                this.entity.setItemSlot(EquipmentSlot.FEET, stack);
            } else if (slot == 37) { // Leggings
                this.entity.setItemSlot(EquipmentSlot.LEGS, stack);
            } else if (slot == 38) { // Chestplate
                this.entity.setItemSlot(EquipmentSlot.CHEST, stack);
            } else if (slot == 39) { // Helmet
                this.entity.setItemSlot(EquipmentSlot.HEAD, stack);
            } else if (slot == 40) { // Off hand
                this.entity.setItemSlot(EquipmentSlot.OFFHAND, stack);
            } else if (slot == 0) { // Premier slot hotbar = main hand
                this.entity.setItemSlot(EquipmentSlot.MAINHAND, stack);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.entity != null && this.entity.isAlive() && this.entity.distanceTo(player) < 8.0F;
    }

    public void fromTag(ListTag listTag) {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int slot = compoundTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.getContainerSize()) {
                ItemStack stack = ItemStack.of(compoundTag);
                this.setItem(slot, stack);
            }
        }
    }

    public ListTag createTag() {
        ListTag listTag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)i);
                itemStack.save(compoundTag);
                listTag.add(compoundTag);
            }
        }

        return listTag;
    }

    public void readFromNBT(CompoundTag tag) {
        ListTag listTag = tag.getList("Items", 10);
        this.fromTag(listTag);
        // Synchroniser après le chargement
        if (entity != null) {
            syncEquipmentToEntity();
        }
    }

    public void writeToNBT(CompoundTag tag) {
        tag.put("Items", this.createTag());
    }
    
    public void syncEquipmentToEntity() {
        if (entity == null) return;
        this.entity.setItemSlot(EquipmentSlot.FEET, this.getItem(36));
        this.entity.setItemSlot(EquipmentSlot.LEGS, this.getItem(37));
        this.entity.setItemSlot(EquipmentSlot.CHEST, this.getItem(38));
        this.entity.setItemSlot(EquipmentSlot.HEAD, this.getItem(39));
        this.entity.setItemSlot(EquipmentSlot.OFFHAND, this.getItem(40));
        this.entity.setItemSlot(EquipmentSlot.MAINHAND, this.getItem(0));
    }
}
