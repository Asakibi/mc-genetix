package com.asakibi.genetix.item.registry;

import com.asakibi.genetix.Mod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GroupRegistry {

    public static final ItemGroup DEFAULT_GROUP = FabricItemGroupBuilder.build(
        new Identifier(Mod.NAME, "default"),
        () -> new ItemStack(Items.BELL)
    );

    public static final ItemGroup TOOLS_GROUP = FabricItemGroupBuilder.build(
        new Identifier(Mod.NAME, "tools"),
        () -> new ItemStack(Items.SHEARS)
    );

    public static final ItemGroup FOOD_GROUP = FabricItemGroupBuilder.build(
        new Identifier(Mod.NAME, "food"),
        () -> new ItemStack(ItemRegistry.PORK_BAOZI)
    );

}
