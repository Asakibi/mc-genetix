package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.entity.GarlicCropEntity;
import com.asakibi.genetix.block.entity.TomatoCropEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Constructor;

public class BlockEntityRegistry {
    public static BlockEntityType<TomatoCropEntity> TOMATO_CROP_ENTITY;
    public static BlockEntityType<GarlicCropEntity> GARLIC_CROP_ENTITY;

    public static void registerAll() {
        TOMATO_CROP_ENTITY = register("tomato_crop_entity", TomatoCropEntity::new, BlockRegistry.TOMATO_CROP);
        GARLIC_CROP_ENTITY = register("garlic_crop_entity",
            (blockPos, blockState)
                -> new GarlicCropEntity(BlockEntityRegistry.GARLIC_CROP_ENTITY, blockPos, blockState),
                BlockRegistry.PURPLE_GARLIC_CROP, BlockRegistry.WHITE_GARLIC_CROP
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
