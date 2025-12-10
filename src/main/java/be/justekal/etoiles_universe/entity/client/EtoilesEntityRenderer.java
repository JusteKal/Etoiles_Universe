package be.justekal.etoiles_universe.entity.client;

import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;

public class EtoilesEntityRenderer extends HumanoidMobRenderer<EtoilesEntity, HumanoidModel<EtoilesEntity>> {
    public EtoilesEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM)), 0.5f);
        // Ajout du layer d'armure pour afficher l'armure sur l'entité
        this.addLayer(new HumanoidArmorLayer<>(this,
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),
            context.getModelManager()));
    }

    @Override
    protected void setupRotations(EtoilesEntity entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
        
        // Si l'entité a la pose SITTING, l'abaisser pour qu'elle touche le sol
        if (entity.getPose() == net.minecraft.world.entity.Pose.SITTING) {
            poseStack.translate(0.0D, -0.5D, 0.0D);
        }
    }

    @Override
    protected void scale(EtoilesEntity entity, PoseStack poseStack, float partialTickTime) {
        // Configure la pose du modèle pour qu'il plie les jambes
        if (entity.getPose() == net.minecraft.world.entity.Pose.SITTING) {
            this.model.riding = true;
        } else {
            this.model.riding = false;
        }
        super.scale(entity, poseStack, partialTickTime);
    }

     @Override
    public ResourceLocation getTextureLocation(EtoilesEntity entity) {
        if ("justekal".equalsIgnoreCase(entity.getName().getString().trim())) {
            return ResourceLocation.fromNamespaceAndPath("etoiles_universe", "textures/entity/justekal.png");
        }
        else if ("etoiles".equalsIgnoreCase(entity.getName().getString().trim())) {
            return ResourceLocation.fromNamespaceAndPath("etoiles_universe", "textures/entity/etoiles_qsmp.png");
        }
        else if ("viaduc".equalsIgnoreCase(entity.getName().getString().trim())) {
            return ResourceLocation.fromNamespaceAndPath("etoiles_universe", "textures/entity/viaduc.png");
        }
        return ResourceLocation.fromNamespaceAndPath("etoiles_universe", "textures/entity/etoiles.png");
    }
}
