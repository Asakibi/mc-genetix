package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class GeneticVariationConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "genetic_variation";
    }

    @Transitive
    private final NaturalMutationConfig naturalMutationConfig = new NaturalMutationConfig();

    @Transitive
    private final HereditaryMutationConfig hereditaryMutationConfig = new HereditaryMutationConfig();

    @Transitive
    private final CrossoverConfig crossoverConfig = new CrossoverConfig();

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean natural_mutation_on = true;

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean hereditary_mutation_on = true;

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean crossover_on = true;
}
