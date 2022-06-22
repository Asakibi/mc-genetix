package com.asakibi.genetix.block;

import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.item.ItemConvertible;

public class WhiteGarlicCrop extends GarlicCrop {

    public WhiteGarlicCrop(Settings settings) {
        super(settings);
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return ItemRegistry.WHITE_GARLIC;
    }
}
