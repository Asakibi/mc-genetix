package com.asakibi.genetix.block;

import com.asakibi.genetix.block.entity.SexualGenetixCropEntity;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class SexualGenetixCrop extends GenetixCrop {

    public SexualGenetixCrop(Settings settings) {
        super(settings);
    }

    public abstract Item getFruitById(Integer index);

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SexualGenetixCropEntity) {

                BlockState blockState = world.getBlockState(pos);
                int age = blockState.get(CropBlock.AGE);

                SexualGenetixCropEntity sexualGenetixCropEntity = (SexualGenetixCropEntity) blockEntity;

                if (age == 7) {
                    int fruitType = sexualGenetixCropEntity.getFruitType();
                    int production = sexualGenetixCropEntity.getProduction();

                    if (production > 0) {
                        ItemStack itemStack = new ItemStack(this.getFruitById(fruitType), production);
                        itemStack.setSubNbt("diploid", sexualGenetixCropEntity.getDiploid().toNBT());
                        ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                        itemEntity.setToDefaultPickupDelay();
                        world.spawnEntity(itemEntity);
                    }

                    sexualGenetixCropEntity.getChildrenAtLast(world.random).forEach((nbtChildDiploid, num) -> {
                        if (num > 0) {
                            ItemStack itemStack = new ItemStack(getSeedsItem(), num);
                            itemStack.setSubNbt("diploid", nbtChildDiploid);
                            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                            itemEntity.setToDefaultPickupDelay();
                            world.spawnEntity(itemEntity);
                        }
                    });
                } else {
                    ItemStack itemStack = new ItemStack(getSeedsItem(), 1);
                    itemStack.setSubNbt("diploid", sexualGenetixCropEntity.getDiploid().toNBT());
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        }
        super.onBreak(world, pos, state, player);
    }
}
