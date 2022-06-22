package com.asakibi.genetix.block;

import com.asakibi.genetix.block.entity.AsexualGenetixCropEntity;
import com.asakibi.genetix.block.entity.SexualGenetixCropEntity;
import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.Trait;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public abstract class AsexualGenetixCrop extends GenetixCrop{
    AsexualGenetixCrop(Settings settings) {
        super(settings);
    }

    public abstract Item getFruitById(Integer index);

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AsexualGenetixCropEntity) {

                BlockState blockState = world.getBlockState(pos);
                int age = blockState.get(CropBlock.AGE);

                AsexualGenetixCropEntity asexualGenetixCropEntity = (AsexualGenetixCropEntity) blockEntity;

                if (age == 7) {
                    Map<NbtCompound, Integer> fruits = asexualGenetixCropEntity.getChildrenAtLast(world.random);

                    fruits.forEach((fruit, num) -> {
                        if (num > 0) {
                            Diploid childDiploid = new Diploid(fruit);
                            Integer typeId = (Integer) childDiploid.computeTrait(Trait.GARLIC_TYPE);
                            ItemStack itemStack
                                = new ItemStack(getFruitById(typeId), num);
                            itemStack.setSubNbt("parent_diploid", asexualGenetixCropEntity.getDiploid().toNBT());
                            itemStack.setSubNbt("diploid", fruit);
                            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                            itemEntity.setToDefaultPickupDelay();
                            world.spawnEntity(itemEntity);
                        }
                    });
                } else {
                    Diploid thisDiploid = asexualGenetixCropEntity.getDiploid();

                    ItemStack itemStack = new ItemStack(getSeedsItem(), 1);
                    itemStack.setSubNbt("diploid", thisDiploid.toNBT());
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                }
            }
        }

        super.onBreak(world, pos, state, player);
    }
}
