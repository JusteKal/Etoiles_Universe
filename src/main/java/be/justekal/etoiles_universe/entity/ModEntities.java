package be.justekal.etoiles_universe.entity;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EtoilesUniverseMod.MODID);

    public static final RegistryObject<EntityType<EtoilesEntity>> ETOILES =
        ENTITIES.register("etoiles",
            () -> EntityType.Builder.of(EtoilesEntity::new, MobCategory.CREATURE)
                .sized(0.6F, 1.8F) // taille d'un joueur
                .build("etoiles"));
}