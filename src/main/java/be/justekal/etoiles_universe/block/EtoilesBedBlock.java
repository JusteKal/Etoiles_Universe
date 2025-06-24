package be.justekal.etoiles_universe.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EtoilesBedBlock extends BedBlock {
    public EtoilesBedBlock() {
        super(DyeColor.BLUE, BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).strength(0.2F).noOcclusion());
    }
}