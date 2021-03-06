package com.asakibi.genetix.world;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class PlacedFeatureRegistry {
    public static final RegistryEntry<PlacedFeature> BAY_PLACED =
        PlacedFeatures.register("bay_placed",
            ConfiguredFeatureRegistry.BAY_SPAWN,
            VegetationPlacedFeatures.modifiers(
                PlacedFeatures.createCountExtraModifier(1, 0.5f, 1)
            )
        );

    public static final RegistryEntry<PlacedFeature> CHINESE_CASSIA_PLACED =
        PlacedFeatures.register("chinese_cassia_placed",
            ConfiguredFeatureRegistry.CHINESE_CASSIA_SPAWN,
            VegetationPlacedFeatures.modifiers(
                PlacedFeatures.createCountExtraModifier(1, 0.5f, 1)
            )
        );
}
