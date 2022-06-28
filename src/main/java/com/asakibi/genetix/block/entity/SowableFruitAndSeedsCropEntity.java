package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Gamete;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.RunArgs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.LinkedList;
import java.util.List;

public abstract class SowableFruitAndSeedsCropEntity extends FruitAndSeedsCropEntity {
    public SowableFruitAndSeedsCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }

    @Override
    public final List<ItemStack> getSowableDroppings(Random random) {

        // SEED
        if (SELF) {
            addGametes(diploid, getTotalSeedNum(diploid), random);
        }

        List<ItemStack> children = new LinkedList<>();

        gametes.forEach((gamete, num) -> {
            for (int i = 0; i < num; i++) {
                Gamete thisCropGamete = new Gamete(diploid, random);
                Diploid child = new Diploid(thisCropGamete, new Gamete(gamete));
                ItemStack itemStack = new ItemStack(getSeedsItem());
                itemStack.setSubNbt("diploid", child.toNBT());
                children.add(itemStack);
            }
        });

        // FRUIT
        int totalFruitNum = getTotalFruitNum(diploid);

        for (int i = 0; i < totalFruitNum; i++) {
            Diploid childDiploid = new Diploid(diploid, random);
            ItemStack itemStack = new ItemStack(getFruitType(childDiploid), 1);
            if (PlantConfig.product_getting_genes) {
                itemStack.setSubNbt("diploid", childDiploid.toNBT());
            }
            children.add(itemStack);
        }

        return children;
    }

    public final List<ItemStack> getUnsowableDroppings(Random random) {
        return List.of();
    }
}
