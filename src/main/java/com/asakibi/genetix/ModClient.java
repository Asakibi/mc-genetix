package com.asakibi.genetix;

import com.asakibi.genetix.block.registry.BlockRegistry;
import com.asakibi.genetix.entity.EntityRegistry;
import com.asakibi.genetix.entity.model.GenetixSheepEntityModel;
import com.asakibi.genetix.entity.renderer.GenetixSheepEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_GENETIX_SHEEP_LAYER
        = new EntityModelLayer(
            new Identifier(Mod.NAME, "genetix_sheep"),
        "main"
    );

    @Override
    public void onInitializeClient() {
        registryAll();
    }

    private void registryAll() {
        EntityRendererRegistry.register(EntityRegistry.GENETIX_SHEEP, GenetixSheepEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_GENETIX_SHEEP_LAYER, GenetixSheepEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.TOMATO_CROP_SEEDS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.TOMATO_CROP_RED);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.TOMATO_CROP_PINK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.TOMATO_CROP_ORANGE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.TOMATO_CROP_YELLOW);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.GARLIC_CROP_WHITE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.GARLIC_CROP_PURPLE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.GARLIC_CROP_SEEDS);
    }
}
