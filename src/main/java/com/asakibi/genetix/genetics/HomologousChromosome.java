package com.asakibi.genetix.genetics;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import net.minecraft.util.math.random.Random;

public class HomologousChromosome {

    private final ChromosomeStructure CHROMOSOME_STRUCTURE;
    private final Chromosome CHROMOSOME1;
    private final Chromosome CHROMOSOME2;

    public static HomologousChromosome getRandomHomologousChromosome(
        ChromosomeStructure chromosomeStructure,
        Random random
    ) {
        return new HomologousChromosome(chromosomeStructure, random, false);
    }

    public static HomologousChromosome getNaturalHomologousChromosome(
        ChromosomeStructure chromosomeStructure,
        Random random
    ) {
        return new HomologousChromosome(chromosomeStructure, random, true);
    }

    private HomologousChromosome(ChromosomeStructure chromosomeStructure,
                                Random random,
                                boolean isNatural
    ) {
        CHROMOSOME_STRUCTURE = chromosomeStructure;
        Chromosome chromosome1;
        Chromosome chromosome2;

        if (isNatural) {
            chromosome1 = Chromosome.getNaturalChromosome(CHROMOSOME_STRUCTURE, random);
            chromosome2 = Chromosome.getNaturalChromosome(CHROMOSOME_STRUCTURE, random);
        } else {
            chromosome1 = Chromosome.getRandomChromosome(CHROMOSOME_STRUCTURE, random);
            chromosome2 = Chromosome.getRandomChromosome(CHROMOSOME_STRUCTURE, random);
        }

        Pair<Chromosome, Chromosome> pair = new Pair<>(chromosome1, chromosome2);
        sort(pair);
        CHROMOSOME1 = pair.getLeft();
        CHROMOSOME2 = pair.getRight();
    }

    public HomologousChromosome(HomologousChromosome parent1,
                                HomologousChromosome parent2,
                                Random random) {

        assert  parent1.CHROMOSOME_STRUCTURE == parent2.CHROMOSOME_STRUCTURE;

        CHROMOSOME_STRUCTURE = parent1.CHROMOSOME_STRUCTURE;
        Chromosome gameteFromParent1 = parent1.meiosis(random);
        Chromosome gameteFromParent2 = parent2.meiosis(random);

        Pair<Chromosome, Chromosome> pair = new Pair<>(gameteFromParent1, gameteFromParent2);
        sort(pair);
        CHROMOSOME1 = pair.getLeft();
        CHROMOSOME2 = pair.getRight();
    }

    public HomologousChromosome(HomologousChromosome singleParent, Random random) {
        CHROMOSOME_STRUCTURE = singleParent.CHROMOSOME_STRUCTURE;
        Pair<Chromosome, Chromosome> pair = singleParent.mitosis(random);

        sort(pair);
        CHROMOSOME1 = pair.getLeft();
        CHROMOSOME2 = pair.getRight();
    }

    public HomologousChromosome(ChromosomeStructure chromosomeStructure, NbtCompound nbtCompound) {
        CHROMOSOME_STRUCTURE = chromosomeStructure;
        NbtCompound chromosome1Nbt = nbtCompound.getCompound("1");
        Chromosome chromosome1 = new Chromosome(CHROMOSOME_STRUCTURE, chromosome1Nbt);
        NbtCompound chromosome2Nbt = nbtCompound.getCompound("2");
        Chromosome chromosome2  = new Chromosome(CHROMOSOME_STRUCTURE, chromosome2Nbt);

        Pair<Chromosome, Chromosome> pair = new Pair<>(chromosome1, chromosome2);
        sort(pair);
        CHROMOSOME1 = pair.getLeft();
        CHROMOSOME2 = pair.getRight();
    }

    public Integer getValue(Loci loci) {
        int value1 = CHROMOSOME1.getValue(loci);
        int value2 = CHROMOSOME2.getValue(loci);
        return loci.getDominant(value1, value2);
    }

    /**
     *  Get a child chromosome by randomly choose one chromosome (c1.
     *  the other is c2) from the homologous chromosomes and randomly
     *  apply mutations to the new chromosome (cn).
     *
     *  step 1 - Crossover: Each gene in cn is likely to be replaced
     *  by its allele from c2. The possibility is decided by the
     *  crossover factor.
     *  step 2 - Gene Mutation: Each gene in cn is likely to mutate
     *  and its value is projected by a series of lambda expression
     *  with their weight. The possibility is decided by the gene
     *  mutation factor.
     *
     *  All factors should range from (>=) 0 to (<=) 1.
     */
    private Chromosome meiosis(Random random) {

        boolean r = random.nextBoolean();

        Chromosome c1 = r ? CHROMOSOME1 : CHROMOSOME2;
        Chromosome c2 = r ? CHROMOSOME2 : CHROMOSOME1;

        Chromosome cn = new Chromosome(c1);

        cn.crossover(random, c2, CHROMOSOME_STRUCTURE);
        cn.hereditaryGeneMutation(random);

        return cn;
    }

    private Pair<Chromosome, Chromosome> mitosis(Random random) {

        Chromosome c1 = new Chromosome(CHROMOSOME1);
        Chromosome c2 = new Chromosome(CHROMOSOME2);

        c1.hereditaryGeneMutation(random);
        c2.hereditaryGeneMutation(random);

        return new Pair<>(c1, c2);
    }

    @Override
    public String toString() {
        return "[" + CHROMOSOME1.toString()
            + "], [" + CHROMOSOME2.toString() + "].";
    }

    public String getShortString() {
        return "[" + CHROMOSOME1.getShortString()
            + "], [" + CHROMOSOME2.getShortString() + "].";
    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("1", CHROMOSOME1.toNbt());
        nbtCompound.put("2", CHROMOSOME2.toNbt());
        return nbtCompound;
    }

    private void sort(Pair<Chromosome, Chromosome> pair) {
        Chromosome left = pair.getLeft();
        Chromosome right = pair.getRight();
        if (CHROMOSOME_STRUCTURE.compare(left, right) >= 0) {
            return;
        }

        pair.setLeft(right);
        pair.setRight(left);
    }
}


