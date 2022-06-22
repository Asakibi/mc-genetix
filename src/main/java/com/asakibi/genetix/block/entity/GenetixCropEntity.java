package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public abstract class GenetixCropEntity extends BlockEntity {
    protected Diploid diploid;
    protected Map<NbtCompound, Integer> childDiploids;
    protected final DiploidStructure DIPLOID_STRUCTURE;

    public GenetixCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, DiploidStructure diploidStructure) {
        super(type, pos, state);
        this.DIPLOID_STRUCTURE = diploidStructure;
        childDiploids = new HashMap<>();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound nbtDiploid = nbt.getCompound("diploid");
        this.diploid = nbtDiploid == null ? null : new Diploid(nbtDiploid);

        NbtCompound nbtChildren = nbt.getCompound("child_diploids");
        if (nbtChildren != null) {
            nbtChildren.getKeys().forEach(e -> {
                NbtCompound nbtChild = (NbtCompound) nbtChildren.get(e);
                if (nbtChild != null) {
                    NbtCompound nbtChildDiploid = nbtChild.getCompound("diploid");
                    int num = nbtChild.getInt("num");
                    childDiploids.put(nbtChildDiploid, num);
                }
            });
        }

        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.put("diploid", this.diploid.toNBT());

        NbtCompound nbtChildDiploids = new NbtCompound();

        int index = 0;

        childDiploids.forEach((diploid, num) -> {
            NbtCompound nbtChild = new NbtCompound();
            nbtChild.put("diploid", diploid);
            nbtChild.putInt("num", num);
            nbtChildDiploids.put(String.valueOf(index), nbtChild);
        });

        nbt.put("child_diploids", nbtChildDiploids);
    }

    public void generateDiploid(Random random, boolean isNatural) {
        this.diploid = new Diploid(this.DIPLOID_STRUCTURE, random, isNatural);
    }

    public void setDiploid(NbtCompound nbtCompound) {
        this.diploid = new Diploid(nbtCompound);
        markDirty();
    }

    public Diploid getDiploid() {
        return this.diploid;
    }

    protected void addChild(NbtCompound nbtDiploid) {
        Integer existedTime = childDiploids.get(nbtDiploid);
        existedTime = existedTime == null ? 0 : existedTime;

        childDiploids.put(nbtDiploid, existedTime + 1);
    }

    public abstract Map<NbtCompound, Integer> getChildrenAtLast(Random random);
}
