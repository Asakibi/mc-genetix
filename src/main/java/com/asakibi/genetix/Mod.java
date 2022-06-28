package com.asakibi.genetix;

import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.block.registry.BlockRegistry;
import com.asakibi.genetix.config.ModConfig;
import com.asakibi.genetix.entity.EntityRegistry;
import com.asakibi.genetix.item.registry.ItemRegistry;
import com.asakibi.genetix.world.ConfiguredFeatureRegistry;
import com.asakibi.genetix.world.generation.WorldGeneration;
import net.fabricmc.api.ModInitializer;

public class Mod implements ModInitializer {

	public static final String NAME = "genetix";

	@Override
	public void onInitialize() {
		ModConfig modConfig = new ModConfig();
		modConfig.load();
//		ConfiguredFeatureRegistry.register();
		ItemRegistry.registerAll();
		EntityRegistry.registerAll();
		BlockRegistry.registerAll();
		BlockEntityRegistry.registerAll();
		WorldGeneration.initialize();
	}
}
