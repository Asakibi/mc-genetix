package com.asakibi.genetix.genetics;

import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum Trait {

    SHEEP_COLOR(
        new Loci[]{Loci.SHEEP_A, Loci.SHEEP_B, Loci.SHEEP_C, Loci.SHEEP_D},
        locus -> {
            int a = locus.get(Loci.SHEEP_A);
            int b = locus.get(Loci.SHEEP_B);
            int c = locus.get(Loci.SHEEP_C);
            int d = locus.get(Loci.SHEEP_D);

            int flag = ((a << 2) & 0b100) | ((b << 1) & 0b10) | (c & 0b1);

            if (d == 0)
                return switch (flag) {
                    case 0b000 -> DyeColor.LIGHT_GRAY;
                    case 0b001 -> DyeColor.ORANGE;
                    case 0b010 -> DyeColor.PURPLE;
                    case 0b011 -> DyeColor.PINK;
                    case 0b100 -> DyeColor.GREEN;
                    case 0b101 -> DyeColor.LIME;
                    case 0b110 -> DyeColor.LIGHT_BLUE;
                    case 0b111 -> DyeColor.WHITE;
                    default -> DyeColor.WHITE;
            };
            if (d == 1)
                return switch (flag) {
                    case 0b000 -> DyeColor.GRAY;
                    case 0b001 -> DyeColor.ORANGE;
                    case 0b010 -> DyeColor.PURPLE;
                    case 0b011 -> DyeColor.RED;
                    case 0b100 -> DyeColor.GREEN;
                    case 0b101 -> DyeColor.YELLOW;
                    case 0b110 -> DyeColor.BLUE;
                    case 0b111 -> DyeColor.LIGHT_GRAY;
                    default -> DyeColor.LIGHT_GRAY;
                };
            if (d == 2)
                return switch (flag) {
                    case 0b000 -> DyeColor.BLACK;
                    case 0b001 -> DyeColor.ORANGE;
                    case 0b010 -> DyeColor.PURPLE;
                    case 0b011 -> DyeColor.RED;
                    case 0b100 -> DyeColor.GREEN;
                    case 0b101 -> DyeColor.YELLOW;
                    case 0b110 -> DyeColor.BLUE;
                    case 0b111 -> DyeColor.GRAY;
                    default -> DyeColor.GRAY;
                };

            return switch (flag) {
                case 0b000 -> DyeColor.BLACK;
                case 0b001 -> DyeColor.ORANGE;
                case 0b010 -> DyeColor.PURPLE;
                case 0b011 -> DyeColor.MAGENTA;
                case 0b100 -> DyeColor.GREEN;
                case 0b101 -> DyeColor.BROWN;
                case 0b110 -> DyeColor.CYAN;
                case 0b111 -> DyeColor.BLACK;
                default -> DyeColor.BLACK;
            };
        }),

    SHEEP_SPEED(new Loci[]{Loci.SHEEP_E, Loci.SHEEP_F},
        locus -> {
            int e = locus.get(Loci.SHEEP_E);
            int f = locus.get(Loci.SHEEP_F);

            float basic = 0.23f;
            float pe = switch (e) {
                case 2 -> 1.5f;
                case 1 -> 1.2f;
                default -> 1.0f;
            };

            float pf = switch (f) {
                case 2 -> 0.5f;
                case 1 -> 0.8f;
                default -> 1.0f;
            };

            return basic * pe * pf;
        }),

    SHEEP_WOOL_SHEARING(new Loci[]{Loci.SHEEP_J, Loci.SHEEP_K},
        locus -> {
            int j = locus.get(Loci.SHEEP_J);
            int k = locus.get(Loci.SHEEP_K);

            int base = switch (j) {
                case 2 -> 3;
                case 1 -> 2;
                default -> 1;
            };

            int offset = switch (k) {
                case 2 -> 3;
                case 1 -> 2;
                default -> 1;
            };

            // pair key:   base
            // pair value: the parameter of random.nextInt()
            return new Pair<>(base, offset);
        }),

    SHEEP_MAX_HEALTH(new Loci[]{Loci.SHEEP_G, Loci.SHEEP_H, Loci.SHEEP_I},
        locus -> {
            int g = locus.get(Loci.SHEEP_G);
            int h = locus.get(Loci.SHEEP_H);
            int i = locus.get(Loci.SHEEP_I);

            float base = 8.0f;

            if (g == 0) base += 1.0f;
            if (h == 0) base += 2.0f;
            if (i == 0) base += 3.0f;

            return base;
        }),

    TOMATO_PRODUCTION(new Loci[]{Loci.TOMATO_A, Loci.TOMATO_B},
        locus -> {
            int a = locus.get(Loci.TOMATO_A);
            int b = locus.get(Loci.TOMATO_B);

            int base = 2;

            base += switch (a) {
                case 2 -> 2;
                case 1 -> 1;
                default -> 0;
            };

            base -= switch (b) {
                case 2 -> 2;
                case 1 -> 1;
                default -> 0;
            };

            return base;
        }),

    TOMATO_SEED_NUM(new Loci[]{Loci.TOMATO_C},
        locus -> {
            int c = locus.get(Loci.TOMATO_C);
            return 2 - c;
        }),

    TOMATO_TYPE(new Loci[]{Loci.TOMATO_D},
        locus -> {
            int d = locus.get(Loci.TOMATO_D);
            return switch (d) {
                case 0 -> ItemRegistry.YELLOW_TOMATO;
                case 1 -> ItemRegistry.ORANGE_TOMATO;
                case 2 -> ItemRegistry.PINK_TOMATO;
                default -> ItemRegistry.RED_TOMATO;
            };
        }),

    GARLIC_PRODUCTION(new Loci[]{Loci.GARLIC_A, Loci.GARLIC_B, Loci.GARLIC_C},
    locus -> {
        int a = locus.get(Loci.GARLIC_A);
        int b = locus.get(Loci.GARLIC_B);
        int c = locus.get(Loci.GARLIC_C);

        int base = 2;

        base += switch (a) {
            case 2 -> 2;
            case 1 -> 1;
            default -> 0;
        };

        base -= switch (b) {
            case 2 -> 2;
            case 1 -> 1;
            default -> 0;
        };

        base += switch (c) {
            case 2 -> 2;
            case 1 -> 1;
            default -> 0;
        };

        return base;
    }),

    GARLIC_TYPE(new Loci[]{Loci.GARLIC_D},
        locus -> {
            int d = locus.get(Loci.GARLIC_D);
            return switch (d) {
                case 0 -> ItemRegistry.PURPLE_GARLIC;
                default -> ItemRegistry.WHITE_GARLIC;
            };
        }),

    SCALLION_PRODUCTION(new Loci[]{Loci.SCALLION_A},
        locus -> {
            int a = locus.get(Loci.SCALLION_A);

            int base = 3;

            return a == 1 ? base + 2 : base;
        }),

    SCALLION_SEED_NUM(new Loci[]{Loci.SCALLION_B},
        locus -> {
            int a = locus.get(Loci.SCALLION_B);

            int base = 3;

            return a == 1 ? base + 2 : base;
        });

    private final List<Loci> POLYGENES;
    private final Function<Map<Loci, Integer>, Object> MAPPING;

    Trait(Loci[] polygenes, Function<Map<Loci, Integer>, Object> mapping) {
        POLYGENES = List.of(polygenes);
        MAPPING = mapping;
    }

    public Object getValue(Map<Loci, Integer> genes) {
        return MAPPING.apply(genes);
    }

    public List<Loci> getPolygenes() {
        return POLYGENES;
    }

    @Override
    public String toString() {
        return name();
    }
}
