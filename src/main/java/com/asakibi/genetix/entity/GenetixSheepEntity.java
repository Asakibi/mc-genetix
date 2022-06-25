package com.asakibi.genetix.entity;

import com.asakibi.genetix.genetics.Trait;
import com.asakibi.genetix.genetics.diploid.GenetixSheepDiploid;
import com.asakibi.genetix.item.AnimalGenotypingCottonSwabItem;
import com.asakibi.genetix.item.registry.ItemRegistry;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class GenetixSheepEntity extends AnimalEntity implements Shearable {
    private static final int MAX_GRASS_TIMER = 40;
    private static final TrackedData<Byte> COLOR;
    private static final Map<DyeColor, ItemConvertible> DROPS;
    private static final Map<DyeColor, float[]> COLORS;
    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    private static final TrackedData<NbtCompound> DIPLOID;
    private GenetixSheepDiploid diploid;

    private static final TrackedData<Integer> WOOL_BASIC;
    private static final TrackedData<Integer> WOOL_OFFSET;

    private static float[] getDyedColor(DyeColor color) {
        if (color == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] fs = color.getColorComponents();
            float f = 0.75F;
            return new float[]{fs[0] * f, fs[1] * f, fs[2] * f};
        }
    }

    public static float[] getRgbColor(DyeColor dyeColor) {
        return COLORS.get(dyeColor);
    }

    public GenetixSheepEntity(EntityType<? extends GenetixSheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(Items.WHEAT), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, this.eatGrassGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }

        super.tickMovement();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, (byte)0);
        this.dataTracker.startTracking(DIPLOID, null);
        this.dataTracker.startTracking(WOOL_BASIC, 1);
        this.dataTracker.startTracking(WOOL_OFFSET, 3);
    }

    @Override
    public Identifier getLootTableId() {
        if (this.isSheared()) {
            return this.getType().getLootTableId();
        } else {
            return switch (this.getColor()) {
                case WHITE -> LootTables.WHITE_SHEEP_ENTITY;
                case ORANGE -> LootTables.ORANGE_SHEEP_ENTITY;
                case MAGENTA -> LootTables.MAGENTA_SHEEP_ENTITY;
                case LIGHT_BLUE -> LootTables.LIGHT_BLUE_SHEEP_ENTITY;
                case YELLOW -> LootTables.YELLOW_SHEEP_ENTITY;
                case LIME -> LootTables.LIME_SHEEP_ENTITY;
                case PINK -> LootTables.PINK_SHEEP_ENTITY;
                case GRAY -> LootTables.GRAY_SHEEP_ENTITY;
                case LIGHT_GRAY -> LootTables.LIGHT_GRAY_SHEEP_ENTITY;
                case CYAN -> LootTables.CYAN_SHEEP_ENTITY;
                case PURPLE -> LootTables.PURPLE_SHEEP_ENTITY;
                case BLUE -> LootTables.BLUE_SHEEP_ENTITY;
                case BROWN -> LootTables.BROWN_SHEEP_ENTITY;
                case GREEN -> LootTables.GREEN_SHEEP_ENTITY;
                case RED -> LootTables.RED_SHEEP_ENTITY;
                case BLACK -> LootTables.BLACK_SHEEP_ENTITY;
            };
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            this.eatGrassTimer = MAX_GRASS_TIMER;
        } else {
            super.handleStatus(status);
        }

    }

    public float getNeckAngle(float delta) {
        if (this.eatGrassTimer <= 0) {
            return 0.0F;
        } else if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
            return 1.0F;
        } else {
            return this.eatGrassTimer < 4 ? ((float)this.eatGrassTimer - delta) / 4.0F : -((float)(this.eatGrassTimer - MAX_GRASS_TIMER) - delta) / 4.0F;
        }
    }

    public float getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0F;
            return 0.62831855F + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatGrassTimer > 0 ? 0.62831855F : this.getPitch() * 0.017453292F;
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, (playerx) -> {
                    playerx.sendToolBreakStatus(hand);
                });
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        }

        else if (itemStack.isOf(ItemRegistry.ANIMAL_GENOTYPING_COTTON_SWAB)) {
            if (!this.world.isClient) {
                NbtCompound nbt = itemStack.getNbt();
                if (nbt != null) {
                    return super.interactMob(player, hand);
                }

                itemStack.decrement(1);
                ItemStack newItemStack = new ItemStack(ItemRegistry.ANIMAL_GENOTYPING_COTTON_SWAB);
                AnimalGenotypingCottonSwabItem.addNbt(newItemStack, diploid);
                this.dropStack(newItemStack, 1);
                return ActionResult.SUCCESS;
            }
            return super.interactMob(player, hand);
        }

        else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);

        int i = this.getWoolBasic() + this.random.nextInt(this.getWoolOffset());

        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(DROPS.get(this.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }

    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
        nbt.put("diploid", this.getDiploid().toNBT());
        nbt.putByte("Color", (byte)this.getColor().getId());
        nbt.putInt("wool_basic", this.getWoolBasic());
        nbt.putInt("wool_offset", this.getWoolOffset());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
        this.setDiploid(nbt.contains("diploid") ?
            new GenetixSheepDiploid(nbt.getCompound("diploid"))
            : null);
        if (diploid != null) {
            this.setColor();
            this.setWoolArguments();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public DyeColor getColor() {
        return DyeColor.byId((Byte)this.dataTracker.get(COLOR) & 15);
    }

    public void setColor() {
        DyeColor color = (DyeColor) getDiploid().computeTrait(Trait.SHEEP_COLOR);
        byte b = (Byte)this.dataTracker.get(COLOR);
        this.dataTracker.set(COLOR, (byte)(b & 240 | color.getId() & 15));
    }

    public boolean isSheared() {
        return ((Byte)this.dataTracker.get(COLOR) & 16) != 0;
    }

    public void setSheared(boolean sheared) {
        byte b = (Byte) this.dataTracker.get(COLOR);
        if (sheared) {
            this.dataTracker.set(COLOR, (byte) (b | 16));
        } else {
            this.dataTracker.set(COLOR, (byte) (b & -17));
        }
    }

    public void setMovementSpeed() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance == null) {
            return;
        }

        entityAttributeInstance.setBaseValue((float) this.getDiploid().computeTrait(Trait.SHEEP_SPEED));
    }

    public void setMaxHealth() {
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) {
            return;
        }

        float maxHealth = (float) this.getDiploid().computeTrait(Trait.SHEEP_MAX_HEALTH);
        entityAttributeInstance.setBaseValue(maxHealth);
        setHealth(maxHealth);
    }

    public void setWoolArguments() {
        Pair<Integer, Integer> pair = (Pair<Integer, Integer>) getDiploid().computeTrait(Trait.SHEEP_WOOL_SHEARING);
        dataTracker.set(WOOL_BASIC, pair.getLeft());
        dataTracker.set(WOOL_OFFSET, pair.getRight());
    }

    public int getWoolBasic() {
        return dataTracker.get(WOOL_BASIC);
    }

    public int getWoolOffset() {
        return dataTracker.get(WOOL_OFFSET);
    }

    public GenetixSheepDiploid getDiploid() {
        return this.diploid;
    }

    public void setDiploid(GenetixSheepDiploid newDiploid) {
        if (newDiploid != null) {
            this.dataTracker.set(DIPLOID, newDiploid.toNBT());
        }
        this.diploid = newDiploid;
    }

    @Override
    public GenetixSheepEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        GenetixSheepEntity anotherParent = (GenetixSheepEntity) passiveEntity;
        GenetixSheepEntity childEntity = EntityRegistry.GENETIX_SHEEP.create(serverWorld);
        childEntity.setDiploid(new GenetixSheepDiploid(
            this.getDiploid(), anotherParent.getDiploid(),
            serverWorld.getRandom()
        ));
        childEntity.setColor();
        childEntity.setMovementSpeed();
        childEntity.setMaxHealth();
        childEntity.setWoolArguments();
        return childEntity;
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        this.setSheared(false);
        if (this.isBaby()) {
            this.growUp(60);
        }

    }

    @Override @Nullable
    public EntityData initialize(ServerWorldAccess world,
                                 LocalDifficulty difficulty,
                                 SpawnReason spawnReason,
                                 @Nullable EntityData entityData,
                                 @Nullable NbtCompound entityNbt) {
        boolean isNatural = spawnReason == SpawnReason.CHUNK_GENERATION
            || spawnReason == SpawnReason.SPAWNER
            || spawnReason == SpawnReason.NATURAL
            || spawnReason == SpawnReason.STRUCTURE;
        this.setDiploid(new GenetixSheepDiploid(random, isNatural));
        this.setColor();
        this.setMovementSpeed();
        this.setMaxHealth();
        this.setWoolArguments();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.95F * dimensions.height;
    }

    static {
        COLOR = DataTracker.registerData(GenetixSheepEntity.class, TrackedDataHandlerRegistry.BYTE);
        DROPS = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
            map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
            map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
            map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
            map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
            map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
            map.put(DyeColor.LIME, Blocks.LIME_WOOL);
            map.put(DyeColor.PINK, Blocks.PINK_WOOL);
            map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
            map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
            map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
            map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
            map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
            map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
            map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
            map.put(DyeColor.RED, Blocks.RED_WOOL);
            map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
        });
        COLORS = Maps.newEnumMap((Map) Arrays.stream(DyeColor.values()).collect(Collectors.toMap((dyeColor) -> {
            return dyeColor;
        }, GenetixSheepEntity::getDyedColor)));

        DIPLOID = DataTracker.registerData(GenetixSheepEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
        WOOL_BASIC = DataTracker.registerData(GenetixSheepEntity.class, TrackedDataHandlerRegistry.INTEGER);
        WOOL_OFFSET = DataTracker.registerData(GenetixSheepEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
