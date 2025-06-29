package be.justekal.etoiles_universe.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class EtoilesNecklaceItem extends Item implements ICurioItem {
    
    public EtoilesNecklaceItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // Effet quand le collier est porté
        LivingEntity wearer = slotContext.entity();
        if (wearer instanceof Player player) {
            // Donne l'effet Luck infini
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 200, 255, false, false, false));
        }
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        tooltips.add(Component.translatable("item.etoiles_universe.etoiles_necklace.tooltip").withStyle(ChatFormatting.GREEN));
        return tooltips;
    }
}