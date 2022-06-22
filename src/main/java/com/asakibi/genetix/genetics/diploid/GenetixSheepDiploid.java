package com.asakibi.genetix.genetics.diploid;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

public class GenetixSheepDiploid extends Diploid {

    public GenetixSheepDiploid(Random random, boolean isNatural) {
        super(DiploidStructure.SHEEP, random, isNatural);
    }

    public GenetixSheepDiploid(Diploid parent1, Diploid parent2,
                               Random random) {
        super(parent1, parent2, random);
    }

    public GenetixSheepDiploid(NbtCompound nbtCompound) {
        super(nbtCompound);
    }
}
