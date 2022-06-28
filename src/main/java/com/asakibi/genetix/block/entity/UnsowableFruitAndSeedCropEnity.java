package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.LinkedList;
import java.util.List;

public abstract class UnsowableFruitAndSeedCropEnity extends FruitAndSeedsCropEntity {
    public UnsowableFruitAndSeedCropEnity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }


    @Override
    public final List<ItemStack> getSowableDroppings(Random random) {

        if (SELF) {
            addParents(diploid, getTotalSeedNum(diploid));
        }

        List<ItemStack> children = new LinkedList<>();

        parents.forEach((parent, num) -> {
            for (int i = 0; i < num; i++) {
                Diploid child = new Diploid(diploid, new Diploid(parent), random);
                ItemStack itemStack = new ItemStack(getSeedsItem());
                itemStack.setSubNbt("diploid", child.toNBT());
                children.add(itemStack);
            }
        });

        return children;
    }

    @Override
    public final List<ItemStack> getUnsowableDroppings(Random random) {
        int totalFruitNum = getTotalFruitNum(diploid);
        Item fruitType = getFruitType(diploid);

        return List.of(new ItemStack(fruitType, totalFruitNum));
    }
}
