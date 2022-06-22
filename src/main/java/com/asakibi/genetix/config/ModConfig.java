package com.asakibi.genetix.config;

import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

public class ModConfig extends Config {
    public ModConfig() {
        super(ConfigOptions.mod("genetix"));
    }

    @Transitive
    private final GeneticVariationConfig geneticVariationConfig = new GeneticVariationConfig();

}
