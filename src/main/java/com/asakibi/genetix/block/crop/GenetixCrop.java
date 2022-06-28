package com.asakibi.genetix.block.crop;

import com.asakibi.genetix.block.entity.GenetixCropEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public abstract class GenetixCrop extends CropBlock implements BlockEntityProvider {

    private final ItemConvertible SEED;

    GenetixCrop(Settings settings, Item seed) {
        super(settings);
        SEED = seed;
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return SEED;
    }

    @Override
    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public final void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenetixCropEntity genetixCropEntity) {

                NbtCompound nbtDiploid = itemStack.getSubNbt("diploid");
                if (nbtDiploid != null) {
                    genetixCropEntity.setDiploid(itemStack.getSubNbt("diploid"));
                } else {
                    genetixCropEntity.generateDiploid(world.random, true);
                }

                Item sowedItem = itemStack.getItem();
                genetixCropEntity.setSowedItem(sowedItem);
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof GenetixCropEntity genetixCropEntity) {
                List<ItemStack> items = getDroppings(world.getBlockState(pos), genetixCropEntity, world.random);

                items.forEach(itemStack -> {
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                });
            }
        }

        super.onBreak(world, pos, state, player);
    }

    protected List<ItemStack> getDroppings(BlockState blockState, GenetixCropEntity genetixCropEntity, Random random) {
        int age = blockState.get(CropBlock.AGE);

        List<ItemStack> items = List.of();

        if (age == 7) {
            items = new LinkedList<>();
            items.addAll(genetixCropEntity.getSowableDroppings(random));
            items.addAll(genetixCropEntity.getUnsowableDroppings(random));
        } else if (age >= 0 && age <= 3) {
            items = List.of(genetixCropEntity.returnSowable());
        }
        return items;
    }
}
