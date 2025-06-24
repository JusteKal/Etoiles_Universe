package be.justekal.etoiles_universe.painting;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTINGS =
        DeferredRegister.create(Registries.PAINTING_VARIANT, EtoilesUniverseMod.MODID);

    public static final RegistryObject<PaintingVariant> ETOIL =
        PAINTINGS.register("etoil",
            () -> new PaintingVariant(16, 16)
        );

    public static final RegistryObject<PaintingVariant> ETOILES_QSMP =
        PAINTINGS.register("etoiles_qsmp",
            () -> new PaintingVariant(16, 32)
        );
}