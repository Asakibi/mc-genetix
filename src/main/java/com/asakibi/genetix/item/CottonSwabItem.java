package com.asakibi.genetix.item;

import com.asakibi.genetix.Mod;
import com.asakibi.genetix.block.entity.FruitAndSeedsCropEntity;
import com.asakibi.genetix.block.entity.GenetixCropEntity;
import com.asakibi.genetix.config.PlantConfig;
import com.asakibi.genetix.genetics.Diploid;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
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

                        NbtList parents = new NbtList();

                        Diploid diploid = cropEntity.getDiploid();
                        for (int i = 0; i < PlantConfig.cotton_swab_capacity; i++) {
                            parents.add(diploid.toNBT());
                        }

                        thisSwab.setSubNbt("parents", parents);
                        thisSwab.setSubNbt("info", info);
                    } else {
                        NbtCompound info = thisSwab.getSubNbt("info");
                        int num = info.getInt("num");
                        boolean diploidStructures = cropEntity.getDiploid().getLowerCaseStructureName()
                            .equals(info.getString("diploid_structure"));

                        NbtList parents = thisSwab.getNbt().getList("parents", NbtCompound.COMPOUND_TYPE);

                        if (num > 0 && diploidStructures) {
                            int flag = cropEntity.countVacancies();
                            while (flag > 0 && num > 0) {
                                NbtCompound parent = parents.getCompound(num - 1);

                                cropEntity.addParents(new Diploid(parent), 1);
                                parents.remove(num - 1);
                                flag = cropEntity.countVacancies();
                                num--;
                            }

                            thisSwab.setSubNbt("parents", parents);
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
        NbtList parents = itemStackNbt.getList("parents", NbtElement.COMPOUND_TYPE);
        return parents == null;
    }

    private String getPrefix() {
        return "item." + Mod.NAME + "."  + getItemName();
    }
}
