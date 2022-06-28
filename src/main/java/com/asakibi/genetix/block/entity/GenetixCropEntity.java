package com.asakibi.genetix.block.entity;

import com.asakibi.genetix.genetics.Diploid;
import com.asakibi.genetix.genetics.DiploidStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.List;

public abstract class GenetixCropEntity extends BlockEntity {

    protected Diploid diploid;
    protected final DiploidStructure diploidStructure;
    protected Item sowedItem;

    public GenetixCropEntity(BlockEntityType<?> type, BlockPos pos, BlockState state,
                             DiploidStructure diploidStructure) {
        super(type, pos, state);
        this.diploidStructure = diploidStructure;
    }

    public final void generateDiploid(Random random, boolean isNatural) {
        this.diploid = new Diploid(this.diploidStructure, random, isNatural);
    }

    public final void setDiploid(NbtCompound nbtCompound) {
        this.diploid = new Diploid(nbtCompound);
        markDirty();
    }

    public final void setSowedItem(Item item) {
        this.sowedItem = item;
        markDirty();
    }

    public final Diploid getDiploid() {
        return this.diploid;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtCompound nbtDiploid = nbt.getCompound("diploid");
        this.diploid = nbtDiploid == null ? null : new Diploid(nbtDiploid);
        int sowedItemRawId = nbt.getInt("sowed_item");
        this.sowedItem = Item.byRawId(sowedItemRawId);
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("diploid", this.diploid.toNBT());
        nbt.putInt("sowed_item", Item.getRawId(this.sowedItem));
    }

    public ItemStack returnSowable() {
        ItemStack itemStack = new ItemStack(sowedItem);
        itemStack.setSubNbt("diploid", this.diploid.toNBT());
        return itemStack;
    }

    public abstract List<ItemStack> getSowableDroppings(Random random);

    public abstract List<ItemStack> getUnsowableDroppings(Random random);
}
