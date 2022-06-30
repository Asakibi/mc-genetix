package com.asakibi.genetix.block.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.entity.impl.GarlicCropEntity;
import com.asakibi.genetix.block.entity.impl.ScallionCropEntity;
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
    public static BlockEntityType<ScallionCropEntity> SCALLION_CROP_ENTITY;

//    public static BlockEntityType<SignBlockEntity> SIGN;

    public static void registerAll() {
        TOMATO_CROP_ENTITY = register("tomato_crop_entity", TomatoCropEntity::new,
            BlockRegistry.TOMATO_CROP
        );
        GARLIC_CROP_ENTITY = register("garlic_crop_entity", GarlicCropEntity::new,
            BlockRegistry.GARLIC_CROP_WHITE,
            BlockRegistry.GARLIC_CROP_PURPLE
        );
        SCALLION_CROP_ENTITY = register("scallion_crop_entity", ScallionCropEntity::new,
            BlockRegistry.SCALLION_CROP,
            BlockRegistry.SCALLION_CROP_SEED
        );
//        SIGN = register("genetix_sign", SignBlockEntity::new,
//            BlockRegistry.BAY_SIGN,
//            BlockRegistry.BAY_WALL_SIGN);
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
