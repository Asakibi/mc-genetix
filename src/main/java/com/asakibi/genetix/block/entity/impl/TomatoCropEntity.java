package com.asakibi.genetix.block.entity.impl;

import com.asakibi.genetix.block.entity.RootCropEntity;
import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class TomatoCropEntity extends RootCropEntity {

    public TomatoCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TOMATO_CROP_ENTITY, pos, state, DiploidStructure.TOMATO);
    }

    @Override
    protected Trait getSeedNumTrait() {
        return Trait.TOMATO_SEED_NUM;
    }

    @Override
    protected Item getSeedsItem() {
        return ItemRegistry.TOMATO_SEEDS;
    }

    @Override
    protected Trait getProductionTrait() {
        return Trait.TOMATO_PRODUCTION;
    }

    @Override
    protected Item getProductType(Diploid diploid) {
        return (Item) diploid.computeTrait(Trait.TOMATO_TYPE);
    }

    @Override
    protected boolean hereditaryFruit() {
        return PlantConfig.HeredityFruit.tomato;
    }
}
