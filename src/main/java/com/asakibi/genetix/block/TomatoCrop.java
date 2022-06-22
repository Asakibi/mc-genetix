package com.asakibi.genetix.block;

import com.asakibi.genetix.block.entity.TomatoCropEntity;
import com.asakibi.genetix.item.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TomatoCrop extends SexualGenetixCrop {

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D),
        Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
        Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)
    };

    private static final Function<Integer, Item> typeMap = index -> switch (index) {
        default -> ItemRegistry.YELLOW_TOMATO;
        case 1 -> ItemRegistry.ORANGE_TOMATO;
        case 2 -> ItemRegistry.PINK_TOMATO;
        case 3 -> ItemRegistry.RED_TOMATO;
    };

    public TomatoCrop(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return ItemRegistry.TOMATO_SEEDS;
    }

    @Override
    public Item getFruitById(Integer index) {
        return typeMap.apply(index);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TomatoCropEntity(pos, state);
    }

}
