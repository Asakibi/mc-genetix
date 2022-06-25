package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.entity.impl.GarlicCropEntity;
import com.asakibi.genetix.block.entity.impl.TomatoCropEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {
    public static BlockEntityType<TomatoCropEntity> TOMATO_CROP_ENTITY;
    public static BlockEntityType<GarlicCropEntity> GARLIC_CROP_ENTITY;

    public static void registerAll() {
        TOMATO_CROP_ENTITY = register("tomato_crop_entity", TomatoCropEntity::new,
            BlockRegistry.TOMATO_CROP_SEEDS,
            BlockRegistry.TOMATO_CROP_RED,
            BlockRegistry.TOMATO_CROP_PINK,
            BlockRegistry.TOMATO_CROP_ORANGE,
            BlockRegistry.TOMATO_CROP_YELLOW
        );
        GARLIC_CROP_ENTITY = register("garlic_crop_entity", GarlicCropEntity::new,
            BlockRegistry.GARLIC_CROP_WHITE,
            BlockRegistry.GARLIC_CROP_PURPLE
        );
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
        String path,
        FabricBlockEntityTypeBuilder.Factory<T> factory,
        Block... block) {
        return Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(Mod.NAME, path),
            FabricBlockEntityTypeBuilder.create(factory, block)
                .build(null)
        );
    }
}
