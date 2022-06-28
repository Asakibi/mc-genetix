package com.asakibi.genetix.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries(includeAll = true)
public class CrossoverConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "crossover";
    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Sheep implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_1 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_2 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_3 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_4 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_5 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_6 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_7 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_8 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double sheep_9 = 0.3;

    }

    @ConfigEntry.Boolean(trueKey = "1", falseKey = "0")
    public static boolean overall_override_on = true;

    @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
    public static double overall_override_crossover_factor = 0.3;

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Tomato implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double tomato_1 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double tomato_2 = 0.3;

    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Garlic implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double garlic_1 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double garlic_2 = 0.3;

    }

    @Transitive
    @ConfigEntries(includeAll = true)
    public static class Scallion implements ConfigGroup {

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double scallion_1 = 0.3;

        @ConfigEntry.BoundedDouble(min = 0.0, max = 1.0)
        public static double scallion_2 = 0.3;

    }
}
