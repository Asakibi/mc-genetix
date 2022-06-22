package com.asakibi.genetix;

import com.asakibi.genetix.genetics.Loci;
import org.slf4j.LoggerFactory;

public class Logger {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mod.NAME);

    public static void log(String s) {
        LOGGER.info("\n" + s);
    }

    public static void log(String info, String ... object) {
        log(info + ":\n\t"
            + String.join("\n\t", object));
    }

    public static void logRandomBoolean(boolean b, double factor, double r) {
        log("random boolean", "value:" + b, "factor:" + factor, "generated double:" + r);
    }

    private static String oldToNew(String key, int oldValue, int newValue) {
        return key + ":" + oldValue + "->" + newValue;
    }

    public static void logNaturalMutation(Loci loci, int oldValue, int newValue) {
        log("natural mutation", oldToNew(loci.toString(), oldValue, newValue));
    }

    public static void logHereditaryMutation(Loci loci, int oldValue, int newValue) {
        log("hereditary mutation", oldToNew(loci.toString(), oldValue, newValue));
    }

    public static void logCrossover(Loci loci, int oldValue, int newValue) {
        log("crossover", oldToNew(loci.toString(), oldValue, newValue));
    }

    public static void logNaturalMutation(Loci loci) {
        log("natural mutation (failed)", loci.toString());
    }

    public static void logHereditaryMutation(Loci loci) {
        log("hereditary mutation (failed)", loci.toString());
    }

    public static void logCrossover(Loci loci) {
        log("crossover (failed)", loci.toString());
    }
}
