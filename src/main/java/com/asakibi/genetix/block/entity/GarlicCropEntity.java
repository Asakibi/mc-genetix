package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class GarlicCropEntity extends AsexualGenetixCropEntity {
    public GarlicCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, DiploidStructure.GARLIC);
    }

    @Override
    public Trait getProductionTrait() {
        return Trait.GARLIC_PRODUCTION;
    }
}
