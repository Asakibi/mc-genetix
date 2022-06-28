package com.asakibi.genetix.genetics;

import com.asakibi.genetix.Logger;
import com.asakibi.genetix.config.CrossoverConfig;
import com.asakibi.genetix.config.GeneticVariationConfig;
import com.asakibi.genetix.config.HereditaryMutationConfig;
import com.asakibi.genetix.config.NaturalMutationConfig;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.asakibi.genetix.Logger.*;

public class Chromosome {
    private final Map<Loci, Integer> GENES;

    public Chromosome(Chromosome chromosome) {
        GENES = new HashMap<>(chromosome.GENES);
    }

    public static Chromosome getRandomChromosome(ChromosomeStructure chromosomeStructure,
                                                 Random random) {
        return new Chromosome(chromosomeStructure, random, false);
    }

    public static Chromosome getNaturalChromosome(ChromosomeStructure chromosomeStructure,
                                                  Random random) {
        return new Chromosome(chromosomeStructure, random, true);
    }

    private Chromosome(ChromosomeStructure chromosomeStructure,
                       Random random,
                       boolean isNatural
    ) {
        if (isNatural) {
            GENES = chromosomeStructure.getLocus().stream().collect(
                Collectors.toMap(e -> e, e -> e.getNaturalRandomValue(random))
            );
            this.naturalGeneMutation(random);
        }

        else
            GENES = chromosomeStructure.getLocus().stream().collect(
                Collectors.toMap(e -> e, e -> e.getHereditaryRandomValue(random))
            );
    }

    public Chromosome(ChromosomeStructure chromosomeStructure,
                      NbtCompound nbtCompound) {
        GENES = chromosomeStructure.getLocus().stream().collect(
            Collectors.toMap(e -> e, e -> nbtCompound.getInt(e.name()))
        );
    }

    public int getValue(Loci loci) {
        return GENES.get(loci);
    }

    protected void crossover(Random random,
                           Chromosome chromosome,
                           ChromosomeStructure chromosomeStructure
                           ) {
        if (GeneticVariationConfig.crossover_on) {
            if (CrossoverConfig.overall_override_on) {
                GENES.replaceAll((loci, value) -> {
                        boolean flag = getRandomBoolean(random, CrossoverConfig.overall_override_crossover_factor);
                        if (flag) {
                            int newValue = chromosome.getValue(loci);
                            Logger.logCrossover(loci, value, newValue);
                            return newValue;
                        } else {
                            Logger.logCrossover(loci);
                            return value;
                        }
                    }
                );
            }
            else {
                GENES.replaceAll((loci, value) -> {
                    boolean flag = getRandomBoolean(random, chromosomeStructure.getCrossoverFactor());
                    if (flag) {
                        int newValue = chromosome.getValue(loci);
                        Logger.logCrossover(loci, value, newValue);
                        return newValue;
                    } else {
                        Logger.logCrossover(loci);
                        return value;
                    }
                }
                );
            }
        }

    }

    protected void hereditaryGeneMutation(Random random) {
        if (GeneticVariationConfig.hereditary_mutation_on) {
            if (HereditaryMutationConfig.overall_override_on) {
                GENES.replaceAll((loci, value) -> {
                    boolean flag = getRandomBoolean(random, HereditaryMutationConfig.overall_basic_mutation_factor);
                    if (flag) {
                        int newValue = loci.getMutatedValue(random, getValue(loci));
                        logHereditaryMutation(loci, value, newValue);
                        return newValue;
                    } else {
                        logHereditaryMutation(loci);
                        return value;
                    }
                });
            }
            else {
                GENES.replaceAll((loci, value) -> {
                        boolean flag = getRandomBoolean(random, loci.getHereditaryMutationFactor());
                        if (flag) {
                            int newValue = loci.getMutatedValue(random, getValue(loci));
                            logHereditaryMutation(loci, value, newValue);
                            return newValue;
                        } else {
                            logHereditaryMutation(loci);
                            return value;
                        }
                    });
            }
        }
    }

    private void naturalGeneMutation(Random random) {
        if (GeneticVariationConfig.natural_mutation_on) {
            if (NaturalMutationConfig.overall_override_on) {
                GENES.replaceAll((loci, value) -> {
                        boolean flag = getRandomBoolean(random, NaturalMutationConfig.overall_override_mutation_factor);
                        if (flag) {
                            int newValue = loci.getMutatedValue(random, getValue(loci));
                            logNaturalMutation(loci, value, newValue);
                            return newValue;
                        } else {
                            logNaturalMutation(loci);
                            return value;
                        }
                    });
            }
            else {
                GENES.replaceAll((loci, value) -> {
                        boolean flag = getRandomBoolean(random, loci.getNaturalMutationFactor());
                        if (flag) {
                            int newValue = loci.getMutatedValue(random, getValue(loci));
                            logNaturalMutation(loci, value, newValue);
                            return newValue;
                        } else {
                            logNaturalMutation(loci);
                            return value;
                        }
                    });
            }
        }
    }

    private boolean getRandomBoolean(Random random, double factor) {
        double r = random.nextDouble();
        boolean flag = r < factor;
        logRandomBoolean(flag, factor, r);
        return r < factor;
    }

    @Override
    public String toString() {
        return GENES.entrySet().stream()
            .map(entry -> entry.getKey().name() + "=" + entry.getValue())
            .collect(Collectors.joining(", ")
            );
    }

    public String getShortString() {
        return GENES.entrySet().stream()
            .map(entry -> entry.getKey().getShortName() + "=" + entry.getValue())
            .collect(Collectors.joining(", ")
            );
    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        GENES.forEach((loci, value) -> nbtCompound.putInt(loci.name(), value));
        return nbtCompound;
    }
}
