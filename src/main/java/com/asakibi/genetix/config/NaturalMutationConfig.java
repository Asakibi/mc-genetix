package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class NaturalMutationConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "natural_mutation";
    }

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean overall_override_on = true;

    @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
    public static double overall_override_mutation_factor = 0.3;

//    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
//    public static boolean species_override = false;
//
//    @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
//    public static double species_override_mutation_factor = 1.0;

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Sheep implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double a = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double b = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double c = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double d = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double e = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double f = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double g = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double h = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double i = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double j = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double k = 0.3;
    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Tomato implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double a = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double b = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double c = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double d = 0.3;
    }


    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Garlic implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double a = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double b = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double c = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double d = 0.3;
    }
}
