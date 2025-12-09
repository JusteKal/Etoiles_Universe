package be.justekal.etoiles_universe.item;

import be.justekal.etoiles_universe.effect.ModEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.ChatFormatting;

import java.util.List;

public class DiabetesCureItem extends Item {
    public DiabetesCureItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                if (player.hasEffect(ModEffects.DIABETES.get())) {
                    player.removeEffect(ModEffects.DIABETES.get());
                    player.displayClientMessage(Component.literal("You are cured of diabetes!")
                            .withStyle(ChatFormatting.GOLD), true);
                } else {
                    player.displayClientMessage(Component.literal("You do not have diabetes.")
                            .withStyle(ChatFormatting.YELLOW), true);
                }
            }
            
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.BOW;
    }

    @Override
    public net.minecraft.world.InteractionResultHolder<ItemStack> use(Level level, Player player, 
                                                                        net.minecraft.world.InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return net.minecraft.world.InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Permanently cures diabetes").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Single use").withStyle(ChatFormatting.GRAY));
    }
}
