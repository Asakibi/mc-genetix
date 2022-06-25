package com.asakibi.genetix.item;

import com.asakibi.genetix.block.entity.GenetixCropEntity;
import com.asakibi.genetix.genetics.Diploid;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantGenotypingCottonSwabItem extends GenotypingCottonSwabItem {
    public PlantGenotypingCottonSwabItem(Settings settings) {
        super(settings);
    }

    @Override
    protected String getItemName() {
        return "plant_genotyping_cotton_swab";
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenetixCropEntity) {

                ItemStack thisSwab = context.getStack();

                if (isNew(thisSwab)) {
                    thisSwab.decrement(1);

                    GenetixCropEntity cropEntity = (GenetixCropEntity) blockEntity;
                    Diploid diploid = cropEntity.getDiploid();

                    ItemStack newItemStack = new ItemStack(this);
                    addNbt(newItemStack, diploid);
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, newItemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        }
        return super.useOnBlock(context);
    }
}
