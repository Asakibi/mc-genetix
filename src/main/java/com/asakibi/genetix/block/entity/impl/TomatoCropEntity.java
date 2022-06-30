package com.asakibi.genetix.block.entity.impl;

import com.asakibi.genetix.block.entity.UnsowableFruitAndSeedCropEntity;
import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;

public class TomatoCropEntity extends UnsowableFruitAndSeedCropEntity {

    public TomatoCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TOMATO_CROP_ENTITY, pos, state, DiploidStructure.TOMATO);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ItemRegistry.TOMATO_SEEDS;
    }

    @Override
    protected Item getFruitType(Diploid child) {
        return (Item) child.computeTrait(Trait.TOMATO_TYPE);
    }

    @Override
    protected int getTotalFruitNum(Diploid parent) {
        return (int) parent.computeTrait(Trait.TOMATO_PRODUCTION);
    }

    @Override
    protected int getTotalSeedNum(Diploid parent) {
        return (int) parent.computeTrait(Trait.TOMATO_SEED_NUM);
    }

    @Override
    protected boolean isFruitHereditary() {
        return PlantConfig.HeredityFruit.tomato;
    }
}
