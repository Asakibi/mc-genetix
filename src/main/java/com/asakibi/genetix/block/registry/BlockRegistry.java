package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.crop.GarlicCrop;
import com.asakibi.genetix.block.crop.ScallionCrop;
import com.asakibi.genetix.block.crop.TomatoCrop;
import com.asakibi.genetix.world.generator.BaySaplingGenerator;
import com.asakibi.genetix.item.registry.GroupRegistry;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class BlockRegistry {

    private static final AbstractBlock.Settings CROP_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP);

    public static final TomatoCrop TOMATO_CROP = new TomatoCrop(CROP_SETTINGS, ItemRegistry.TOMATO_SEEDS);

    public static final GarlicCrop GARLIC_CROP_WHITE = new GarlicCrop(CROP_SETTINGS, ItemRegistry.WHITE_GARLIC);
    public static final GarlicCrop GARLIC_CROP_PURPLE = new GarlicCrop(CROP_SETTINGS, ItemRegistry.PURPLE_GARLIC);

    public static final ScallionCrop SCALLION_CROP = new ScallionCrop(CROP_SETTINGS, ItemRegistry.SCALLION);
    public static final ScallionCrop SCALLION_CROP_SEED = new ScallionCrop(CROP_SETTINGS, ItemRegistry.SCALLION_SEEDS);

    public static final Block BAY_LEAVES = new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES));
    public static final Block BAY_LOG = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG));
    public static final Block BAY_SAPLING = new SaplingBlock(new BaySaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING));

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop"), TOMATO_CROP);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_white"), GARLIC_CROP_WHITE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_purple"), GARLIC_CROP_PURPLE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop"), SCALLION_CROP);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop_seed"), SCALLION_CROP_SEED);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "bay_leaves"), BAY_LEAVES);
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, "bay_leaves"), new BlockItem(BAY_LEAVES, new FabricItemSettings().group(GroupRegistry.BLOCKS)));
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "bay_log"), BAY_LOG);
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, "bay_log"), new BlockItem(BAY_LOG, new FabricItemSettings().group(GroupRegistry.BLOCKS)));
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "bay_sapling"), BAY_SAPLING);
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, "bay_sapling"), new BlockItem(BAY_SAPLING, new FabricItemSettings().group(GroupRegistry.BLOCKS)));
    }
}
