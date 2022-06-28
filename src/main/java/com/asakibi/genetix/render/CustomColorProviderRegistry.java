package com.asakibi.genetix.render;

import com.asakibi.genetix.block.registry.BlockRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class CustomColorProviderRegistry {

    public static void initialize() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 0x95A645, BlockRegistry.BAY_LEAVES);
    }
}
