package be.justekal.etoiles_universe.events;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.effect.ModEffects;
import be.justekal.etoiles_universe.gamerule.ModGameRules;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EtoilesUniverseMod.MODID)
public class DiabetesEventHandler {
    
    private static final float SUGAR_THRESHOLD = 0.3F; // Seuil pour considérer un aliment sucré
    
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            // Vérifie si la gamerule est activée
            if (!player.level().getGameRules().getBoolean(ModGameRules.ENABLE_DIABETES)) {
                return;
            }
            
            ItemStack itemStack = event.getItem();
            FoodProperties food = itemStack.getFoodProperties(player);
            
            if (food != null) {
                // Détecte les aliments sucrés (saturation élevée)
                float saturation = food.getSaturationModifier();
                
                if (saturation >= SUGAR_THRESHOLD) {
                    // 15% de chance de développer/aggraver le diabète
                    if (player.getRandom().nextFloat() < 0.15F) {
                        MobEffectInstance currentEffect = player.getEffect(ModEffects.DIABETES.get());
                        
                        if (currentEffect == null) {
                            // Premier niveau de diabète
                            player.addEffect(new MobEffectInstance(ModEffects.DIABETES.get(), 
                                    Integer.MAX_VALUE, 0, false, false, true));
                            player.displayClientMessage(Component.literal("Vous ressentez une fatigue inhabituelle...")
                                    .withStyle(ChatFormatting.RED), true);
                        } else {
                            // Aggrave le diabète (max niveau 5)
                            int newAmplifier = Math.min(currentEffect.getAmplifier() + 1, 5);
                            player.removeEffect(ModEffects.DIABETES.get());
                            player.addEffect(new MobEffectInstance(ModEffects.DIABETES.get(), 
                                    Integer.MAX_VALUE, newAmplifier, false, false, true));
                            
                            if (newAmplifier > currentEffect.getAmplifier()) {
                                player.displayClientMessage(Component.literal("Votre diabète s'aggrave ! Niveau: " + (newAmplifier + 1))
                                        .withStyle(ChatFormatting.DARK_RED), true);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        
        // Vérifie si la gamerule est activée
        if (!player.level().isClientSide && player.level().getGameRules().getBoolean(ModGameRules.ENABLE_DIABETES)) {
            if (player.hasEffect(ModEffects.DIABETES.get())) {
                MobEffectInstance diabetesEffect = player.getEffect(ModEffects.DIABETES.get());
            
                if (diabetesEffect != null) {
                    // Aggrave le diabète chaque jour si non traité
                    int currentAmplifier = diabetesEffect.getAmplifier();
                    int newAmplifier = Math.min(currentAmplifier + 1, 5);
                    
                    player.removeEffect(ModEffects.DIABETES.get());
                    player.addEffect(new MobEffectInstance(ModEffects.DIABETES.get(), 
                            Integer.MAX_VALUE, newAmplifier, false, false, true));
                    
                    player.displayClientMessage(Component.literal("Vous devez prendre votre insuline ! Niveau: " + (newAmplifier + 1))
                            .withStyle(ChatFormatting.RED), false);
                }
            }
        }
    }
}
