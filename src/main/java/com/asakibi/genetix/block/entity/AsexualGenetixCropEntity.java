package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.Map;

public abstract class AsexualGenetixCropEntity extends GenetixCropEntity{

    public AsexualGenetixCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }

    @Override
    public Map<NbtCompound, Integer> getChildrenAtLast(Random random) {
        this.generateChildren(random);
        return super.childDiploids;
    }

    private void generateChildren(Random random) {
        int production = (int) diploid.computeTrait(getProductionTrait());
        for (int i = 0; i < production; i++) {
            addChild(new Diploid(diploid, random).toNBT());
        }
    }

    public abstract Trait getProductionTrait();
}
