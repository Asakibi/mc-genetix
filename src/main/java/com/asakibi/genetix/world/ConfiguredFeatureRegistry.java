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
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
            new TwoLayersFeatureSize(1, 0, 2)
        ).build());

    public static final RegistryEntry<PlacedFeature> BAY_CHECKED =
        PlacedFeatures.register("bay_checked", BAY_TREE,
            PlacedFeatures.wouldSurvive(BlockRegistry.BAY_SAPLING)
        );

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> BAY_SPAWN =
        ConfiguredFeatures.register("bay_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfig(List.of(new RandomFeatureEntry(BAY_CHECKED, 0.5f)),
            BAY_CHECKED));

//    public static void register() {
//        System.out.print("genetix::ConfiguredFeatureRegistry");
//    }
}
