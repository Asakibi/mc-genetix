package com.asakibi.genetix.item;

import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.item.Item;

import java.util.Map;

public class SeedBundleItem extends GenetixBundleItem {

    public final Map<Item, Integer> ITEM_CATEGORY_MAP = Map.of(
        ItemRegistry.TOMATO_SEEDS, 0,
        ItemRegistry.WHITE_GARLIC, 1,
        ItemRegistry.PURPLE_GARLIC, 1,
        ItemRegistry.SCALLION_SEEDS, 2
    );

    public SeedBundleItem(Settings settings) {
        super(settings, 128);
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
