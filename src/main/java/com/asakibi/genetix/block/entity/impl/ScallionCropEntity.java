package com.asakibi.genetix.block.entity.impl;

import com.asakibi.genetix.block.entity.SowableFruitAndSeedsCropEntity;
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

public class ScallionCropEntity extends SowableFruitAndSeedsCropEntity {

    public ScallionCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SCALLION_CROP_ENTITY, pos, state, DiploidStructure.SCALLION);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ItemRegistry.SCALLION_SEEDS;
    }

    @Override
    protected Item getFruitType(Diploid child) {
        return ItemRegistry.SCALLION;
    }

    @Override
    protected int getTotalFruitNum(Diploid parent) {
        return (int) parent.computeTrait(Trait.SCALLION_PRODUCTION);
    }

    @Override
    protected int getTotalSeedNum(Diploid parent) {
        return (int) parent.computeTrait(Trait.SCALLION_SEED_NUM);
    }

    @Override
    protected boolean isFruitHereditary() {
        return PlantConfig.HeredityFruit.scallion;
    }
}
