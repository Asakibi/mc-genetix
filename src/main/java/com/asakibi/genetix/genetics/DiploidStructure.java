package com.asakibi.genetix.genetics;

import org.checkerframework.checker.units.qual.C;

import java.util.*;

public enum DiploidStructure {

    SHEEP(new ChromosomeStructure[]{
        ChromosomeStructure.SHEEP_1,
        ChromosomeStructure.SHEEP_2,
        ChromosomeStructure.SHEEP_3,
        ChromosomeStructure.SHEEP_4,
        ChromosomeStructure.SHEEP_5,
        ChromosomeStructure.SHEEP_6,
        ChromosomeStructure.SHEEP_7,
        ChromosomeStructure.SHEEP_8,
        ChromosomeStructure.SHEEP_9
    }, new Trait[]{
        Trait.SHEEP_COLOR,
        Trait.SHEEP_MAX_HEALTH,
        Trait.SHEEP_SPEED
    }),

    TOMATO(new ChromosomeStructure[]{
        ChromosomeStructure.TOMATO_1,
        ChromosomeStructure.TOMATO_2
    }, new Trait[]{
        Trait.TOMATO_PRODUCTION,
        Trait.TOMATO_SELF_SEED_PRODUCTION,
        Trait.TOMATO_HYBRID_SEED_PRODUCTION,
        Trait.TOMATO_TYPE
    }),

    GARLIC(new ChromosomeStructure[]{
        ChromosomeStructure.GARLIC_1,
        ChromosomeStructure.GARLIC_2
    }, new Trait[]{
        Trait.GARLIC_PRODUCTION,
        Trait.GARLIC_TYPE
    });

    private final LinkedHashSet<ChromosomeStructure> CHROMOSOME_STRUCTURES;
    private final Set<Trait> TRAITS;

    private final Map<Loci, ChromosomeStructure> LOCI_TO_CHROMOSOME_STRUCTURE_MAP;

    DiploidStructure(ChromosomeStructure[] chromosomeStructures,
                     Trait[] traits) {
        CHROMOSOME_STRUCTURES = new LinkedHashSet<>(List.of(chromosomeStructures));
        TRAITS = Set.of(traits);

        LOCI_TO_CHROMOSOME_STRUCTURE_MAP = new HashMap<>();
        for (ChromosomeStructure chromosomeStructure : chromosomeStructures) {
            for (Loci loci : chromosomeStructure.getLocus())
                LOCI_TO_CHROMOSOME_STRUCTURE_MAP.put(loci, chromosomeStructure);
        }
    }

    // Find where chromosome the loci is on.
    public ChromosomeStructure getChromosomeStructureByLoci(Loci loci) {
        return LOCI_TO_CHROMOSOME_STRUCTURE_MAP.get(loci);
    }

    public Set<ChromosomeStructure> getChromosomeStructures() {
        return CHROMOSOME_STRUCTURES;
    }

    public Set<Trait> getTraits() {
        return TRAITS;
    }

    @Override
    public String toString() {
        return name();
    }
}
