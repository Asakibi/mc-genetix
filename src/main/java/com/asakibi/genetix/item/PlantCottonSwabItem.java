package com.asakibi.genetix.item;

import com.asakibi.genetix.block.entity.SexualGenetixCropEntity;
import com.asakibi.genetix.genetics.Diploid;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantCottonSwabItem extends AbstractCottonSwabItem {
    public PlantCottonSwabItem(Settings settings) {
        super(settings);
    }

    @Override
    protected String getItemName() {
        return "plant_cotton_swab";
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SexualGenetixCropEntity) {
                SexualGenetixCropEntity sexualGenetixCropEntity = (SexualGenetixCropEntity) blockEntity;
                Diploid diploid = sexualGenetixCropEntity.getDiploid();
                ItemStack itemStack = context.getStack();
                itemStack.decrement(1);

                ItemStack newItemStack = new ItemStack(this);
                addNbt(newItemStack,
                    diploid.getLowerCaseStructureName(),
                    diploid.toStringMap()
                );
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, newItemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        return super.useOnBlock(context);
    }
}
