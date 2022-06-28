package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.List;

public abstract class SowableFruitCropEntity extends GenetixCropEntity {

    public SowableFruitCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }

    protected abstract Item getFruitType(Diploid child);
    protected abstract int getTotalFruitNum(Diploid parent);

    @Override
    public final List<ItemStack> getSowableDroppings(Random random) {
        int totalFruitNum = getTotalFruitNum(diploid);
        ItemStack[] itemStacks = new ItemStack[totalFruitNum];

        for (int i = 0; i < totalFruitNum; i++) {
            // apply mutation to each child
            Diploid childDiploid = new Diploid(diploid, random);
            // get item stack of each child, which type is decided by the child's genotype
            ItemStack itemStack = new ItemStack(getFruitType(childDiploid), 1);
            itemStack.setSubNbt("diploid", childDiploid.toNBT());
            itemStacks[i] = itemStack;
        }

        return List.of(itemStacks);
    }

    @Override
    public final List<ItemStack> getUnsowableDroppings(Random random) {
        return List.of();
    }
}
