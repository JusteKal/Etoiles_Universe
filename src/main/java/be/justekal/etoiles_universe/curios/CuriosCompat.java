package be.justekal.etoiles_universe.curios;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod.EventBusSubscriber(modid = EtoilesUniverseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosCompat {

    @SubscribeEvent
    public static void enqueueIMC(InterModEnqueueEvent event) {
        // Enregistre le slot necklace
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
            () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
    }
}