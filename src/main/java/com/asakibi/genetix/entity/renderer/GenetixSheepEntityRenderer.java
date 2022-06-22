package com.asakibi.genetix.entity.renderer;

import com.asakibi.genetix.entity.GenetixSheepEntity;
import com.asakibi.genetix.entity.model.GenetixSheepEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GenetixSheepEntityRenderer extends MobEntityRenderer<GenetixSheepEntity, GenetixSheepEntityModel<GenetixSheepEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/sheep/sheep.png");

    public GenetixSheepEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GenetixSheepEntityModel<>(context.getPart(EntityModelLayers.SHEEP)), 0.7F);
        this.addFeature(new GenetixSheepWoolFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(GenetixSheepEntity genetixSheepEntity) {
        return TEXTURE;
    }
}
