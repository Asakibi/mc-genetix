package com.asakibi.genetix.genetics;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import net.minecraft.util.math.random.Random;

public class HomologousChromosome {

    private final ChromosomeStructure chromosomeStructure;
    private final Chromosome chromosome1;
    private final Chromosome chromosome2;

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
        this.chromosomeStructure = chromosomeStructure;
        Chromosome chromosome1;
        Chromosome chromosome2;

        if (isNatural) {
            chromosome1 = Chromosome.getNaturalChromosome(this.chromosomeStructure, random);
            chromosome2 = Chromosome.getNaturalChromosome(this.chromosomeStructure, random);
        } else {
            chromosome1 = Chromosome.getRandomChromosome(this.chromosomeStructure, random);
            chromosome2 = Chromosome.getRandomChromosome(this.chromosomeStructure, random);
        }

        Pair<Chromosome, Chromosome> pair = new Pair<>(chromosome1, chromosome2);
        sort(pair);
        this.chromosome1 = pair.getLeft();
        this.chromosome2 = pair.getRight();
    }

    public HomologousChromosome(HomologousChromosome parent1,
                                HomologousChromosome parent2,
                                Random random) {

        assert  parent1.chromosomeStructure == parent2.chromosomeStructure;

        this.chromosomeStructure = parent1.chromosomeStructure;
        Chromosome gameteFromParent1 = parent1.meiosis(random);
        Chromosome gameteFromParent2 = parent2.meiosis(random);

        Pair<Chromosome, Chromosome> pair = new Pair<>(gameteFromParent1, gameteFromParent2);
        sort(pair);
        this.chromosome1 = pair.getLeft();
        this.chromosome2 = pair.getRight();
    }

    public HomologousChromosome(HomologousChromosome singleParent, Random random) {
        this.chromosomeStructure = singleParent.chromosomeStructure;
        Pair<Chromosome, Chromosome> pair = singleParent.mitosis(random);

        sort(pair);
        this.chromosome1 = pair.getLeft();
        this.chromosome2 = pair.getRight();
    }

    public HomologousChromosome(ChromosomeStructure chromosomeStructure, NbtCompound nbtCompound) {
        this.chromosomeStructure = chromosomeStructure;
        NbtCompound chromosome1Nbt = nbtCompound.getCompound("1");
        Chromosome chromosome1 = new Chromosome(this.chromosomeStructure, chromosome1Nbt);
        NbtCompound chromosome2Nbt = nbtCompound.getCompound("2");
        Chromosome chromosome2  = new Chromosome(this.chromosomeStructure, chromosome2Nbt);

        Pair<Chromosome, Chromosome> pair = new Pair<>(chromosome1, chromosome2);
        sort(pair);
        this.chromosome1 = pair.getLeft();
        this.chromosome2 = pair.getRight();
    }

    public HomologousChromosome(ChromosomeStructure chromosomeStructure, Chromosome gameteChromosome1, Chromosome gameteChromosome2) {
        this.chromosomeStructure = chromosomeStructure;

        Chromosome childChromosome1 = new Chromosome(gameteChromosome1);
        Chromosome childChromosome2 = new Chromosome(gameteChromosome2);

        Pair<Chromosome, Chromosome> pair = new Pair<>(childChromosome1, childChromosome2);
        sort(pair);
        this.chromosome1 = pair.getLeft();
        this.chromosome2 = pair.getRight();
    }

    public Integer getValue(Loci loci) {
        int value1 = this.chromosome1.getValue(loci);
        int value2 = this.chromosome2.getValue(loci);
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
    protected Chromosome meiosis(Random random) {

        boolean r = random.nextBoolean();

        Chromosome c1 = r ? this.chromosome1 : this.chromosome2;
        Chromosome c2 = r ? this.chromosome2 : this.chromosome1;

        Chromosome cn = new Chromosome(c1);

        cn.crossover(random, c2, this.chromosomeStructure);
        cn.hereditaryGeneMutation(random);

        return cn;
    }

    private Pair<Chromosome, Chromosome> mitosis(Random random) {

        Chromosome c1 = new Chromosome(this.chromosome1);
        Chromosome c2 = new Chromosome(this.chromosome2);

        c1.hereditaryGeneMutation(random);
        c2.hereditaryGeneMutation(random);

        return new Pair<>(c1, c2);
    }

    @Override
    public String toString() {
        return "[" + this.chromosome1.toString()
            + "], [" + this.chromosome2.toString() + "].";
    }

    public String getShortString() {
        return "[" + this.chromosome1.getShortString()
            + "], [" + this.chromosome2.getShortString() + "].";
    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("1", this.chromosome1.toNbt());
        nbtCompound.put("2", this.chromosome2.toNbt());
        return nbtCompound;
    }

    private void sort(Pair<Chromosome, Chromosome> pair) {
        Chromosome left = pair.getLeft();
        Chromosome right = pair.getRight();
        if (this.chromosomeStructure.compare(left, right) >= 0) {
            return;
        }

        pair.setLeft(right);
        pair.setRight(left);
    }
}


