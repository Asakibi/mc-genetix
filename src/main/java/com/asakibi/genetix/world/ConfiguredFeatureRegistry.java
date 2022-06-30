package com.asakibi.genetix.world;

import com.asakibi.genetix.block.registry.BlockRegistry;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ConfiguredFeatureRegistry {

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> BAY_TREE =
        ConfiguredFeatures.register("bay_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(BlockRegistry.BAY_LOG),
            new StraightTrunkPlacer(5, 1, 2),
            BlockStateProvider.of(BlockRegistry.BAY_LEAVES),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(0, 0, 2)
        ).build());

    public static final RegistryEntry<PlacedFeature> BAY_CHECKED =
        PlacedFeatures.register("bay_checked", BAY_TREE,
            PlacedFeatures.wouldSurvive(BlockRegistry.BAY_SAPLING)
        );

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BAY_SPAWN =
        ConfiguredFeatures.register("bay_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(List.of(new RandomFeatureEntry(BAY_CHECKED, 0.5f)),
            BAY_CHECKED));

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> CHINESE_CASSIA_TREE =
        ConfiguredFeatures.register("chinese_cassia_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(BlockRegistry.CHINESE_CASSIA_LOG),
            new StraightTrunkPlacer(5, 1, 2),
            BlockStateProvider.of(BlockRegistry.CHINESE_CASSIA_LEAVES),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(0, 0, 2)
        ).build());

    public static final RegistryEntry<PlacedFeature> CHINESE_CASSIA_CHECKED =
        PlacedFeatures.register("chinese_cassia_checked", CHINESE_CASSIA_TREE,
            PlacedFeatures.wouldSurvive(BlockRegistry.CHINESE_CASSIA_SAPLING)
        );

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CHINESE_CASSIA_SPAWN =
        ConfiguredFeatures.register("chinese_cassia_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(List.of(new RandomFeatureEntry(CHINESE_CASSIA_CHECKED, 0.5f)),
                CHINESE_CASSIA_CHECKED));

}
