package be.justekal.etoiles_universe.effect;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EtoilesUniverseMod.MODID);

    public static final RegistryObject<MobEffect> DIABETES = MOB_EFFECTS.register("diabetes",
            () -> new DiabetesEffect());

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
