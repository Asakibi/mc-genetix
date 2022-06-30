package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.crop.GarlicCrop;
import com.asakibi.genetix.block.crop.ScallionCrop;
import com.asakibi.genetix.block.crop.TomatoCrop;
import com.asakibi.genetix.item.registry.GroupRegistry;
import com.asakibi.genetix.item.registry.ItemRegistry;
import com.asakibi.genetix.world.generator.BaySaplingGenerator;
import com.asakibi.genetix.world.generator.ChineseCassiaSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
//    public static final Block BAY_SIGN = register("bay_sign",
//        new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN), SignType.OAK));
//    public static final Block BAY_WALL_SIGN = registerWithoutItem("bay_wall_sign",
//        new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).dropsLike(BAY_SIGN), SignType.OAK));

    public static final Block BAY_WOOD = register("bay_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_BAY_LOG = register("stripped_bay_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_BAY_WOOD = register("stripped_bay_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)));

    public static final Block CHINESE_CASSIA_LEAVES = register("chinese_cassia_leaves",
        new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    public static final Block CHINESE_CASSIA_LOG = register("chinese_cassia_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)));
    public static final Block CHINESE_CASSIA_SAPLING = register("chinese_cassia_sapling",
        new SaplingBlock(new ChineseCassiaSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));
    public static final Block CHINESE_CASSIA_PLANKS = register("chinese_cassia_planks",
        new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block CHINESE_CASSIA_STAIRS = register("chinese_cassia_stairs",
        new StairsBlock(CHINESE_CASSIA_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS)));
    public static final Block CHINESE_CASSIA_SLAB = register("chinese_cassia_slab",
        new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB)));
    public static final Block CHINESE_CASSIA_FENCE = register("chinese_cassia_fence",
        new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE)));
    public static final Block CHINESE_CASSIA_FENCE_GATE = register("chinese_cassia_fence_gate",
        new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE)));
    public static final Block CHINESE_CASSIA_TRAPDOOR = register("chinese_cassia_trapdoor",
        new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR)));
    public static final Block CHINESE_CASSIA_BUTTON = register("chinese_cassia_button",
        new WoodenButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON)));
    public static final Block CHINESE_CASSIA_PRESSURE_PLATE = register("chinese_cassia_pressure_plate",
        new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE)));
    public static final Block CHINESE_CASSIA_DOOR = register("chinese_cassia_door",
        new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR)));
//    public static final Block CHINESE_CASSIA_SIGN = register("chinese_cassia_sign",
//        new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN), SignType.OAK));
//    public static final Block CHINESE_CASSIA_WALL_SIGN = registerWithoutItem("chinese_cassia_wall_sign",
//        new WallSignBlock(FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).dropsLike(CHINESE_CASSIA_SIGN), SignType.OAK));

    public static final Block CHINESE_CASSIA_WOOD = register("chinese_cassia_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)));
    public static final Block STRIPPED_CHINESE_CASSIA_LOG = register("stripped_chinese_cassia_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_CHINESE_CASSIA_WOOD = register("stripped_chinese_cassia_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)));

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop"), TOMATO_CROP);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_white"), GARLIC_CROP_WHITE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_purple"), GARLIC_CROP_PURPLE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop"), SCALLION_CROP);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "scallion_crop_seed"), SCALLION_CROP_SEED);

        registerStrippables();
        registerFlammables();
    }

    private static Block register(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, path), block);
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, path), new BlockItem(block, new FabricItemSettings().group(GroupRegistry.BLOCKS)){
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                tooltip.add(Text.translatable("block." + Mod.NAME + "." + path + ".tooltip"));
            }
        });
        return block;
    }

    private static Block registerWithoutItem(String path, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, path), block);
        return block;
    }

    private static void registerStrippables() {
        StrippableBlockRegistry.register(BAY_LOG, STRIPPED_BAY_LOG);
        StrippableBlockRegistry.register(BAY_WOOD, STRIPPED_BAY_WOOD);
        StrippableBlockRegistry.register(CHINESE_CASSIA_LOG, STRIPPED_CHINESE_CASSIA_LOG);
        StrippableBlockRegistry.register(CHINESE_CASSIA_WOOD, STRIPPED_CHINESE_CASSIA_WOOD);
    }

    private static void registerFlammables() {
        FlammableBlockRegistry instance = FlammableBlockRegistry.getDefaultInstance();

        // attributes from FireBlock
        instance.add(BAY_PLANKS, 5, 20);
        instance.add(BAY_SLAB, 5, 20);
        instance.add(BAY_FENCE_GATE, 5, 20);
        instance.add(BAY_FENCE, 5, 20);
        instance.add(BAY_STAIRS, 5, 20);
        instance.add(BAY_LOG, 5, 5);
        instance.add(BAY_LEAVES, 30, 60);
        instance.add(BAY_WOOD, 5, 5);
        instance.add(STRIPPED_BAY_LOG, 5, 5);
        instance.add(STRIPPED_BAY_WOOD, 5, 5);

        instance.add(CHINESE_CASSIA_PLANKS, 5, 20);
        instance.add(CHINESE_CASSIA_SLAB, 5, 20);
        instance.add(CHINESE_CASSIA_FENCE_GATE, 5, 20);
        instance.add(CHINESE_CASSIA_FENCE, 5, 20);
        instance.add(CHINESE_CASSIA_STAIRS, 5, 20);
        instance.add(CHINESE_CASSIA_LOG, 5, 5);
        instance.add(CHINESE_CASSIA_LEAVES, 30, 60);
        instance.add(CHINESE_CASSIA_WOOD, 5, 5);
        instance.add(STRIPPED_CHINESE_CASSIA_LOG, 5, 5);
        instance.add(STRIPPED_CHINESE_CASSIA_WOOD, 5, 5);
    }
}
