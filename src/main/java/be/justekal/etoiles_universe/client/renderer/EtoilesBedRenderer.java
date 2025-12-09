package be.justekal.etoiles_universe.client.renderer;

import be.justekal.etoiles_universe.EtoilesUniverseMod;
import be.justekal.etoiles_universe.block.entity.EtoilesBedBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayers;

@OnlyIn(Dist.CLIENT)
public class EtoilesBedRenderer implements BlockEntityRenderer<EtoilesBedBlockEntity> {
    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public EtoilesBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(ModelLayers.BED_HEAD);
        this.footRoot = context.bakeLayer(ModelLayers.BED_FOOT);
    }

    @Override
    public void render(EtoilesBedBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Material material = new Material(Sheets.BED_SHEET, 
            new ResourceLocation(EtoilesUniverseMod.MODID, "entity/bed/etoiles"));
        
        BlockState blockState = blockEntity.getBlockState();
        if (blockState.getBlock() instanceof BedBlock) {
            poseStack.pushPose();
            Direction direction = blockState.getValue(BedBlock.FACING);
            poseStack.translate(0.0D, 0.5625D, 0.0D);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0.5D, 0.5D, 0.5D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
            poseStack.translate(-0.5D, -0.5D, -0.5D);

            BedPart bedPart = blockState.getValue(BedBlock.PART);
            ModelPart modelPart = bedPart == BedPart.HEAD ? this.headRoot : this.footRoot;
            
            VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entitySolid);
            modelPart.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            
            poseStack.popPose();
        }
    }
}
