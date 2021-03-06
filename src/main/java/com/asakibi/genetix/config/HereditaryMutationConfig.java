package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class HereditaryMutationConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "hereditary_mutation";
    }

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean overall_override_on = true;

    @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
    public static double overall_basic_mutation_factor = 0.3;

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean temperature_based_mutation_on = true;

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean chemical_based_mutation_on = true;

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

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Scallion implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double a = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double b = 0.3;
    }
}
