package com.twispan.create_encapsulated.block.entity;

import com.cobblemon.mod.common.pokeball.PokeBall;
import com.cobblemon.mod.common.pokemon.Pokemon;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

import com.simibubi.create.foundation.utility.CreateLang;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.animation.LerpedFloat;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;

import com.twispan.create_encapsulated.util.slugma_burner.BlockPokemonState;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SlugmaBurnerBlockEntity extends BlazeBurnerBlockEntity implements IHaveGoggleInformation {

    public BlockPokemonState pokemonState;

    protected LerpedFloat bodyAnimation;
    protected LerpedFloat bodyAngle;

    private Pokemon pokemon;

    private UUID ownerUUID;
    private String ownerName;

    public SlugmaBurnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        pokemon = SlugmaBurnerHelper.getDefaultSlugma(getPlayerByUUID(ownerUUID));
        pokemonState = new BlockPokemonState();
        bodyAnimation = LerpedFloat.linear();
        bodyAngle = LerpedFloat.angular();
        bodyAngle.startWithValue((AngleHelper.horizontalAngle(state.getOptionalValue(BlazeBurnerBlock.FACING)
                .orElse(Direction.SOUTH)) + 180) % 360);
    }

    public SlugmaBurnerBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.SLUGMA_BURNER.get(), pos, state);
    }

    public static ServerPlayer getPlayerByUUID(UUID ownerUUID) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return (server == null || ownerUUID == null) ? null : server.getPlayerList().getPlayer(ownerUUID);
    }

    @Nullable
    private RegistryAccess registryAccess(HolderLookup.Provider registries) {
        if (registries instanceof RegistryAccess access) return access;
        return level != null ? level.registryAccess() : null;
    }

    @Override
    public void tick() {
        assert level != null;
        if (level.isClientSide()) {
            tickHeadAnim();
            tickBodyAnim();
        }
        super.tick();
    }

    public static final float MAX_HEAD_TURN = 60f;
    private static final float BODY_DEADZONE = 35f;
    private static final float BODY_CATCHUP = 0.25f;

    private boolean isSleeping() {
        boolean active = getHeatLevelFromBlock().isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && isValidBlockAbove();
        boolean awakeByHat = hat || stockKeeper;
        return !active && !awakeByHat && getHeatLevelFromBlock() == BlazeBurnerBlock.HeatLevel.SMOULDERING;
    }

    private void tickBodyAnim() {
        boolean active = getHeatLevelFromBlock().isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && isValidBlockAbove();

        if (isSleeping()) {
            float facing = (AngleHelper.horizontalAngle(getBlockState().getOptionalValue(BlazeBurnerBlock.FACING)
                    .orElse(Direction.SOUTH)) + 180) % 360;
            float body = bodyAngle.getValue();
            bodyAngle.chase(body + AngleHelper.getShortestAngleDiff(body, facing),
                    0.625, LerpedFloat.Chaser.EXP);
            bodyAngle.tickChaser();
        } else if (!active) {
            float head = headAngle.getValue();
            float body = bodyAngle.getValue();
            float diff = AngleHelper.getShortestAngleDiff(body, head);
            float excess = Math.abs(diff) - BODY_DEADZONE;

            float target = excess > 0 ? body + Math.signum(diff) * excess : body;
            bodyAngle.chase(target, BODY_CATCHUP, LerpedFloat.Chaser.EXP);
            bodyAngle.tickChaser();
        }

        bodyAnimation.chase(active ? 1 : 0, .125f, LerpedFloat.Chaser.exp(.125f));
        bodyAnimation.tickChaser();
    }

    private void tickHeadAnim() {
        boolean active = getHeatLevelFromBlock().isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && isValidBlockAbove();

        if (isSleeping()) {
            headAngle.chase((AngleHelper.horizontalAngle(getBlockState().getOptionalValue(BlazeBurnerBlock.FACING)
                    .orElse(Direction.SOUTH)) + 180) % 360, 0.125f, LerpedFloat.Chaser.EXP);
            headAngle.tickChaser();
        } else if (!active) {
            headAngle.chase(lookAtTarget(headAngle), 0.25f, LerpedFloat.Chaser.exp(15));
            headAngle.tickChaser();
        }

        headAnimation.chase(active ? 1 : 0, 0.25f, LerpedFloat.Chaser.exp(0.25f));
        headAnimation.tickChaser();
    }

    private static final double LOOK_RANGE = 10.0; // Look range in blocks

    private float lookAtTarget(LerpedFloat lerpedFloat) {
        float target = (AngleHelper.horizontalAngle(getBlockState().getOptionalValue(BlazeBurnerBlock.FACING)
                .orElse(Direction.SOUTH)) + 180) % 360;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && !player.isInvisible()) {
            double x, z, distSqr;
            double cx = getBlockPos().getX() + 0.5;
            double cy = getBlockPos().getY() + 0.5;
            double cz = getBlockPos().getZ() + 0.5;
            if (isVirtual()) {
                x = -4;
                z = -10;
                distSqr = 0;
            } else {
                x = player.getX();
                z = player.getZ();
                distSqr = player.distanceToSqr(cx, cy, cz);
            }
            if (distSqr <= LOOK_RANGE * LOOK_RANGE) {
                double dx = x - (getBlockPos().getX() + 0.5);
                double dz = z - (getBlockPos().getZ() + 0.5);
                target = AngleHelper.deg(-Mth.atan2(dz, dx)) - 90;
            }
        }
        return lerpedFloat.getValue() + AngleHelper.getShortestAngleDiff(lerpedFloat.getValue(), target);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);

        RegistryAccess registryAccess = registryAccess(registries);
        if (registryAccess != null) {
            Pokemon loaded = readPokemon(tag, registryAccess);
            if (loaded != null) pokemon = loaded;
        }
        if (tag.hasUUID("owner")) {
            ownerUUID = tag.getUUID("owner");
            ownerName = tag.getString("owner_name");
        } else {
            ownerUUID = null;
            ownerName = null;
        }
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        RegistryAccess registryAccess = registryAccess(registries);
        if (registryAccess != null && pokemon != null) {
            SlugmaBurnerHelper.savePokemon(compound, registryAccess, this.pokemon);
        }
        if (ownerUUID != null && ownerName != null) {
            compound.putUUID("owner", ownerUUID);
            compound.putString("owner_name", ownerName);
        }
    }

    public Pokemon getPokemon() { return pokemon; }
    public net.createmod.catnip.animation.LerpedFloat getHeadAnimation() { return headAnimation; }
    public net.createmod.catnip.animation.LerpedFloat getHeadAngle() { return headAngle; }
    public UUID getOwnerUUID() { return ownerUUID; }
    public LerpedFloat getBodyAngle() { return bodyAngle; }


    public void setOwner(UUID ownerUUID, String ownerName) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
    }
    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }

    public static @Nullable Pokemon readPokemon(CompoundTag nbt, RegistryAccess registryAccess) {
        if (!nbt.contains("pokemon", Tag.TAG_COMPOUND)) return null;
        try {
            return Pokemon.Companion.loadFromNBT(registryAccess, nbt.getCompound("pokemon"));
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    protected boolean tryUpdateFuel(ItemStack itemStack, boolean forceOverflow, boolean simulate) {
        if (isCreative()) return false;

        FuelType newFuel = FuelType.NONE;
        int newBurnTime;
        int pokemonLevel = pokemon.getLevel();
        float multiplier;

        if (pokemonLevel < 25) multiplier = 0.5f;
        else if (pokemonLevel < 50) multiplier = 1f;
        else if (pokemonLevel <= 99) multiplier = 1.5f;
        else multiplier = 2f;

        if (itemStack.getItemHolder().getData(CreateDataMaps.SUPERHEATED_BLAZE_BURNER_FUELS) != null) {
            newBurnTime = 3200;
            newFuel = FuelType.SPECIAL;
        } else {
            newBurnTime = itemStack.getBurnTime(RecipeType.SMELTING);
            if (newBurnTime > 0) {
                newFuel = FuelType.NORMAL;
            } else if (itemStack.getItemHolder().getData(CreateDataMaps.REGULAR_BLAZE_BURNER_FUELS) != null) {
                newBurnTime = 1600;
                newFuel = FuelType.NORMAL;
            }
        }

        if (newFuel == FuelType.NONE) return false;
        if (newFuel.ordinal() < activeFuel.ordinal()) return false;

        newBurnTime = Math.round(newBurnTime * multiplier);

        if (newFuel == activeFuel) {
            if (remainingBurnTime <= INSERTION_THRESHOLD) {
                newBurnTime += remainingBurnTime;
            } else if (forceOverflow && newFuel == FuelType.NORMAL) {
                if (remainingBurnTime < MAX_HEAT_CAPACITY) {
                    newBurnTime = Math.min(remainingBurnTime + newBurnTime, MAX_HEAT_CAPACITY);
                } else {
                    newBurnTime = remainingBurnTime;
                }
            } else {
                return false;
            }
        }

        FuelType finalNewFuel = newFuel;
        int finalNewBurnTime = newBurnTime;
        if (!simulate) {
            activeFuel = finalNewFuel;
            remainingBurnTime = finalNewBurnTime;

            assert level != null;
            if (level.isClientSide) {
                spawnParticleBurst(activeFuel == FuelType.SPECIAL);
                return true;
            }

            BlazeBurnerBlock.HeatLevel prev = getHeatLevelFromBlock();
            playSound();
            updateBlockState();

            if (prev != getHeatLevelFromBlock())
                level.playSound(null, worldPosition, SoundEvents.BLAZE_AMBIENT, SoundSource.BLOCKS,
                        .125f + level.random.nextFloat() * .125f, 1.15f - level.random.nextFloat() * .25f);
        }

        return true;
    }

    private static final int FADING_THRESHOLD_TICKS = 3600;

    @Override
    protected BlazeBurnerBlock.HeatLevel getHeatLevel() {
        BlazeBurnerBlock.HeatLevel level = BlazeBurnerBlock.HeatLevel.SMOULDERING;
        switch (activeFuel) {
            case SPECIAL -> level = BlazeBurnerBlock.HeatLevel.SEETHING;
            case NORMAL -> level = remainingBurnTime < FADING_THRESHOLD_TICKS
                    ? BlazeBurnerBlock.HeatLevel.FADING
                    : BlazeBurnerBlock.HeatLevel.KINDLED;
            default -> {}
        }
        return level;
    }

    protected void spawnParticles(BlazeBurnerBlock.HeatLevel heatLevel, double burstMult) {
        if (level == null)
            return;
        if (heatLevel == BlazeBurnerBlock.HeatLevel.NONE)
            return;

        RandomSource r = level.getRandom();

        Vec3 c = VecHelper.getCenterOf(worldPosition);
        Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .125f)
                .multiply(1, 0, 1));

        if (r.nextInt(4) != 0)
            return;

        boolean empty = level.getBlockState(worldPosition.above())
                .getCollisionShape(level, worldPosition.above())
                .isEmpty();

		if (empty || r.nextInt(8) == 0)
			level.addParticle(ParticleTypes.SMALL_FLAME, v.x, v.y, v.z, 0, 0, 0);

        double yMotion = empty ? .0625f : r.nextDouble() * .0125f;
        Vec3 v2 = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .5f)
                        .multiply(1, .25f, 1)
                        .normalize()
                        .scale((empty ? .25f : .5) + r.nextDouble() * .125f))
                .add(0, .5, 0);

        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
        } else if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            level.addParticle(ParticleTypes.FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        if (level == null || pokemon == null || ownerUUID == null || ownerName == null) return;
        CompoundTag tag = pokemon.saveToNBT(level.registryAccess(), new CompoundTag());
        components.set(ModComponents.POKEMON.get(), new StoredPokemon(tag, ownerUUID, ownerName));
    }

    @Override
    protected void applyImplicitComponents(@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        StoredPokemon stored = componentInput.get(ModComponents.POKEMON.get());
        if (stored == null || level == null) return;
        try {
            pokemon = Pokemon.Companion.loadFromNBT(level.registryAccess(), stored.pokemon());
        } catch (Exception ignored) {
        }
        ownerUUID = stored.owner();
        ownerName = stored.ownerName();
        if (!level.isClientSide) notifyUpdate();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        MutableComponent nickname = pokemon.getNickname();
        String speciesName = pokemon.getSpecies().getName();
        int level = pokemon.getLevel();
        boolean hasNickname = nickname != null;

        int color;
        if (level < 25) color = 0xFF5555;
        else if (level < 50) color = 0xFFFF55;
        else if (level <= 99) color = 0x55FF55;
        else color = 0x55FFFF;

        String fallback = "MissingNo.";

        if (hasNickname) {
            CreateLang.builder().add(nickname.copy()).text(0xAAAAAA, " ("+speciesName+")").forGoggles(tooltip);
        } else {
            CreateLang.builder().text(speciesName).forGoggles(tooltip);
        }
        if (ownerName != null && !ownerName.isEmpty()) CreateLang.builder().text(0xAAAAAA, "OT: ")
                .text(0xFFFFFF, ownerName).forGoggles(tooltip);
        else CreateLang.builder().text(0xAAAAAA, "OT: ")
                .text(0xFFFFFF, fallback).forGoggles(tooltip);
        CreateLang.builder().text("Lv. ").text(color, String.valueOf(level)).forGoggles(tooltip);

        return true;
    }

    @Override
    public ItemStack getIcon(boolean isPlayerSneaking) {
        PokeBall caughtBall = pokemon.getCaughtBall();

        // IDE warns that caughtBall is always true, but I prefer having some security for a potential NullPointerException
        return caughtBall.stack(1);
    }
}
