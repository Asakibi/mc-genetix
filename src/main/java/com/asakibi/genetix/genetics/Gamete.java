package com.asakibi.genetix.genetics;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public class Gamete {

    protected final DiploidStructure diploidStructure;
    protected final Map<ChromosomeStructure, Chromosome> chromosomes;


    public Gamete(Diploid diploid, Random random) {
        this.diploidStructure = diploid.diploidStructure;
        this.chromosomes = new HashMap<>();

        diploid.homologousChromosomes.forEach(((chromosomeStructure, homologousChromosome) -> {
            Chromosome gameteChromosome = homologousChromosome.meiosis(random);
            chromosomes.put(chromosomeStructure, gameteChromosome);
        }));
    }

    public Gamete(NbtCompound nbtCompound) {
        this.diploidStructure = DiploidStructure.valueOf(nbtCompound.getString("diploid_structure"));
        this.chromosomes = new HashMap<>();

        NbtCompound chromosomesNbt = nbtCompound.getCompound("chromosomes");
        chromosomesNbt.getKeys().forEach(chromosomeStructure -> {
            NbtCompound chromosomeNbt = chromosomesNbt.getCompound(chromosomeStructure);
            Chromosome chromosome = new Chromosome(chromosomeNbt);
            this.chromosomes.put(ChromosomeStructure.valueOf(chromosomeStructure),
                chromosome);
        });
    }

    public NbtCompound toNBT() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("diploid_structure", this.diploidStructure.name());
        NbtCompound chromosomesNbt = new NbtCompound();
        this.chromosomes.forEach((chromosomeStructure, chromosome) -> {
            chromosomesNbt.put(chromosomeStructure.name(), chromosome.toNbt());
        });
        nbt.put("chromosomes", chromosomesNbt);
        return nbt;
    }
}
