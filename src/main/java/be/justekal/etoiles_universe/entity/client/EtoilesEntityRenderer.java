package be.justekal.etoiles_universe.entity.client;

import be.justekal.etoiles_universe.entity.custom.EtoilesEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EtoilesEntityRenderer extends HumanoidMobRenderer<EtoilesEntity, HumanoidModel<EtoilesEntity>> {
    public EtoilesEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EtoilesEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("etoiles_universe", "textures/entity/etoiles.png");
    }
}