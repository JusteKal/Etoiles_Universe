package be.justekal.etoiles_universe.block;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.block.custom.EtoilesBedBlock;
import be.justekal.etoiles_universe.block.custom.ModStandingSignBlock;
import be.justekal.etoiles_universe.block.custom.ModWallSignBlock;
import be.justekal.etoiles_universe.block.custom.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Blocks;

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
                if (player.getMainHandItem().getItem() instanceof AxeItem) {
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
        () -> new SlabBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get())
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

    public static final RegistryObject<Block> CUCUMBER_STAIRS = BLOCKS.register("cucumber_stairs",
        () -> new StairBlock(() -> CUCUMBER_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get())
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
        @Override
        public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
            if (player.getMainHandItem().getItem() instanceof AxeItem) {
                return 1.5F; // Plus rapide avec une hache
            }
            return super.getDestroyProgress(state, player, level, pos);
        }
    }
);

    public static final RegistryObject<Block> CUCUMBER_FENCE = BLOCKS.register("cucumber_fence",
        () -> new FenceBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get())
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

    public static final RegistryObject<Block> CUCUMBER_FENCE_GATE = BLOCKS.register("cucumber_fence_gate",
        () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CUCUMBER_PLANKS.get()).noOcclusion(), ModWoodTypes.CUCUMBER)
        {
            @Override
            public float getDestroyProgress(net.minecraft.world.level.block.state.BlockState state, Player player, BlockGetter level, BlockPos pos) {
                if (player.getMainHandItem().getItem() instanceof AxeItem) {
                    return 1.5F;
                }
                return super.getDestroyProgress(state, player, level, pos);
            }
        }
    );

    public static final RegistryObject<Block> CUCUMBER_SIGN = BLOCKS.register("cucumber_sign",
    () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.CUCUMBER));

    public static final RegistryObject<Block> CUCUMBER_WALL_SIGN = BLOCKS.register("cucumber_wall_sign",
    () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.CUCUMBER));

    //registre ma etoiles_plush
    public static final RegistryObject<Block> ETOILES_PLUSH = BLOCKS.register(
        "etoiles_plush",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).strength(0.5f).noOcclusion()) {
            public static final net.minecraft.world.level.block.state.properties.DirectionProperty FACING =
                net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

            @Override
            protected void createBlockStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(FACING);
            }

            @Override
            public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
                // Hitbox centrée, 0.25 à 0.75 sur X/Z, 0 à 0.5 sur Y
                return Shapes.box(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
            }

            @Override
            public BlockState getStateForPlacement(BlockPlaceContext context) {
                // Pour que la peluche regarde vers le joueur
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
            }
        }
    );

    //registre ma etoiles_statue
    public static final RegistryObject<Block> ETOILES_STATUE = BLOCKS.register(
        "etoiles_statue",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2.0f).noOcclusion()) {
            public static final net.minecraft.world.level.block.state.properties.DirectionProperty FACING =
                net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

            @Override
            protected void createBlockStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(FACING);
            }

            @Override
            public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
                // Hitbox pour une statue : 1 bloc de large (16 pixels) sur 2 blocs de haut (32 pixels)
                return Shapes.box(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);
            }

            @Override
            public BlockState getStateForPlacement(BlockPlaceContext context) {
                // Pour que la statue regarde vers le joueur
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
            }

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

    // Etoiles Bed
    public static final RegistryObject<Block> ETOILES_BED = BLOCKS.register("etoiles_bed",
        () -> new EtoilesBedBlock(
            BlockBehaviour.Properties.copy(Blocks.RED_BED).sound(net.minecraft.world.level.block.SoundType.WOOD))
    );

}

