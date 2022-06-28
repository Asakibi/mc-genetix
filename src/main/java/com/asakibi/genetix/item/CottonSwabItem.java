package com.asakibi.genetix.item;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.entity.FruitAndSeedsCropEntity;
import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.Gamete;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CottonSwabItem extends Item {
    public CottonSwabItem(Settings settings) {
        super(settings);
    }

    protected String getItemName() {
        return "cotton_swab";
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FruitAndSeedsCropEntity) {
                BlockState blockState = context.getWorld().getBlockState(pos);
                int age = blockState.get(CropBlock.AGE);

                if (age >= 4 && age <= 6) {
                    ItemStack thisSwab = context.getStack();
                    FruitAndSeedsCropEntity cropEntity = (FruitAndSeedsCropEntity) blockEntity;
                    Random random = context.getWorld().random;

                    if (isNew(thisSwab)) {
                        int capacity = PlantConfig.cotton_swab_capacity;
                        NbtCompound info = new NbtCompound();
                        info.putInt("capacity", capacity);
                        info.putInt("num", capacity);
                        info.putString("diploid_structure", cropEntity.getDiploid().getLowerCaseStructureName());

                        NbtList gametes = new NbtList();

                        Diploid diploid = cropEntity.getDiploid();
                        for (int i = 0; i < PlantConfig.cotton_swab_capacity; i++) {
                            gametes.add(new Gamete(diploid, random).toNBT());
                        }

                        thisSwab.setSubNbt("gametes", gametes);
                        thisSwab.setSubNbt("info", info);
                    } else {
                        NbtCompound info = thisSwab.getSubNbt("info");
                        int num = info.getInt("num");
                        boolean diploidStructures = cropEntity.getDiploid().getLowerCaseStructureName()
                            .equals(info.getString("diploid_structure"));

                        NbtList gametes = thisSwab.getNbt().getList("gametes", NbtCompound.COMPOUND_TYPE);

                        if (num > 0 && diploidStructures) {
                            int flag = cropEntity.countVacancies();
                            while (flag > 0 && num > 0) {
                                NbtCompound gamete = gametes.getCompound(num - 1);

                                cropEntity.addGametes(new Gamete(gamete), 1);
                                gametes.remove(num - 1);
                                flag = cropEntity.countVacancies();
                                num--;
                            }

                            thisSwab.setSubNbt("gametes", gametes);
                            info.putInt("num", num);
                        }
                    }
                }
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound info = stack.getSubNbt("info");
        if (info == null) {
            tooltip.add(Text.translatable(getPrefix() + ".tooltip.null"));
            return;
        }

        int capacity = info.getInt("capacity");
        int num = info.getInt("num");
        String diploidStructure = info.getString("diploid_structure");
        tooltip.add(Text.translatable(getPrefix() + ".tooltip.structure." + diploidStructure));
        tooltip.add(Text.literal("§7" + num + "/" + capacity + "§r"));
    }

    public boolean isNew(ItemStack itemStack) {
        NbtCompound itemStackNbt = itemStack.getNbt();
        if (itemStackNbt == null) return true;
        NbtList gametes = itemStackNbt.getList("gametes", NbtElement.COMPOUND_TYPE);
        return gametes == null;
    }

    private String getPrefix() {
        return "item." + Mod.NAME + "."  + getItemName();
    }
}
