package com.asakibi.genetix.block.entity.impl;

import com.asakibi.genetix.block.entity.FruitCropEntity;
import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class GarlicCropEntity extends FruitCropEntity {
    public GarlicCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GARLIC_CROP_ENTITY, pos, state, DiploidStructure.GARLIC);
    }

    @Override
    protected Trait getSeedNumTrait() {
        return Trait.GARLIC_SEED_NUM;
    }

    @Override
    protected Item getSeedsItem() {
        return ItemRegistry.GARLIC_SEEDS;
    }

    @Override
    protected Trait getProductionTrait() {
        return Trait.GARLIC_PRODUCTION;
    }

    @Override
    protected Item getProductType(Diploid diploid) {
        return (Item) diploid.computeTrait(Trait.GARLIC_TYPE);
    }

    @Override
    protected boolean hereditaryFruit() {
        return PlantConfig.HeredityFruit.garlic;
    }
}
