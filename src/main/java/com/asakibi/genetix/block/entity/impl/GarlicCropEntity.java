package com.asakibi.genetix.block.entity.impl;

import com.asakibi.genetix.block.entity.SowableFruitCropEntity;
import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class GarlicCropEntity extends SowableFruitCropEntity {
    public GarlicCropEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GARLIC_CROP_ENTITY, pos, state, DiploidStructure.GARLIC);
    }

    @Override
    protected final Item getFruitType(Diploid child) {
        return (Item) child.computeTrait(Trait.GARLIC_TYPE);
    }

    @Override
    protected final int getTotalFruitNum(Diploid parent) {
        return (int) parent.computeTrait(Trait.GARLIC_PRODUCTION);
    }
}
