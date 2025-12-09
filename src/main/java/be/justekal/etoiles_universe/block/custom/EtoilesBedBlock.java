package be.justekal.etoiles_universe.block.custom;

import be.justekal.etoiles_universe.block.ModBlocks;
import be.justekal.etoiles_universe.block.entity.EtoilesBedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EtoilesBedBlock extends BedBlock {
    
    public EtoilesBedBlock(Properties properties) {
        super(DyeColor.GREEN, properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EtoilesBedBlockEntity(pos, state);
    }
}
