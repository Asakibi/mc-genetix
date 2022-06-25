package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class GenetixCropEntity extends BlockEntity {

    protected Map<NbtCompound, Integer> parents;
    protected int parentNum;
    protected Diploid diploid;
    protected final DiploidStructure DIPLOID_STRUCTURE;

    public GenetixCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state);
        parents = new HashMap<>();
        parentNum = 0;
        DIPLOID_STRUCTURE = diploidStructure;
    }

    public final void generateDiploid(Random random, boolean isNatural) {
        this.diploid = new Diploid(this.DIPLOID_STRUCTURE, random, isNatural);
    }

    public final void setDiploid(NbtCompound nbtCompound) {
        this.diploid = new Diploid(nbtCompound);
        markDirty();
    }

    public final Diploid getDiploid() {
        return this.diploid;
    }

    @Override
    public final void readNbt(NbtCompound nbt) {

//        NbtCompound nbtDiploid = nbt.getCompound("diploid");
//        this.diploid = nbtDiploid == null ? null : new Diploid(nbtDiploid);
//        this.parentNum = nbt.getInt("parent_num");
//
//        NbtCompound parents = nbt.getCompound("parents");
//        if (parents != null) {
//            parents.getKeys().forEach(e -> {
//                NbtCompound parent = (NbtCompound) parents.get(e);
//                if (parent != null) {
//                    NbtCompound parentDiploid = parent.getCompound("diploid");
//                    int num = parent.getInt("num");
//                    this.parents.put(parentDiploid, num);
//                }
//            });
//        }
//
//        super.readNbt(nbt);

        NbtCompound nbtDiploid = nbt.getCompound("diploid");
        this.diploid = nbtDiploid == null ? null : new Diploid(nbtDiploid);
        this.parentNum = nbt.getInt("parent_num");

        NbtList parents = nbt.getList("parents", NbtCompound.COMPOUND_TYPE);
        if (parents != null) {
            parents.forEach(e -> {
                NbtCompound parent = (NbtCompound) e;
                NbtCompound parentDiploid = parent.getCompound("diploid");
                int num = parent.getInt("num");
                this.parents.put(parentDiploid, num);
            });
        }

        super.readNbt(nbt);
    }

    @Override
    protected final void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        nbt.put("diploid", this.diploid.toNBT());
//
//        NbtCompound parents = new NbtCompound();
//
//        int index = 0;
//        this.parents.forEach((diploid, num) -> {
//            NbtCompound parent = new NbtCompound();
//            parent.put("diploid", diploid);
//            parent.putInt("num", num);
//            parents.put(String.valueOf(index + 1), parent);
//        });
//
//        nbt.put("parents", parents);
//        nbt.putInt("parentNum", parentNum);

        super.writeNbt(nbt);
        nbt.put("diploid", this.diploid.toNBT());

        NbtList parents = new NbtList();

        this.parents.forEach((diploid, num) -> {
            NbtCompound parent = new NbtCompound();
            parent.put("diploid", diploid);
            parent.putInt("num", num);
            parents.add(parent);
        });

        nbt.put("parents", parents);
        nbt.putInt("parentNum", parentNum);
    }

    protected final void addParent(NbtCompound nbtDiploid, int num) {
        int remaining = getRemainingParentNum();
        num = Math.min(remaining, num);
        Integer existedNum = parents.get(nbtDiploid);
        existedNum = existedNum == null ? 0 : existedNum;
        parentNum += num;

        parents.put(nbtDiploid, existedNum + num);
        markDirty();
    }

    public final int getRemainingParentNum() {
        int bound = (int) diploid.computeTrait(getSeedNumTrait());
        int remaining = bound - parentNum;
        return Math.max(remaining, 0);
    }

    protected final void self() {
        int remaining = getRemainingParentNum();
        if (remaining <= 0) {
            return;
        }

        addParent(diploid.toNBT(), remaining);
    }

    public final int hybrid(Diploid anotherDiploid, int num, Random random) {
        if (!PlantConfig.pollination) {
            return 0;
        }

        int remaining = getRemainingParentNum();
        if (remaining <= 0 || num <= 0) {
            return 0;
        }

        addParent(anotherDiploid.toNBT(), num);
        return getRemainingParentNum();
    }

    protected abstract Trait getSeedNumTrait();
    protected abstract Item getSeedsItem();
    protected abstract Trait getProductionTrait();

    protected final int getTotalProductNum() {
        return (int) diploid.computeTrait(getProductionTrait());
    }

    protected List<ItemStack> getSeedsYoung(Random random) {
        Item seed = getSeedsItem();
        ItemStack itemStack = new ItemStack(seed);
        itemStack.setSubNbt("diploid", diploid.toNBT());
        return List.of(itemStack);
    }

    protected List<ItemStack> getSeedsUnripe(Random random) {
        return List.of();
    }

    protected List<ItemStack> getSeedsRipe(Random random) {
        self();

        List<ItemStack> itemStacks = new LinkedList<>();
        parents.forEach((diploidNbt, num) -> {
            if (num >= 1) {
                Diploid child = new Diploid(new Diploid(diploidNbt), diploid, random);
                ItemStack itemStack = new ItemStack(getSeedsItem(), num);
                itemStack.setSubNbt("diploid", child.toNBT());
                itemStacks.add(itemStack);
            }
        });
        return itemStacks;
    }

    protected List<ItemStack> getProductsYoung(Random random) {
        return List.of();
    }

    protected List<ItemStack> getProductsUnripe(Random random) {
        return List.of();
    }

    protected abstract List<ItemStack> getProductsRipe(Random random);

    public List<ItemStack> getSeedsAndProductsYoung(Random random) {
        List<ItemStack> items = new LinkedList<>();
        items.addAll(getSeedsYoung(random));
        items.addAll(getProductsYoung(random));
        return items;
    }

    public List<ItemStack> getSeedsAndProductsUnripe(Random random) {
        List<ItemStack> items = new LinkedList<>();
        items.addAll(getSeedsUnripe(random));
        items.addAll(getProductsUnripe(random));
        return items;
    }

    public List<ItemStack> getSeedsAndProductsRipe(Random random) {
        List<ItemStack> items = new LinkedList<>();
        items.addAll(getSeedsRipe(random));
        items.addAll(getProductsRipe(random));
        return items;
    }
}
