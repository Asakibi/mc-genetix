package com.asakibi.genetix.genetics;

import com.asakibi.genetix.config.CrossoverConfig;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public enum ChromosomeStructure {

    SHEEP_1("1", new Loci[]{Loci.SHEEP_A}, CrossoverConfig.Sheep.sheep_1),
    SHEEP_2("2", new Loci[]{Loci.SHEEP_B}, CrossoverConfig.Sheep.sheep_2),
    SHEEP_3("3", new Loci[]{Loci.SHEEP_C}, CrossoverConfig.Sheep.sheep_3),
    SHEEP_4("4", new Loci[]{Loci.SHEEP_D}, CrossoverConfig.Sheep.sheep_4),
    SHEEP_5("5", new Loci[]{Loci.SHEEP_E, Loci.SHEEP_J}, CrossoverConfig.Sheep.sheep_5),
    SHEEP_6("6", new Loci[]{Loci.SHEEP_F, Loci.SHEEP_K}, CrossoverConfig.Sheep.sheep_6),
    SHEEP_7("7", new Loci[]{Loci.SHEEP_G}, CrossoverConfig.Sheep.sheep_7),
    SHEEP_8("8", new Loci[]{Loci.SHEEP_H}, CrossoverConfig.Sheep.sheep_8),
    SHEEP_9("9", new Loci[]{Loci.SHEEP_I}, CrossoverConfig.Sheep.sheep_9),

    TOMATO_1("1", new Loci[]{Loci.TOMATO_A, Loci.TOMATO_B, Loci.TOMATO_C}, CrossoverConfig.Tomato.tomato_1),
    TOMATO_2("2", new Loci[]{Loci.TOMATO_D}, CrossoverConfig.Tomato.tomato_2),

    GARLIC_1("1", new Loci[]{Loci.GARLIC_A, Loci.GARLIC_B, Loci.GARLIC_C}, CrossoverConfig.Garlic.garlic_1),
    GARLIC_2("2", new Loci[]{Loci.GARLIC_D}, CrossoverConfig.Garlic.garlic_2);

    private final String SHORT_NAME;
    private final LinkedHashSet<Loci> LOCUS;
    private final double CROSSOVER_FACTOR;

    ChromosomeStructure(String shortName, Loci[] locus, double crossoverFactor) {
        SHORT_NAME = shortName;
        LOCUS = new LinkedHashSet<>(List.of(locus));
        CROSSOVER_FACTOR = crossoverFactor;
    }

    public Set<Loci> getLocus() {
        return LOCUS;
    }

    public double getCrossoverFactor() {
        return CROSSOVER_FACTOR;
    }

    public String toString() {
        return name();
    }

    public String getShortName() {
        return SHORT_NAME;
    }

    public int compare(Chromosome chromosome1, Chromosome chromosome2) {
        for (Loci loci : LOCUS) {
            int value1 = chromosome1.getValue(loci);
            int value2 = chromosome2.getValue(loci);
            if (value1 == value2) continue;
            return value1 > value2 ? 1 : -1;
        }
        return 0;
    }
}
