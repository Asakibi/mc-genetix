package com.asakibi.genetix.genetics;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.stream.Collectors;

public class Diploid {
    protected final DiploidStructure diploidStructure;
    protected final Map<ChromosomeStructure, HomologousChromosome> homologousChromosomes;

    private final String string;
    private final LinkedHashMap<ChromosomeStructure, String> stringMap;

    public Diploid(DiploidStructure diploidStructure, Random random, boolean isNatural) {
        this.diploidStructure = diploidStructure;
        if (isNatural) {
            this.homologousChromosomes = this.diploidStructure.getChromosomeStructures().stream().collect(
                Collectors.toMap(e -> e,
                    e -> HomologousChromosome.getNaturalHomologousChromosome(e, random)));
        } else {
            this.homologousChromosomes = this.diploidStructure.getChromosomeStructures().stream().collect(
                Collectors.toMap(e -> e,
                    e -> HomologousChromosome.getRandomHomologousChromosome(e, random)));
        }
        this.string = getString();
        this.stringMap = getChromosomeStringMap();
    }

    public Diploid(Diploid parent1, Diploid parent2,
                   Random random) {
        assert parent1.diploidStructure == parent2.diploidStructure;
        this.diploidStructure = parent1.diploidStructure;
        this.homologousChromosomes = diploidStructure.getChromosomeStructures().stream().collect(
            Collectors.toMap(
                e -> e,
                e -> new HomologousChromosome(
                    parent1.homologousChromosomes.get(e),
                    parent2.homologousChromosomes.get(e),
                    random
                )
            )
        );
        this.string = getString();
        this.stringMap = getChromosomeStringMap();
    }

    public Diploid(Diploid singleParent, Random random) {
        this.diploidStructure = singleParent.diploidStructure;
        this.homologousChromosomes = new HashMap<>();
        singleParent.homologousChromosomes.forEach(((chromosomeStructure, homologousChromosome) -> {
            HomologousChromosome newHomologousChromosome =
                new HomologousChromosome(homologousChromosome, random);
            this.homologousChromosomes.put(chromosomeStructure, newHomologousChromosome);
        }));
        this.string = getString();
        this.stringMap = getChromosomeStringMap();
    }

    public Diploid(NbtCompound nbt) {
        String diploidStructureString = nbt.getString("diploid_structure");
        this.diploidStructure = DiploidStructure.valueOf(diploidStructureString);
        NbtCompound chromosomesNbt = (NbtCompound) nbt.get("chromosomes");
        this.homologousChromosomes = diploidStructure.getChromosomeStructures().stream().collect(
            Collectors.toMap(
                e -> e,
                e -> new HomologousChromosome(e, chromosomesNbt.getCompound(e.toString()))
            )
        );
        this.string = getString();
        this.stringMap = getChromosomeStringMap();
    }

    public Diploid(Gamete g1, Gamete g2) {
        assert g1.diploidStructure == g2.diploidStructure;
        this.diploidStructure = g1.diploidStructure;

        this.homologousChromosomes = g1.diploidStructure.getChromosomeStructures().stream().collect(
            Collectors.toMap(
                e -> e,
                e -> new HomologousChromosome(e, g1.chromosomes.get(e), g2.chromosomes.get(e))
            )
        );
        this.string = getString();
        this.stringMap = getChromosomeStringMap();
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

    // Get the value of the loci.
    public Integer getGene(Loci loci) {
        ChromosomeStructure chromosomeStructure = this.diploidStructure.getChromosomeStructureByLoci(loci);
        HomologousChromosome homologousChromosome = this.homologousChromosomes.get(chromosomeStructure);
        return homologousChromosome.getValue(loci);
    }

    @Override
    public String toString() {
        return string;
    }

    public LinkedHashMap<ChromosomeStructure, String> toStringMap() {
        return stringMap;
    }

    private String getString() {
        return this.homologousChromosomes.entrySet().stream()
            .map(entry -> entry.getKey().getShortName() + ": " + entry.getValue().toString())
            .collect(Collectors.joining("\n"));
    }

    private String getShortString() {
        return this.homologousChromosomes.entrySet().stream()
            .map(entry -> entry.getKey().getShortName() + ": " + entry.getValue().getShortString())
            .collect(Collectors.joining("\n"));
    }

    private LinkedHashMap<ChromosomeStructure, String> getChromosomeStringMap() {
        LinkedHashSet<ChromosomeStructure> structures = (LinkedHashSet<ChromosomeStructure>) diploidStructure.getChromosomeStructures();
        LinkedHashMap<ChromosomeStructure, String> map = new LinkedHashMap<>();
        structures.forEach((structure) -> {
                map.put(structure, this.homologousChromosomes.get(structure).getShortString());
            });
        return map;
    }

    public NbtCompound toNBT() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("diploid_structure", this.diploidStructure.name());
        NbtCompound chromosomesNbt = new NbtCompound();
        this.homologousChromosomes.forEach((chromosomeStructure, homologousChromosome) -> {
            chromosomesNbt.put(chromosomeStructure.name(), homologousChromosome.toNbt());
        });
        nbtCompound.put("chromosomes", chromosomesNbt);
        return nbtCompound;
    }

    public String getLowerCaseStructureName() {
        return this.diploidStructure.name().toLowerCase();
    }
}
