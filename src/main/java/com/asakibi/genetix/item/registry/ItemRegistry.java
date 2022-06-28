package com.asakibi.genetix.item.registry;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.registry.BlockRegistry;
import com.asakibi.genetix.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.CropBlock;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemRegistry {
    public static final Item TEST = new Item(new FabricItemSettings().group(GroupRegistry.DEFAULT)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.genetix.test.tooltip"));
        }
    };


    public static final Item SEED_BUNDLE = new SeedBundleItem(new Item.Settings().maxCount(1).group(GroupRegistry.TOOLS));
    public static final Item PRODUCT_BUNDLE = new ProductBundleItem(new Item.Settings().maxCount(1).group(GroupRegistry.TOOLS));
    public static final Item ANIMAL_GENOTYPING_COTTON_SWAB = new AnimalGenotypingCottonSwabItem(new FabricItemSettings().group(GroupRegistry.TOOLS));
    public static final Item PLANT_GENOTYPING_COTTON_SWAB = new PlantGenotypingCottonSwabItem(new FabricItemSettings().group(GroupRegistry.TOOLS));
    public static final Item COTTON_SWAB = new CottonSwabItem(new FabricItemSettings().group(GroupRegistry.TOOLS).maxCount(1));

    public static final Item FLOUR
        = food(1, 0.2f, "flour");
    public static final Item DOUGH
        = food(1, 0.4f, "dough");
    public static final Item POTATO_STARCH
        = food(1, 1f, "potato_starch");

    public static final Item GROUND_PORK
        = food(1, 0.8f, "ground_pork");
    public static final Item GROUND_BEEF
        = food(1, 0.8f, "ground_beef");
    public static final Item GROUND_CHICKEN
        = food(1, 0.6f, "ground_chicken");
    public static final Item GROUND_MUTTON
        = food(1, 0.6f, "ground_mutton");
    public static final Item GROUND_FISH
        = food(1, 0.2f, "ground_fish");

    public static final Item UNSTEAMED_MANTOU
        = food(1, 0.4f, "unsteamed_mantou");
    public static final Item UNSTEAMED_PORK_BAOZI
        = food(3, 1.2f, "unsteamed_pork_baozi");
    public static final Item UNSTEAMED_BEEF_BAOZI
        = food(3, 1.2f, "unsteamed_beef_baozi");
    public static final Item UNSTEAMED_CHICKEN_BAOZI
        = food(3, 1f, "unsteamed_chicken_baozi");
    public static final Item UNSTEAMED_MUTTON_BAOZI
        = food(3, 1f, "unsteamed_mutton_baozi");
    public static final Item UNSTEAMED_FISH_BAOZI
        = food(3, 0.4f, "unsteamed_fish_baozi");

    public static final Item RAW_PORK_MEATBALL
        = food(1, 0.6f, "raw_pork_meatball");
    public static final Item RAW_BEEF_MEATBALL
        = food(1, 0.6f, "raw_beef_meatball");
    public static final Item RAW_CHICKEN_MEATBALL
        = food(1, 0.4f, "raw_chicken_meatball");
    public static final Item RAW_MUTTON_MEATBALL
        = food(1, 0.5f, "raw_mutton_meatball");
    public static final Item RAW_FISH_MEATBALL
        = food(1, 0.1f, "raw_fish_ball");

    public static final Item MANTOU =
    food(3, 3.6f, "mantou");
    public static final Item PORK_BAOZI
        = food(7, 12.8f, "pork_baozi");
    public static final Item BEEF_BAOZI
        = food(7, 12.8f, "beef_baozi");
    public static final Item CHICKEN_BAOZI
        = food(6, 9.6f, "chicken_baozi");
    public static final Item MUTTON_BAOZI
        = food(6, 11.2f, "mutton_baozi");
    public static final Item FISH_BAOZI
        = food(6, 9.6f, "fish_baozi");

    public static final Item PORK_MEATBALL
        = food(4, 6.4f, "pork_meatball");
    public static final Item BEEF_MEATBALL
        = food(4, 6.4f, "beef_meatball");
    public static final Item CHICKEN_MEATBALL
        = food(3, 4.8f, "chicken_meatball");
    public static final Item MUTTON_MEATBALL
        = food(3, 5.6f, "mutton_meatball");
    public static final Item FISH_MEATBALL
        = food(3, 4.2f, "fish_ball");

    public static final Item TOMATO_SEEDS
        = seed(BlockRegistry.TOMATO_CROP, "tomato_seeds");
    public static final Item RED_TOMATO
        = food(1, 0.6f, "red_tomato");
    public static final Item PINK_TOMATO
        = food(1, 0.6f, "pink_tomato");
    public static final Item ORANGE_TOMATO
        = food(1, 0.6f, "orange_tomato");
    public static final Item YELLOW_TOMATO
        = food(1, 0.6f, "yellow_tomato");

    public static final Item WHITE_GARLIC
        = seedAsFood(1, 0.6f, BlockRegistry.GARLIC_CROP_WHITE, "white_garlic");
    public static final Item PURPLE_GARLIC
        = seedAsFood(1, 0.6f, BlockRegistry.GARLIC_CROP_PURPLE, "purple_garlic");

    public static final Item SCALLION
        = seedAsFood(1, 0.6f, BlockRegistry.SCALLION_CROP, "scallion");
    public static final Item SCALLION_SEEDS
        = seed(BlockRegistry.SCALLION_CROP_SEED, "scallion_seeds");


    private static void register(Item item, String path) {
        Registry.register(Registry.ITEM, new Identifier(Mod.NAME, path), item);
    }

    private static Item food(int hunger, float saturation, String path) {
        Item item = new Item(
            new FabricItemSettings()
                .food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build())
                .group(GroupRegistry.FOOD)) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                tooltip.add(Text.translatable("item." + Mod.NAME + "." + path + ".tooltip"));
            }
        };
        register(item, path);
        return item;
    }

    private static Item seed(CropBlock cropBlock, String path) {
        Item item = new AliasedBlockItem(cropBlock,
            new FabricItemSettings()
                .group(GroupRegistry.FOOD)) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                tooltip.add(Text.translatable("item." + Mod.NAME + "."+ path + ".tooltip"));
            }
        };
        register(item, path);
        return item;
    }

    private static Item seedAsFood(int hunger, float saturation,
                                   CropBlock cropBlock, String path) {
        Item item = new AliasedBlockItem(cropBlock,
            new FabricItemSettings()
                .food(new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build())
                .group(GroupRegistry.FOOD)) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                tooltip.add(Text.translatable("item." + Mod.NAME + "."+ path + ".tooltip"));
            }
        };
        register(item, path);
        return item;
    }

    public static void registerAll() {
        register(TEST, "test");
        register(ANIMAL_GENOTYPING_COTTON_SWAB, "animal_genotyping_cotton_swab");
        register(PLANT_GENOTYPING_COTTON_SWAB, "plant_genotyping_cotton_swab");
        register(COTTON_SWAB, "cotton_swab");
        register(SEED_BUNDLE, "seed_bundle");
        register(PRODUCT_BUNDLE, "product_bundle");

        // model predicate provider
        ModelPredicateProviderRegistry.register(SEED_BUNDLE, new Identifier("filled"), (stack, world, entity, seed) -> GenetixBundleItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(PRODUCT_BUNDLE, new Identifier("filled"), (stack, world, entity, seed) -> GenetixBundleItem.getAmountFilled(stack));
    }
}
