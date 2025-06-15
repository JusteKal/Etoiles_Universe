package be.justekal.etoiles_universe.block;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EtoilesUniverseMod.MODID);

    public static final RegistryObject<Block> CUCUMBER_BLOCK = BLOCKS.register("cucumber_block",
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.6f)
            .sound(net.minecraft.world.level.block.SoundType.SLIME_BLOCK)
            .isValidSpawn((state, getter, pos, entity) -> false)
            .isRedstoneConductor((state, getter, pos) -> false)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
        ) {
            @Override
            public float getDestroyProgress(net.minecraft.world.level.block.state.BlockState state, Player player, BlockGetter level, BlockPos pos) {
                if (player.getMainHandItem().getItem() instanceof HoeItem) {
                    return 1.5F;
                }
                return super.getDestroyProgress(state, player, level, pos);
            }
        }
    );
    public static final RegistryObject<Block> CUCUMBER_PLANKS = BLOCKS.register("cucumber_planks",
        () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(2.0f, 3.0f)
            .sound(net.minecraft.world.level.block.SoundType.WOOD)
            .isValidSpawn((state, getter, pos, entity) -> false)
            .isRedstoneConductor((state, getter, pos) -> false)
            .isSuffocating((state, getter, pos) -> false)
            .isViewBlocking((state, getter, pos) -> false)
        ) {
            @Override
            public float getDestroyProgress(net.minecraft.world.level.block.state.BlockState state, Player player, BlockGetter level, BlockPos pos) {
                if (player.getMainHandItem().getItem() instanceof AxeItem) {
                    return 1.5F;
                }
                return super.getDestroyProgress(state, player, level, pos);
            }
        }
    );
    public static final RegistryObject<Block> CUCUMBER_SLAB = BLOCKS.register("cucumber_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()))
    );

    public static final RegistryObject<Block> CUCUMBER_STAIRS = BLOCKS.register("cucumber_stairs",
        () -> new StairBlock(() -> CUCUMBER_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()))
    );

public static final RegistryObject<Block> CUCUMBER_DOOR = BLOCKS.register("cucumber_door",
    () -> new DoorBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()).noOcclusion(), BlockSetType.OAK) {
        @Override
        public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
            return 1.0F;
        }
        @Override
        public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
            return true;
        }
    }
);

    public static final RegistryObject<Block> CUCUMBER_FENCE = BLOCKS.register("cucumber_fence",
        () -> new FenceBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()))
    );

    public static final RegistryObject<Block> CUCUMBER_FENCE_GATE = BLOCKS.register("cucumber_fence_gate",
        () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()).noOcclusion(), WoodType.OAK)
    );
}
