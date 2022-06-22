package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.GarlicCrop;
import com.asakibi.genetix.block.PurpleGarlicCrop;
import com.asakibi.genetix.block.TomatoCrop;
import com.asakibi.genetix.block.WhiteGarlicCrop;
import com.asakibi.genetix.block.entity.GarlicCropEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class BlockRegistry {

    private static final AbstractBlock.Settings CROP_SETTINGS = AbstractBlock.Settings.of(Material.PLANT).nonOpaque().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP);

    public static final CropBlock TOMATO_CROP = new TomatoCrop(CROP_SETTINGS);
    public static final GarlicCrop PURPLE_GARLIC_CROP = new PurpleGarlicCrop(CROP_SETTINGS);
    public static final GarlicCrop WHITE_GARLIC_CROP = new WhiteGarlicCrop(CROP_SETTINGS);

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "tomato_crop"), TOMATO_CROP);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "purple_garlic_crop"), PURPLE_GARLIC_CROP);
        Registry.register(Registry.BLOCK, new Identifier(Mod.NAME, "white_garlic_crop"), WHITE_GARLIC_CROP);
    }
}
