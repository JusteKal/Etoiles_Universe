package be.justekal.etoiles_universe.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;

public class DiabetesEffect extends MobEffect {
    public DiabetesEffect() {
        super(MobEffectCategory.HARMFUL, 0xE9967A);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Effet de fatigue progressive
            if (player.level().getGameTime() % 100 == 0) {
                player.getFoodData().addExhaustion(0.5F);
            }
            
            // Dégâts si non traité pendant longtemps (amplifier > 2)
            if (amplifier > 2 && player.level().getGameTime() % 200 == 0) {
                player.hurt(player.damageSources().magic(), 1.0F);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
