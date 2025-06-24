package be.justekal.etoiles_universe.block.entity;

import be.justekal.etoiles_universe.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "etoiles_universe");

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> CUCUMBER_SIGN =
            BLOCK_ENTITIES.register("cucumber_sign",
                    () -> BlockEntityType.Builder.of(
                            ModSignBlockEntity::new,
                            ModBlocks.CUCUMBER_SIGN.get(),
                            ModBlocks.CUCUMBER_WALL_SIGN.get()
                    ).build(null));
}