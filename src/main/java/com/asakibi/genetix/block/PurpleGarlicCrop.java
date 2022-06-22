package com.asakibi.genetix.block;

import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.item.ItemConvertible;

public class PurpleGarlicCrop extends GarlicCrop {

    public PurpleGarlicCrop(Settings settings) {
        super(settings);
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return ItemRegistry.PURPLE_GARLIC;
    }
}
