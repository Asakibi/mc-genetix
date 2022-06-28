package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FruitAndSeedsCropEntity extends GenetixCropEntity {
    public FruitAndSeedsCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
        parents = new HashMap<>();
    }

    protected final Map<NbtCompound, Integer> parents;
    protected final boolean SELF = true;

    protected abstract ItemConvertible getSeedsItem();
    protected abstract Item getFruitType(Diploid child);
    protected abstract int getTotalFruitNum(Diploid parent);
    protected abstract int getTotalSeedNum(Diploid parent);

    protected abstract boolean isFruitHereditary();

    @Override
    public final void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        NbtList parents = nbt.getList("parents", NbtElement.COMPOUND_TYPE);
        parents.forEach(parent -> {
            NbtCompound nbtCompound = (NbtCompound) parent;
            this.parents.put(nbtCompound.getCompound("diploid"), nbtCompound.getInt("num"));
        });
    }

    @Override
    protected final void writeNbt(NbtCompound nbt) {
        NbtList parents = new NbtList();
        this.parents.forEach((parent, num) -> {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.put("diploid", parent);
            nbtCompound.putInt("num", num);
            parents.add(nbtCompound);
        });

        nbt.put("parents", parents);
        super.writeNbt(nbt);
    }

    protected final int countParents() {
        AtomicInteger total = new AtomicInteger();
        parents.forEach((key, value) -> {
            int num = value;
            total.addAndGet(num);
        });
        return total.get();
    }

    public final int countVacancies() {
        int count = countParents();
        int d = getTotalSeedNum(diploid) - count;
        return Math.max(d, 0);
    }

    public final void addParents(Diploid diploid, int num) {
        if (num <= 0) return;

        int count = countVacancies();
        if (count <= 0) return;

        num = Math.min(num, count);

        NbtCompound nbtCompound = diploid.toNBT();

        parents.put(nbtCompound, num);
        markDirty();
    }
}
