package com.asakibi.genetix.item;

import java.util.*;
import java.util.stream.Stream;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class GenetixBundleItem
    extends Item {
    private static final String ITEMS_KEY = "Items";
    public final int maxStorage;
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4f, 0.4f, 1.0f);

    public GenetixBundleItem(Item.Settings settings, int maxStorage) {
        super(settings);
        this.maxStorage = maxStorage;
    }

    public static float getAmountFilled(ItemStack stack) {
        Item item = stack.getItem();
        GenetixBundleItem genetixBundleItem = (GenetixBundleItem) item;
        return (float)genetixBundleItem.getBundleOccupancy(stack) / (float) getMaxStorage(stack);
    }

    public abstract boolean supported(Item item);
    public abstract Integer getItemFlag(Item item);

    public boolean inSameGroup(Item item1, Item item2) {
        Integer flag1 = getItemFlag(item1);
        Integer flag2 = getItemFlag(item2);
        if (flag1 == null || flag2 == null) return false;

        return flag1.equals(flag2);
    }

    private static GenetixBundleItem getItem(ItemStack stack) {
        return (GenetixBundleItem) stack.getItem();
    }

    public static int getMaxStorage(ItemStack stack) {
        return getItem(stack).maxStorage;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }
        ItemStack itemStack = slot.getStack();

        // when the slot is not empty
        if (!itemStack.isEmpty()) {
            if (!validate(stack, itemStack)) {
                return false;
            }
        }

        if (itemStack.isEmpty()) {
            playRemoveOneSound(player);
            removeFirstStack(stack).ifPresent(removedStack -> addToBundle(stack, slot.insertStack(removedStack)));
        } else if (itemStack.getItem().canBeNested()) {
            int i = (maxStorage - getBundleOccupancy(stack)) / getItemOccupancy(itemStack);
            int j = addToBundle(stack, slot.takeStackRange(itemStack.getCount(), i, player));
            if (j > 0) {
                playInsertSound(player);
            }
        }
        return true;
    }

    private boolean validate(ItemStack bundleStack, ItemStack itemStack) {
        // get the item in the slot
        Item stackItem = itemStack.getItem();

        // if the item isn't supported by this type of bundle,
        // do nothing
        if (!supported(stackItem)) {
            return false;
        }

        // get the item in the bundle
        Optional<Item> bundleFirstItem = getFirstItem(bundleStack);

        // if the items are not in the same category,
        // do nothing
        if (bundleFirstItem.isPresent()) {
            if (!inSameGroup(bundleFirstItem.get(), stackItem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT || !slot.canTakePartial(player)) {
            return false;
        }
        if (otherStack.isEmpty()) {
            removeFirstStack(stack).ifPresent(itemStack -> {
                playRemoveOneSound(player);
                cursorStackReference.set(itemStack);
            });
        } else {
            // when the other item stack is not empty
            if (!validate(stack, otherStack)) {
                return false;
            }

            int i = addToBundle(stack, otherStack);
            if (i > 0) {
                playInsertSound(player);
                otherStack.decrement(i);
            }
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (dropAllBundledItems(itemStack, user)) {
            playDropContentsSound(user);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getBundleOccupancy(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getBundleOccupancy(stack) / maxStorage, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    private int addToBundle(ItemStack bundle, ItemStack stack) {
        if (stack.isEmpty() || !stack.getItem().canBeNested()) {
            return 0;
        }
        NbtCompound nbtCompound = bundle.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            nbtCompound.put(ITEMS_KEY, new NbtList());
        }
        int i = getBundleOccupancy(bundle);
        int j = getItemOccupancy(stack);
        int k = Math.min(stack.getCount(), (maxStorage - i) / j);
        if (k == 0) {
            return 0;
        }
        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);

        Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
        if (optional.isPresent()) {
            NbtCompound nbtCompound2 = optional.get();
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
            itemStack.increment(k);
            itemStack.writeNbt(nbtCompound2);
            nbtList.remove(nbtCompound2);
            nbtList.add(0, nbtCompound2);
        } else {
            ItemStack itemStack2 = stack.copy();
            itemStack2.setCount(k);
            NbtCompound nbtCompound3 = new NbtCompound();
            itemStack2.writeNbt(nbtCompound3);
            nbtList.add(0, nbtCompound3);
        }
        return k;
    }

    private static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        if (stack.isOf(Items.BUNDLE)) {
            return Optional.empty();
        }
        return items.stream().filter(NbtCompound.class::isInstance).map(NbtCompound.class::cast).filter(item -> ItemStack.canCombine(ItemStack.fromNbt(item), stack)).findFirst();
    }

    private int getItemOccupancy(ItemStack stack) {
        return 64 / stack.getMaxCount();
    }

    private int getBundleOccupancy(ItemStack stack) {
        return getBundledStacks(stack).mapToInt(itemStack -> getItemOccupancy(itemStack) * itemStack.getCount()).sum();
    }

    private static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        }
        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
        if (nbtList.isEmpty()) {
            return Optional.empty();
        }
        NbtCompound nbtCompound2 = nbtList.getCompound(0);
        ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
        nbtList.remove(0);
        if (nbtList.isEmpty()) {
            stack.removeSubNbt(ITEMS_KEY);
        }
        return Optional.of(itemStack);
    }

    private static Optional<Item> getFirstItem(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        }
        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
        if (nbtList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ItemStack.fromNbt((NbtCompound) nbtList.get(0)).getItem());
    }

    private static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return false;
        }
        if (player instanceof ServerPlayerEntity) {
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                player.dropItem(itemStack, true);
            }
        }
        stack.removeSubNbt(ITEMS_KEY);
        return true;
    }

    private static Stream<ItemStack> getBundledStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        }
        NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
        return nbtList.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
//        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
//        getBundledStacks(stack).forEach(defaultedList::add);
//        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        Map<Item, Integer> map = new HashMap<>();
        Set<Item> set = new LinkedHashSet<>();
        getBundledStacks(stack).forEach(content -> {
            Item item = content.getItem();
            int count = content.getCount();
            if (set.contains(item)) {
                map.replace(item, map.get(item) + count);
            } else {
                map.put(item, count);
                set.add(item);
            }
        });
        set.forEach(item -> {
            defaultedList.add(new ItemStack(item, map.get(item)));
        });
        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", getBundleOccupancy(stack), maxStorage).formatted(Formatting.GRAY));
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemUsage.spawnItemContents(entity, getBundledStacks(entity.getStack()));
    }

    private static void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }

    private static void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }
}

