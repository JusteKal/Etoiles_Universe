package be.justekal.etoiles_universe.events;

import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import be.justekal.etoiles_universe.block.ModBlocks;
import be.justekal.etoiles_universe.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "etoiles_universe")
public class CommonEvents {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BlockPos pos = event.getPos();
        BlockState placed = event.getPlacedBlock();

        // Vérifie si la citrouille vient d'être posée
        if (placed.is(Blocks.CARVED_PUMPKIN)) {
            BlockPos below = pos.below();
            BlockPos below2 = below.below();
            BlockState stateBelow = level.getBlockState(below);
            BlockState stateBelow2 = level.getBlockState(below2);

            // Vérifie la structure: 2 cucumber blocks sous la citrouille
            if (stateBelow.is(ModBlocks.CUCUMBER_BLOCK.get()) && stateBelow2.is(ModBlocks.CUCUMBER_BLOCK.get())) {
                // Retire les blocs
                level.removeBlock(pos, false);
                level.removeBlock(below, false);
                level.removeBlock(below2, false);

                // Invoque Etoiles
                EtoilesEntity etoiles = ModEntities.ETOILES.get().create(level);
                if (etoiles != null) {
                    etoiles.moveTo(below2.getX() + 0.5, below2.getY(), below2.getZ() + 0.5, 0, 0);
                    level.addFreshEntity(etoiles);

                        level.sendParticles(
                        net.minecraft.core.particles.ParticleTypes.EXPLOSION,
                        etoiles.getX(), etoiles.getY() + 1, etoiles.getZ(),
                        10, 0.3, 0.5, 0.3, 0.01
                    );

                    level.playSound(
                        null,
                        etoiles.blockPosition(),
                        net.minecraft.sounds.SoundEvents.TOTEM_USE,
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        1.0F, 1.0F
                
                    );
                }
            }
        }
    }
}