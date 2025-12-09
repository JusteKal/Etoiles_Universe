package be.justekal.etoiles_universe.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EtoilesBedBlockEntity extends BedBlockEntity {
    
    public EtoilesBedBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.ETOILES_BED.get();
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.BLUE;
    }
}
