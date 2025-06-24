package be.justekal.etoiles_universe.block.custom;

import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import be.justekal.etoiles_universe.EtoilesUniverseMod;

public class ModWoodTypes {
    public static final WoodType CUCUMBER = WoodType.register(new WoodType(EtoilesUniverseMod.MODID + ":cucumber", BlockSetType.OAK));
}