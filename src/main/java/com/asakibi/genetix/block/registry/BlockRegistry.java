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
import net.minecraft.item.Item;
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

    public static final Block BAY_LEAVES = register("bay_leaves",
        new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    public static final Block BAY_LOG = register("bay_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));
    public static final Block BAY_SAPLING = register("bay_sapling",
        new SaplingBlock(new BaySaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));
    public static final Block BAY_PLANKS = register("bay_planks",
        new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block BAY_STAIRS = register("bay_stairs",
        new StairsBlock(BAY_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block BAY_SLAB = register("bay_slab",
        new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)));
    public static final Block BAY_FENCE = register("bay_fence",
        new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE)));
    public static final Block BAY_FENCE_GATE = register("bay_fence_gate",
        new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)));
    public static final Block BAY_TRAPDOOR = register("bay_trapdoor",
        new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR)));
    public static final Block BAY_BUTTON = register("bay_button",
        new WoodenButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON)));
    public static final Block BAY_PRESSURE_PLATE = register("bay_pressure_plate",
        new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE)));
    public static final Block BAY_DOOR = register("bay_door",
        new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR)));

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop"), TOMATO_CROP);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_white"), GARLIC_CROP_WHITE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_purple"), GARLIC_CROP_PURPLE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop"), SCALLION_CROP);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop_seed"), SCALLION_CROP_SEED);
    }

    public static Block register(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, path), block);
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, path), new BlockItem(block, new FabricItemSettings().group(GroupRegistry.BLOCKS)));
        return block;
    }
}
