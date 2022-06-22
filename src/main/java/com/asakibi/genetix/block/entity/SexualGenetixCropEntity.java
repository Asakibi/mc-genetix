package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.*;

public abstract class SexualGenetixCropEntity extends GenetixCropEntity {

    private boolean selfDone = false;
    private boolean hybridDone = false;

    public SexualGenetixCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state, diploidStructure);
    }

    @Override
    public final void readNbt(NbtCompound nbt) {
        this.selfDone = nbt.getBoolean("self_done");
        this.hybridDone = nbt.getBoolean("hybrid_done");
        super.readNbt(nbt);
    }

    @Override
    protected final void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("self_done", this.selfDone);
        nbt.putBoolean("hybrid_done", this.hybridDone);
    }

    @Override
    public final Map<NbtCompound, Integer> getChildrenAtLast(Random random) {
        self(random);
        return super.childDiploids;
    }

    private void self(Random random) {
        if (this.selfDone) return;

        int times = (int) this.diploid.computeTrait(getSelfTimesTrait());
        for (int i = 0; i < times; i++) {
            addChild(new Diploid(this.diploid, diploid, random).toNBT());
        }

        this.selfDone = true;
    }

    public void hybrid(Diploid anotherDiploid, Random random) {
        if (this.hybridDone) return;

        int hybridTimesA = (int) this.diploid.computeTrait(getHybridTimesTrait());
        int hybridTimesB = (int) anotherDiploid.computeTrait(getHybridTimesTrait());
        int hybridTimes = Math.min(hybridTimesA, hybridTimesB);

        for (int i = 0; i < hybridTimes; i++) {
            addChild(new Diploid(this.diploid, anotherDiploid, random).toNBT());
        }

        this.hybridDone = true;
    }

    protected abstract Trait getSelfTimesTrait();
    protected abstract Trait getHybridTimesTrait();
    protected abstract Trait getProductionTrait();
    protected abstract Trait getTypeTrait();

    public int getProduction() {
        return (int) diploid.computeTrait(getProductionTrait());
    }

    public int getFruitType() {
        return (int) diploid.computeTrait(getTypeTrait());
    }
}
