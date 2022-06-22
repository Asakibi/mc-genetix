package com.asakibi.genetix.genetics;

import com.asakibi.genetix.config.HereditaryMutationConfig;
import com.asakibi.genetix.config.NaturalMutationConfig;
import com.mojang.datafixers.util.Function3;
import net.minecraft.util.math.random.Random;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;

public enum Loci {
    SHEEP_A(
        "a",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.a,
        NaturalMutationConfig.Sheep.a,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_B(
        "b",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.b,
        NaturalMutationConfig.Sheep.b,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_C(
        "c",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.c,
        NaturalMutationConfig.Sheep.c,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_D(
        "d",
        2,
        r -> r.nextInt(3),
        r -> r.nextInt(3),
        Mutators.MAP_MOD_3,
        HereditaryMutationConfig.Sheep.d,
        NaturalMutationConfig.Sheep.d,
        DeterminantMapping.ALLELE_3_COMBINATION_4),
    SHEEP_E(
        "e",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.e,
        NaturalMutationConfig.Sheep.e,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    SHEEP_F(
        "f",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.f,
        NaturalMutationConfig.Sheep.f,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    SHEEP_G(
        "g",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.g,
        NaturalMutationConfig.Sheep.g,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_H(
        "h",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.h,
        NaturalMutationConfig.Sheep.h,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_I(
        "i",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.i,
        NaturalMutationConfig.Sheep.i,
        DeterminantMapping.COMBINATION_MAX),
    SHEEP_J(
        "j",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.j,
        NaturalMutationConfig.Sheep.j,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    SHEEP_K(
        "k",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Sheep.k,
        NaturalMutationConfig.Sheep.k,
        DeterminantMapping.ALLELE_2_COMBINATION_3),


    TOMATO_A(
        "a",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Tomato.a,
        NaturalMutationConfig.Tomato.a,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    TOMATO_B(
        "b",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Tomato.b,
        NaturalMutationConfig.Tomato.b,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    TOMATO_C(
        "c",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Tomato.c,
        NaturalMutationConfig.Tomato.c,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    TOMATO_D(
        "d",
        2,
        Generator.DEFAULT,
        Generator.THREE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Tomato.d,
        NaturalMutationConfig.Tomato.d,
        DeterminantMapping.COMBINATION_MAX),


    GARLIC_A(
        "a",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Garlic.a,
        NaturalMutationConfig.Garlic.a,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    GARLIC_B(
        "b",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Garlic.b,
        NaturalMutationConfig.Garlic.b,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    GARLIC_C(
        "c",
        1,
        Generator.DEFAULT,
        Generator.ZERO,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Garlic.c,
        NaturalMutationConfig.Garlic.c,
        DeterminantMapping.ALLELE_2_COMBINATION_3),
    GARLIC_D(
        "d",
        1,
        Generator.DEFAULT,
        Generator.ONE,
        Mutators.MAP_MOD,
        HereditaryMutationConfig.Garlic.d,
        NaturalMutationConfig.Garlic.d,
        DeterminantMapping.COMBINATION_MAX);

    private final String SHORT_NAME;
    private final int SCALE;
    private final int MAX;
    private final Function<Random, Integer> GENERATOR;
    private final Function<Random, Integer> NATURAL_GENERATOR;
    private final Function3<Random, Loci, Integer, Integer> MUTATOR;
    private final double HEREDITARY_MUTATION_FACTOR;
    private final double NATURAL_MUTATION_FACTOR;
    private final IntBinaryOperator DETERMINANT_MAPPING;

    Loci(String shortName,
         int scale,
         Function<Random, Integer> generator,
         Function<Random, Integer> naturalGenerator,
         Function3<Random, Loci, Integer, Integer> mutator,
         double hereditaryMutationFactor,
         double naturalMutationFactor,
         IntBinaryOperator determinantMapping
         ) {
        assert scale >= 1 && scale <= 32;
        SHORT_NAME = shortName;
        SCALE = scale;
        MAX = 0xffffffff >>> (32 - SCALE);
        GENERATOR = generator;
        NATURAL_GENERATOR = naturalGenerator;
        MUTATOR = mutator;
        HEREDITARY_MUTATION_FACTOR = hereditaryMutationFactor;
        NATURAL_MUTATION_FACTOR = naturalMutationFactor;
        DETERMINANT_MAPPING = determinantMapping;
    }

    public boolean checkValue(int value) {
        if (SCALE == 32) return true;

        return ((0xffffffff << SCALE) & value) == 0x0;
    }

    int valueStandardize(int value) {
        return MAX & value;
    }

    public int getHereditaryRandomValue(Random random) {
        return valueStandardize(GENERATOR.apply(random));
    }

    public int getNaturalRandomValue(Random random) {
        return valueStandardize(NATURAL_GENERATOR.apply(random));
    }

    public int getMutatedValue(Random random, Integer oldValue) {
        return MUTATOR.apply(random, this, oldValue);
    }

    public double getHereditaryMutationFactor() {
        return HEREDITARY_MUTATION_FACTOR;
    }

    public double getNaturalMutationFactor() {
        return NATURAL_MUTATION_FACTOR;
    }

    public int getDominant(int value1, int value2) {
        return DETERMINANT_MAPPING.applyAsInt(value1, value2);
    }

    @Override
    public String toString() {
        return name();
    }

    public String getShortName() {
        return SHORT_NAME;
    }
}

class Mutators {
    public static final Function3<Random, Loci, Integer, Integer> MAP_MOD = (random, loci, i) ->
        loci.valueStandardize(i + random.nextInt());
    public static final Function3<Random, Loci, Integer, Integer> MAP_MOD_3 = (random, loci, i) ->
        (i + random.nextInt(3)) % 3;

}

class DeterminantMapping {
    /**
     *       2   1   0
     *       Σ   σ   ς
     *  2 Σ  3   2   1
     *  1 σ  2   0   0
     *  0 ς  1   0   0
     */
    public static final IntBinaryOperator ALLELE_3_COMBINATION_4 = ((i1, i2) -> {
        if (i1 == 2 && i2 == 2) {
            return 3;
        }
        if (i1 == 2 || i2 == 2) {
            return (i1 == 1 || i2 == 1) ? 2 : 1;
        }
        return 0;
    });

    /**
     *       1  0
     *       A  a
     *  1 A  2  1
     *  0 a  1  0
     */
    public static final IntBinaryOperator ALLELE_2_COMBINATION_3 = Integer::sum;

    /**
     *       1  0
     *       A  a
     *  1 A  1  1
     *  0 a  1  0
     */
    public static final IntBinaryOperator COMBINATION_MAX = Math::max;
}

class Generator {
    public static final Function<Random, Integer> DEFAULT = Random::nextInt;
    public static final Function<Random, Integer> THREE = (r) -> 3;
    public static final Function<Random, Integer> ONE = (r) -> 1;
    public static final Function<Random, Integer> ZERO = (r) -> 0;
}
