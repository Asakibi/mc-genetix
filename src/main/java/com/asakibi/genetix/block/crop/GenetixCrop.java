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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenetixCropEntity genetixCropEntity) {

                BlockState blockState = world.getBlockState(pos);
                int age = blockState.get(CropBlock.AGE);

                List<ItemStack> items;

                items = switch (age) {
                    case 4, 5, 6 -> genetixCropEntity.getSeedsAndProductsUnripe(world.random);
                    case 7 -> genetixCropEntity.getSeedsAndProductsRipe(world.random);
                    default -> genetixCropEntity.getSeedsAndProductsYoung(world.random);
                };

                items.forEach(itemStack -> {
                    ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                    itemEntity.setToDefaultPickupDelay();
                    world.spawnEntity(itemEntity);
                });
            }
        }

        super.onBreak(world, pos, state, player);
    }
}
