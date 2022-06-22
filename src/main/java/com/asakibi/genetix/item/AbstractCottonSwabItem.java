package com.asakibi.genetix.item;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.genetics.ChromosomeStructure;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractCottonSwabItem extends Item {

    public AbstractCottonSwabItem(Settings settings) {
        super(settings);
    }

    protected abstract String getItemName();

    public static void addNbt(ItemStack itemStack, String structure, LinkedHashMap<ChromosomeStructure, String> stringMap) {
        NbtCompound nbtExisted = itemStack.getSubNbt("diploid");

        if (nbtExisted != null) return;

        NbtCompound diploidNbt = new NbtCompound();
        diploidNbt.putString("structure", structure);
        NbtCompound strings = new NbtCompound();
        stringMap.forEach((cs, string) -> strings.putString(cs.getShortName(), string));
        diploidNbt.put("homologous_chromosomes", strings);
        itemStack.setSubNbt("diploid", diploidNbt);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound diploidNbt = stack.getSubNbt("diploid");
        if (diploidNbt == null) {
            tooltip.add(Text.translatable("item." + Mod.NAME + "." + getItemName() + ".tooltip.null"));
            return;
        }

        String structure = diploidNbt.getString("structure");
        if (structure == null) {
            tooltip.add(Text.translatable("item."+ Mod.NAME + "." + getItemName() + ".tooltip.no_structure"));
            return;
        }
        tooltip.add(Text.translatable("item."+ Mod.NAME + ".tooltip.structure." + structure));

        NbtCompound strings = diploidNbt.getCompound("homologous_chromosomes");
        if (strings == null) {
            tooltip.add(Text.translatable("item." + Mod.NAME + "."  + getItemName() + ".tooltip.no_chromosomes"));
            return;
        }
        strings.getKeys().stream().sorted().forEach(cs -> tooltip.add(Text.literal(cs + ":" + strings.getString(cs))));
    }
}
