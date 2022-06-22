package com.asakibi.genetix.entity.renderer;

import com.asakibi.genetix.entity.GenetixSheepEntity;
import com.asakibi.genetix.entity.model.GenetixSheepEntityModel;
import com.asakibi.genetix.entity.model.GenetixSheepWoolEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GenetixSheepWoolFeatureRenderer extends FeatureRenderer<GenetixSheepEntity, GenetixSheepEntityModel<GenetixSheepEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
    private final GenetixSheepWoolEntityModel<GenetixSheepEntity> model;

    public GenetixSheepWoolFeatureRenderer(FeatureRendererContext<GenetixSheepEntity, GenetixSheepEntityModel<GenetixSheepEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new GenetixSheepWoolEntityModel(loader.getModelPart(EntityModelLayers.SHEEP_FUR));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, GenetixSheepEntity genetixSheepEntity, float f, float g, float h, float j, float k, float l) {
        if (!genetixSheepEntity.isSheared()) {
            if (genetixSheepEntity.isInvisible()) {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                boolean bl = minecraftClient.hasOutline(genetixSheepEntity);
                if (bl) {
                    ((GenetixSheepEntityModel)this.getContextModel()).copyStateTo(this.model);
                    this.model.animateModel(genetixSheepEntity, f, g, h);
                    this.model.setAngles(genetixSheepEntity, f, g, j, k, l);
                    VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SKIN));
                    this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(genetixSheepEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                }

            } else {
                float s;
                float t;
                float u;
                if (genetixSheepEntity.hasCustomName() && "jeb_".equals(genetixSheepEntity.getName().getString())) {
                    int n = genetixSheepEntity.age / 25 + genetixSheepEntity.getId();
                    int o = DyeColor.values().length;
                    int p = n % o;
                    int q = (n + 1) % o;
                    float r = ((float)(genetixSheepEntity.age % 25) + h) / 25.0F;
                    float[] fs = GenetixSheepEntity.getRgbColor(DyeColor.byId(p));
                    float[] gs = GenetixSheepEntity.getRgbColor(DyeColor.byId(q));
                    s = fs[0] * (1.0F - r) + gs[0] * r;
                    t = fs[1] * (1.0F - r) + gs[1] * r;
                    u = fs[2] * (1.0F - r) + gs[2] * r;
                } else {
                    float[] hs = GenetixSheepEntity.getRgbColor(genetixSheepEntity.getColor());
                    s = hs[0];
                    t = hs[1];
                    u = hs[2];
                }

                render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, genetixSheepEntity, f, g, j, k, l, h, s, t, u);
            }
        }
    }
}
