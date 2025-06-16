package be.justekal.etoiles_universe.sounds;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EtoilesUniverseMod.MODID);

    public static final RegistryObject<SoundEvent> DIAMONDSETOILES =
        SOUNDS.register("diamondsetoiles", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "diamondsetoiles")));

    public static final RegistryObject<SoundEvent> ETOILES_VAMOS_A_LA_PLAYA =
        SOUNDS.register("etoiles_vamos_a_la_playa", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "etoiles_vamos_a_la_playa")));

    public static final RegistryObject<SoundEvent> REFLET_VIADUC =
        SOUNDS.register("reflet_viaduc", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "reflet_viaduc")));

    public static final RegistryObject<SoundEvent> ETOILES_SHAKE_IT_OFF =
        SOUNDS.register("etoiles_shake_it_off", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "etoiles_shake_it_off")));

    public static final RegistryObject<SoundEvent> JE_NE_REGRETTE_RIEN =
        SOUNDS.register("je_ne_regrette_rien", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "je_ne_regrette_rien")));

    public static final RegistryObject<SoundEvent> AINSI_FONT_LES_MARIONETTES =
        SOUNDS.register("ainsi_font_les_marionettes", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "ainsi_font_les_marionettes")));

    public static final RegistryObject<SoundEvent> JOYEUX_ANNIVERSAIRE =
        SOUNDS.register("joyeux_anniversaire", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "joyeux_anniversaire")));

    public static final RegistryObject<SoundEvent> RICKS_ROLL =
        SOUNDS.register("ricks_roll", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(EtoilesUniverseMod.MODID, "ricks_roll")));
}