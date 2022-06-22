package com.asakibi.genetix.block;

import com.asakibi.genetix.block.entity.GenetixCropEntity;
import com.asakibi.genetix.block.entity.SexualGenetixCropEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class GenetixCrop extends CropBlock implements BlockEntityProvider {
    GenetixCrop(Settings settings) {
        super(settings);
    }

    @Override
    public abstract ItemConvertible getSeedsItem();

    @Override
    public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public final void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (itemStack.isOf(getSeedsItem().asItem()) && blockEntity instanceof GenetixCropEntity) {
                GenetixCropEntity genetixCropEntity = (GenetixCropEntity) blockEntity;

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
        super.onBreak(world, pos, state, player);
    }
}
