package com.twispan.create_encapsulated.block.dynamo_podium.entity;

import com.cobblemon.mod.common.api.berry.Berry;
import com.cobblemon.mod.common.item.berry.BerryItem;
import com.cobblemon.mod.common.pokemon.Pokemon;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import com.twispan.create_encapsulated.block.dynamo_podium.DynamoPodiumBlock;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import com.twispan.create_encapsulated.util.dynamo_podium.DynamoPodiumHelperServer;
import com.twispan.create_encapsulated.util.BlockPokemonState;

import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.AngleHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DynamoPodiumBlockEntity extends GeneratingKineticBlockEntity {

    public BlockPokemonState blockState;

    protected LerpedFloat bodyAnimation;
    protected LerpedFloat bodyAngle;

    protected LerpedFloat headAnimation;
    protected LerpedFloat headAngle;

    private Pokemon pokemon;

    private UUID ownerUUID;
    private String ownerName;

    private int boostTicks;
    protected float stressGenerated;
    protected boolean reversed;

    private static final float GENERATED_RPM = 16f;
    private static final int PREFERRED_TICKS = 6000;
    private static final int NEUTRAL_TICKS = 3000;
    private static final int DISLIKED_TICKS = 1500;
    private static final float PREFERRED_SU = 2048f;
    private static final float NEUTRAL_SU = 1024f;
    private static final float DISLIKED_SU = 512f;


    public DynamoPodiumBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        blockState = new BlockPokemonState();
        bodyAnimation = LerpedFloat.linear();
        bodyAngle = LerpedFloat.angular();
        bodyAngle.startWithValue((AngleHelper.horizontalAngle(state.getOptionalValue(DynamoPodiumBlock.FACING)
                .orElse(Direction.SOUTH)) + 180) % 360);

        headAnimation = LerpedFloat.linear();
        headAngle = LerpedFloat.angular();
        headAngle.startWithValue((AngleHelper.horizontalAngle(state.getOptionalValue(DynamoPodiumBlock.FACING)
                .orElse(Direction.SOUTH)) + 180) % 360);
    }

    public DynamoPodiumBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.DYNAMO_PODIUM.get(), pos, state);
    }

    @Nullable
    private RegistryAccess registryAccess(HolderLookup.Provider registries) {
        if (registries instanceof RegistryAccess access) return access;
        return level != null ? level.registryAccess() : null;
    }

    public static ServerPlayer getPlayerByUUID(UUID ownerUUID) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return (server == null || ownerUUID == null) ? null : server.getPlayerList().getPlayer(ownerUUID);
    }

    public @Nullable Pokemon getPokemon() {
        if (pokemon == null && level != null && !level.isClientSide) {
            pokemon = DynamoPodiumHelperServer.getDefaultPokemon(getPlayerByUUID(ownerUUID));
            setChanged();
        }
        return pokemon;
    }

    public static @Nullable Pokemon readPokemon(CompoundTag nbt, RegistryAccess registryAccess) {
        if (!nbt.contains("pokemon", Tag.TAG_COMPOUND)) return null;
        try {
            return Pokemon.Companion.loadFromNBT(registryAccess, nbt.getCompound("pokemon"));
        } catch (Exception ignored) {
            return null;
        }
    }

    public net.createmod.catnip.animation.LerpedFloat getHeadAnimation() { return headAnimation; }
    public net.createmod.catnip.animation.LerpedFloat getHeadAngle() { return headAngle; }
    public UUID getOwnerUUID() { return ownerUUID; }
    public String getOwnerName() { return ownerName; }
    public LerpedFloat getBodyAngle() { return bodyAngle; }

    public void setOwner(UUID ownerUUID, String ownerName) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
    }
    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide && boostTicks > 0 && --boostTicks == 0) {
            stressGenerated = 0;
            updateGeneratedRotation();
            setChanged();
            sendData();
        }
        if (level != null && level.isClientSide) {
            tickHeadAnim();
            tickBodyAnim();
        }
        super.tick();
    }

    public static final float MAX_HEAD_TURN = 60f;
    private static final float BODY_DEADZONE = 35f;
    private static final float BODY_CATCHUP = 0.25f;

    private void tickBodyAnim() {
        float head = headAngle.getValue();
        float body = bodyAngle.getValue();
        float diff = AngleHelper.getShortestAngleDiff(body, head);
        float excess = Math.abs(diff) - BODY_DEADZONE;

        float target = excess > 0 ? body + Math.signum(diff) * excess : body;
        bodyAngle.chase(target, BODY_CATCHUP, LerpedFloat.Chaser.EXP);
        bodyAngle.tickChaser();

        bodyAnimation.chase(0, .125f, LerpedFloat.Chaser.exp(.125f));
        bodyAnimation.tickChaser();
    }

    private void tickHeadAnim() {
        headAngle.chase(lookAtTarget(headAngle), 0.25f, LerpedFloat.Chaser.exp(15));
        headAngle.tickChaser();

        headAnimation.chase(0, 0.25f, LerpedFloat.Chaser.exp(0.25f));
        headAnimation.tickChaser();
    }

    private static final double LOOK_RANGE = 10.0; // Look range in blocks

    private float lookAtTarget(LerpedFloat lerpedFloat) {
        float target = (AngleHelper.horizontalAngle(getBlockState().getOptionalValue(DynamoPodiumBlock.FACING)
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
        boostTicks = tag.getInt("boost_ticks");
        stressGenerated = tag.getFloat("stress");
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        RegistryAccess registryAccess = registryAccess(registries);
        if (registryAccess != null && pokemon != null) {
            DynamoPodiumHelperServer.savePokemon(compound, registryAccess, this.pokemon);
        }
        if (ownerUUID != null && ownerName != null) {
            compound.putUUID("owner", ownerUUID);
            compound.putString("owner_name", ownerName);
        }
        compound.putInt("boost_ticks", boostTicks);
        compound.putFloat("stress", stressGenerated);
    }

    public boolean tryFeedBerry(ItemStack stack, boolean simulate) {
        if (level == null || level.isClientSide) return false;
        if (!(stack.getItem() instanceof BerryItem berryItem)) return false;
        if (boostTicks > 0) return false;

        Pokemon p = getPokemon();
        Berry berry = berryItem.berry();
        if (p == null || berry == null) return false;
        if (simulate) return true;

        boolean likes = DynamoPodiumHelperServer.getPreferredBerryByNature(berry, p);
        boolean dislikes = DynamoPodiumHelperServer.getDislikedBerryByNature(berry, p);

        if (likes && !dislikes) {
            stressGenerated = PREFERRED_SU; boostTicks = PREFERRED_TICKS;
        } else if (!likes && dislikes) {
            stressGenerated = DISLIKED_SU; boostTicks = DISLIKED_TICKS;
        } else {
            stressGenerated = NEUTRAL_SU; boostTicks = NEUTRAL_TICKS;
        }

        updateGeneratedRotation();
        setChanged();
        sendData();

        return true;
    }

    @Override
    public float getGeneratedSpeed() {
        return boostTicks > 0 ? (reversed ? -GENERATED_RPM : GENERATED_RPM) : 0;
    }

    @Override
    public float calculateAddedStressCapacity() {
        float speed = Mth.abs(getGeneratedSpeed());
        lastCapacityProvided = speed == 0 ? 0 : stressGenerated / speed;
        return lastCapacityProvided;
    }
}
