package com.asakibi.genetix.world.generation;

import com.asakibi.genetix.world.PlacedFeatureRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class TreeGeneration{

    public static void addFeature() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
            GenerationStep.Feature.VEGETAL_DECORATION,
            PlacedFeatureRegistry.BAY_PLACED.getKey().get());
    }

}
