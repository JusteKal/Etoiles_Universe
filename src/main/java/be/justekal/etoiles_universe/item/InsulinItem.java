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

public class InsulinItem extends Item {
    public InsulinItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                MobEffectInstance diabetesEffect = player.getEffect(ModEffects.DIABETES.get());
                
                if (diabetesEffect != null) {
                    // Réduit l'amplificateur du diabète ou le retire si amplifier = 0
                    int currentAmplifier = diabetesEffect.getAmplifier();
                    
                    if (currentAmplifier > 0) {
                        player.removeEffect(ModEffects.DIABETES.get());
                        player.addEffect(new MobEffectInstance(ModEffects.DIABETES.get(), 
                                Integer.MAX_VALUE, currentAmplifier - 1, false, false, true));
                        player.displayClientMessage(Component.literal("Diabetes treated. Level: " + currentAmplifier)
                                .withStyle(ChatFormatting.GREEN), true);
                    } else {
                        player.displayClientMessage(Component.literal("Diabetes under control")
                                .withStyle(ChatFormatting.GREEN), true);
                    }
                    
                    // Restaure un peu de nourriture
                    player.getFoodData().eat(2, 0.3F);
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
        return 32;
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
        tooltip.add(Component.literal("Treats diabetes").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal("To be taken regularly").withStyle(ChatFormatting.GRAY));
    }
}
