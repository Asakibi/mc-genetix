package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import com.asakibi.genetix.block.registry.BlockEntityRegistry;

import java.util.Map;

public class TomatoCropEntity extends SexualGenetixCropEntity {

    public TomatoCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TOMATO_CROP_ENTITY, pos, state, DiploidStructure.TOMATO);
    }

    @Override
    protected Trait getSelfTimesTrait() {
        return Trait.TOMATO_SELF_SEED_PRODUCTION;
    }

    @Override
    protected Trait getHybridTimesTrait() {
        return Trait.TOMATO_HYBRID_SEED_PRODUCTION;
    }

    @Override
    protected Trait getProductionTrait() {
        return Trait.TOMATO_PRODUCTION;
    }

    @Override
    protected Trait getTypeTrait() {
        return Trait.TOMATO_TYPE;
    }
}
