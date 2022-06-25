package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.crop.GarlicCrop;
import com.asakibi.genetix.block.crop.TomatoCrop;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;


public class BlockRegistry {

    private static final AbstractBlock.Settings CROP_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP);

    public static final TomatoCrop TOMATO_CROP_SEEDS = new TomatoCrop(CROP_SETTINGS, ItemRegistry.TOMATO_SEEDS);
    public static final TomatoCrop TOMATO_CROP_RED = new TomatoCrop(CROP_SETTINGS, ItemRegistry.RED_TOMATO);
    public static final TomatoCrop TOMATO_CROP_PINK = new TomatoCrop(CROP_SETTINGS, ItemRegistry.PINK_TOMATO);
    public static final TomatoCrop TOMATO_CROP_ORANGE = new TomatoCrop(CROP_SETTINGS, ItemRegistry.ORANGE_TOMATO);
    public static final TomatoCrop TOMATO_CROP_YELLOW = new TomatoCrop(CROP_SETTINGS, ItemRegistry.YELLOW_TOMATO);

    public static final GarlicCrop GARLIC_CROP_SEEDS = new GarlicCrop(CROP_SETTINGS, ItemRegistry.GARLIC_SEEDS);
    public static final GarlicCrop GARLIC_CROP_WHITE = new GarlicCrop(CROP_SETTINGS, ItemRegistry.WHITE_GARLIC);
    public static final GarlicCrop GARLIC_CROP_PURPLE = new GarlicCrop(CROP_SETTINGS, ItemRegistry.PURPLE_GARLIC);

    public static final Block BAY_LEAVES = createLeavesBlock(BlockSoundGroup.GRASS);

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop_seeds"), TOMATO_CROP_SEEDS);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop_red"), TOMATO_CROP_RED);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop_pink"), TOMATO_CROP_PINK);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop_orange"), TOMATO_CROP_ORANGE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop_yellow"), TOMATO_CROP_YELLOW);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_seeds"), GARLIC_CROP_SEEDS);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_white"), GARLIC_CROP_WHITE);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "garlic_crop_purple"), GARLIC_CROP_PURPLE);

        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "bay_leaves"), BAY_LEAVES);
    }

    private static LeavesBlock createLeavesBlock(BlockSoundGroup soundGroup) {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(BlockRegistry::canSpawnOnLeaves).suffocates(BlockRegistry::never).blockVision(BlockRegistry::never));
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
