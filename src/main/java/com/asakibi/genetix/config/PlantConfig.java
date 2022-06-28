package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class PlantConfig implements ConfigGroup {

    /**
     *  true: Sowable products get its own genes from its parent.
     *      When sowed, the genes of new plants are the same
     *      as the product's genes.
     *  false: Sowable products hold no genes information at all.
     *      When sowed, the genes of new plants are randomly generated
     *      by its generator.
     *  This config will not affect the seeds' genes.
     */
    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean product_getting_genes = true;

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class HeredityFruit implements ConfigGroup {
        @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
        public static boolean tomato = true;

        @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
        public static boolean scallion = true;
    }

    @ConfigEntry.BoundedInteger(min = 1, max = 64)
    public static int cotton_swab_capacity = 3;

    @Override
    public String getId() {
        return "plant";
    }

}
