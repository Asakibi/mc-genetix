package com.asakibi.genetix.genetics;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.stream.Collectors;

public class Diploid {
    protected final DiploidStructure DIPLOID_STRUCTURE;
    protected final Map<ChromosomeStructure, HomologousChromosome> HOMOLOGOUS_CHROMOSOMES;

    private final String STRING;
    private final LinkedHashMap<ChromosomeStructure, String> STRING_MAP;

    public Diploid(DiploidStructure diploidStructure, Random random, boolean isNatural) {
        DIPLOID_STRUCTURE = diploidStructure;
        if (isNatural) {
            HOMOLOGOUS_CHROMOSOMES = DIPLOID_STRUCTURE.getChromosomeStructures().stream().collect(
                Collectors.toMap(e -> e,
                    e -> HomologousChromosome.getNaturalHomologousChromosome(e, random)));
        } else {
            HOMOLOGOUS_CHROMOSOMES = DIPLOID_STRUCTURE.getChromosomeStructures().stream().collect(
                Collectors.toMap(e -> e,
                    e -> HomologousChromosome.getRandomHomologousChromosome(e, random)));
        }
        STRING = getString();
        STRING_MAP = getChromosomeStringMap();
    }

    public Diploid(Diploid parent1, Diploid parent2,
                   Random random) {
        assert parent1.DIPLOID_STRUCTURE == parent2.DIPLOID_STRUCTURE;
        DIPLOID_STRUCTURE = parent1.DIPLOID_STRUCTURE;
        HOMOLOGOUS_CHROMOSOMES = DIPLOID_STRUCTURE.getChromosomeStructures().stream().collect(
            Collectors.toMap(
                e -> e,
                e -> new HomologousChromosome(
                    parent1.HOMOLOGOUS_CHROMOSOMES.get(e),
                    parent2.HOMOLOGOUS_CHROMOSOMES.get(e),
                    random
                )
            )
        );
        STRING = getString();
        STRING_MAP = getChromosomeStringMap();
    }

    public Diploid(Diploid singleParent, Random random) {
        DIPLOID_STRUCTURE = singleParent.DIPLOID_STRUCTURE;
        HOMOLOGOUS_CHROMOSOMES = new HashMap<>();
        singleParent.HOMOLOGOUS_CHROMOSOMES.forEach(((chromosomeStructure, homologousChromosome) -> {
            HomologousChromosome newHomologousChromosome =
                new HomologousChromosome(homologousChromosome, random);
            this.HOMOLOGOUS_CHROMOSOMES.put(chromosomeStructure, newHomologousChromosome);
        }));
        STRING = getString();
        STRING_MAP = getChromosomeStringMap();
    }

    public Diploid(NbtCompound nbt) {
        String diploidStructureString = nbt.getString("diploid_structure");
        DIPLOID_STRUCTURE = DiploidStructure.valueOf(diploidStructureString);
        NbtCompound chromosomesNbt = (NbtCompound) nbt.get("chromosomes");
        HOMOLOGOUS_CHROMOSOMES = DIPLOID_STRUCTURE.getChromosomeStructures().stream().collect(
            Collectors.toMap(
                e -> e,
                e -> new HomologousChromosome(e, chromosomesNbt.getCompound(e.toString()))
            )
        );
        STRING = getString();
        STRING_MAP = getChromosomeStringMap();
    }

    // Get the trait value by the trait object.
    public Object computeTrait(Trait trait) {
        List<Loci> locus = trait.getPolygenes();
        Map<Loci, Integer> genes = locus.stream().collect(
            Collectors.toMap(
                e -> e, this::getGene
            )
        );
        return trait.getValue(genes);
    }

    // Get all values of traits of this diploid.
    public Map<Trait, Object> computeTraits() {
        return DIPLOID_STRUCTURE.getTraits().stream().collect(
            Collectors.toMap(
                e -> e,
                this::computeTrait
            )
        );
    }

    // Get the value of the loci.
    public Integer getGene(Loci loci) {
        ChromosomeStructure chromosomeStructure = DIPLOID_STRUCTURE.getChromosomeStructureByLoci(loci);
        HomologousChromosome homologousChromosome = HOMOLOGOUS_CHROMOSOMES.get(chromosomeStructure);
        return homologousChromosome.getValue(loci);
    }

    @Override
    public String toString() {
        return STRING;
    }

    public LinkedHashMap<ChromosomeStructure, String> toStringMap() {
        return STRING_MAP;
    }

    private String getString() {
        return HOMOLOGOUS_CHROMOSOMES.entrySet().stream()
            .map(entry -> entry.getKey().getShortName() + ": " + entry.getValue().toString())
            .collect(Collectors.joining("\n"));
    }

    private String getShortString() {
        return HOMOLOGOUS_CHROMOSOMES.entrySet().stream()
            .map(entry -> entry.getKey().getShortName() + ": " + entry.getValue().getShortString())
            .collect(Collectors.joining("\n"));
    }

    private LinkedHashMap<ChromosomeStructure, String> getChromosomeStringMap() {
        LinkedHashSet<ChromosomeStructure> structures = (LinkedHashSet<ChromosomeStructure>) DIPLOID_STRUCTURE.getChromosomeStructures();
        LinkedHashMap<ChromosomeStructure, String> map = new LinkedHashMap<>();
        structures.forEach((structure) -> {
                map.put(structure, HOMOLOGOUS_CHROMOSOMES.get(structure).getShortString());
            });
        return map;
    }

    public NbtCompound toNBT() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("diploid_structure", DIPLOID_STRUCTURE.name());
        NbtCompound chromosomesNbt = new NbtCompound();
        HOMOLOGOUS_CHROMOSOMES.forEach((chromosomeStructure, homologousChromosome) -> {
            chromosomesNbt.put(chromosomeStructure.name(), homologousChromosome.toNbt());
        });
        nbtCompound.put("chromosomes", chromosomesNbt);
        return nbtCompound;
    }

    public String getLowerCaseStructureName() {
        return DIPLOID_STRUCTURE.name().toLowerCase();
    }
}
