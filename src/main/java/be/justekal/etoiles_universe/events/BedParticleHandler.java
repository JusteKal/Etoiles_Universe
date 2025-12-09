package be.justekal.etoiles_universe.events;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EtoilesUniverseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BedParticleHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state.getBlock() == ModBlocks.ETOILES_BED.get()) {
            BlockPos pos = event.getPos();
            BlockState particleState = ModBlocks.CUCUMBER_PLANKS.get().defaultBlockState();
            
            // Génère des particules de destruction côté client
            if (event.getLevel().isClientSide() && Minecraft.getInstance().level != null) {
                for (int i = 0; i < 30; i++) {
                    double offsetX = Minecraft.getInstance().level.random.nextDouble();
                    double offsetY = Minecraft.getInstance().level.random.nextDouble();
                    double offsetZ = Minecraft.getInstance().level.random.nextDouble();
                    
                    double x = pos.getX() + offsetX;
                    double y = pos.getY() + offsetY;
                    double z = pos.getZ() + offsetZ;
                    
                    double vx = (offsetX - 0.5) * 0.1;
                    double vy = offsetY * 0.1;
                    double vz = (offsetZ - 0.5) * 0.1;
                    
                    Minecraft.getInstance().level.addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, particleState),
                        x, y, z, vx, vy, vz
                    );
                }
            }
        }
    }
}
