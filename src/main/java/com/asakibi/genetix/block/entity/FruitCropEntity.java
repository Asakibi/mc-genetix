package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.LinkedList;
import java.util.List;

public abstract class FruitCropEntity extends GenetixCropEntity{

    public FruitCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }

    protected abstract Item getProductType(Diploid diploid);
    protected abstract boolean hereditaryFruit();

    @Override
    protected List<ItemStack> getProductsRipe(Random random) {
        List<ItemStack> itemStacks = new LinkedList<>();

        int totalProduction = getTotalProductNum();

        Item productType = getProductType(diploid);
        for (int i = 0; i < totalProduction; i++) {
            ItemStack itemStack = new ItemStack(productType, 1);
            if (hereditaryFruit()) {
                Diploid productSeedGenotype = new Diploid(diploid, random);
                itemStack.setSubNbt("diploid", productSeedGenotype.toNBT());
            }
            itemStacks.add(itemStack);
        }

        return itemStacks;
    }
}
