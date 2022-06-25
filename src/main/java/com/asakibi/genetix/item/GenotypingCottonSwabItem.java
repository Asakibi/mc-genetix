package com.asakibi.genetix.item;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.genetics.Diploid;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class GenotypingCottonSwabItem extends Item {

    public GenotypingCottonSwabItem(Settings settings) {
        super(settings);
    }

    protected abstract String getItemName();

    public static void addNbt(ItemStack itemStack, Diploid diploid) {
        if (!isNew(itemStack)) return;

        NbtCompound diploidInfo = new NbtCompound();
        diploidInfo.putString("structure", diploid.getLowerCaseStructureName());
        NbtCompound strings = new NbtCompound();
        diploid.toStringMap().forEach((cs, string) -> strings.putString(cs.getShortName(), string));
        diploidInfo.put("homologous_chromosomes", strings);
        itemStack.setSubNbt("diploid_info", diploidInfo);
    }

    public static boolean isNew(ItemStack itemStack) {
        NbtCompound diploidExisted = itemStack.getSubNbt("diploid_info");
        return diploidExisted == null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound diploidInfoNbt = stack.getSubNbt("diploid_info");
        if (diploidInfoNbt == null) {
            tooltip.add(Text.translatable(getPrefix() + ".tooltip.null"));
            return;
        }

        String structure = diploidInfoNbt.getString("structure");
        if ("".equals(structure)) {
            tooltip.add(Text.translatable(getPrefix() + ".tooltip.no_structure"));
            return;
        }
        tooltip.add(Text.translatable(getPrefix() + ".tooltip.structure." + structure));

        NbtCompound strings = diploidInfoNbt.getCompound("homologous_chromosomes");
        if (strings == null) {
            tooltip.add(Text.translatable(getPrefix() + ".tooltip.no_chromosomes"));
            return;
        }
        strings.getKeys().stream().sorted().forEach(cs -> tooltip.add(Text.literal(cs + ":" + strings.getString(cs))));
    }

    private String getPrefix() {
        return "item." + Mod.NAME + "."  + getItemName();
    }
}
