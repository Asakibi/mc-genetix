package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Gamete;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FruitAndSeedsCropEntity extends GenetixCropEntity {


    protected final Map<NbtCompound, Integer> gametes;
    protected final boolean SELF = true;

    public FruitAndSeedsCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
        gametes = new HashMap<>();
    }

    protected abstract ItemConvertible getSeedsItem();
    protected abstract Item getFruitType(Diploid child);
    protected abstract int getTotalFruitNum(Diploid parent);
    protected abstract int getTotalSeedNum(Diploid parent);

    protected abstract boolean isFruitHereditary();

    @Override
    public final void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        NbtList gametes = nbt.getList("gametes", NbtElement.COMPOUND_TYPE);
        gametes.forEach(gamete -> {
            NbtCompound nbtCompound = (NbtCompound) gamete;
            this.gametes.put(nbtCompound.getCompound("gamete"), nbtCompound.getInt("num"));
        });
    }

    @Override
    protected final void writeNbt(NbtCompound nbt) {
        NbtList gametes = new NbtList();
        this.gametes.forEach((gamete, num) -> {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.put("gamete", gamete);
            nbtCompound.putInt("num", num);
            gametes.add(nbtCompound);
        });

        nbt.put("gametes", gametes);
        super.writeNbt(nbt);
    }

    protected final int countGametes() {
        AtomicInteger total = new AtomicInteger();
        gametes.forEach((key, value) -> {
            int num = value;
            total.addAndGet(num);
        });
        return total.get();
    }

    public final int countVacancies() {
        int count = countGametes();
        int d = getTotalSeedNum(diploid) - count;
        return Math.max(d, 0);
    }

    public final void addGametes(Gamete gamete, int num) {
        if (num <= 0) return;

        int count = countVacancies();
        if (count <= 0) return;

        num = Math.min(num, count);

        addGamete(gamete, num);
        markDirty();
    }

    public final void addGametes(Diploid diploid, int num, Random random) {
        if (num <= 0) return;

        int count = countVacancies();
        if (count <= 0) return;

        num = Math.min(num, count);

        for (int i = 0; i < num; i++) {
            addGamete(new Gamete(diploid, random), 1);
        }

        markDirty();
    }

    private final void addGamete(Gamete gamete, int num) {
        NbtCompound gameteNbt = gamete.toNBT();

        boolean existed = gametes.containsKey(gameteNbt);
        if (existed) {
            gametes.put(gameteNbt, gametes.get(gameteNbt) + num);
        } else {
            gametes.put(gameteNbt, num);
        }

        markDirty();
    }
}
