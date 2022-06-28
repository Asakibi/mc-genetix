package com.asakibi.genetix.item;

import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.item.Item;

import java.util.Map;

public class ProductBundleItem extends GenetixBundleItem {

    public static final Map<Item, Integer> ITEM_CATEGORY_MAP = Map.of(
        ItemRegistry.RED_TOMATO, 0,
        ItemRegistry.PINK_TOMATO, 0,
        ItemRegistry.ORANGE_TOMATO, 0,
        ItemRegistry.YELLOW_TOMATO, 0,
        ItemRegistry.WHITE_GARLIC, 1,
        ItemRegistry.PURPLE_GARLIC, 1,
        ItemRegistry.SCALLION, 2
    );

    public ProductBundleItem(Settings settings) {
        super(settings, 64);
    }

    @Override
    public boolean supported(Item item) {
        return ITEM_CATEGORY_MAP.containsKey(item);
    }

    @Override
    public Integer getItemFlag(Item item) {
        return ITEM_CATEGORY_MAP.get(item);
    }
}
