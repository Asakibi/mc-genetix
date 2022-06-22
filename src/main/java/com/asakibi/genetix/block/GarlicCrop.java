package com.asakibi.genetix.block;

import com.asakibi.genetix.block.entity.GarlicCropEntity;
import com.asakibi.genetix.block.registry.BlockEntityRegistry;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class GarlicCrop extends AsexualGenetixCrop {

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D)
    };

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public GarlicCrop(Settings settings) {
        super(settings);
    }

    @Override
    public final Item getFruitById(Integer index) {
        return index == 0 ? ItemRegistry.PURPLE_GARLIC : ItemRegistry.WHITE_GARLIC;
    }

    @Override
    public abstract ItemConvertible getSeedsItem();

    @Override
    public final BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GarlicCropEntity(BlockEntityRegistry.GARLIC_CROP_ENTITY, pos, state);
    }
}
