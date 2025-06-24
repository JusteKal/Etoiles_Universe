package be.justekal.etoiles_universe.paint;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "etoiles_universe")
public class PaintingPlaceHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack heldItem = event.getItemStack();

        // Vérifie si le joueur tient une peinture vanilla
        if (heldItem.getItem() == Items.PAINTING && !level.isClientSide) {
            BlockPos pos = event.getPos().relative(event.getFace());
            BlockState state = level.getBlockState(pos);

            // Récupère tous les variants vanilla + ceux de ton mod
            List<PaintingVariant> allVariants = new ArrayList<>();
            for (PaintingVariant variant : ForgeRegistries.PAINTING_VARIANTS) {
                // Ajoute seulement les variants de ton mod ou vanilla
                String namespace = ForgeRegistries.PAINTING_VARIANTS.getKey(variant).getNamespace();
                if (namespace.equals("minecraft") || namespace.equals("etoiles_universe")) {
                    allVariants.add(variant);
                }
            }

            // Sélectionne un variant aléatoire compatible avec la place disponible
            PaintingVariant chosen = null;
            Random rand = new Random();
            for (int i = 0; i < allVariants.size(); i++) {
                PaintingVariant candidate = allVariants.get(rand.nextInt(allVariants.size()));
                var holder = ForgeRegistries.PAINTING_VARIANTS.getHolder(candidate).orElseThrow();
                Painting painting = new Painting(level, pos, event.getFace(), holder);
                if (painting.survives()) {
                    chosen = candidate;
                    break;
                }
            }

                var chosenHolder = ForgeRegistries.PAINTING_VARIANTS.getHolder(chosen).orElseThrow();
                Painting painting = new Painting(level, pos, event.getFace(), chosenHolder);
                level.addFreshEntity(painting);
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
