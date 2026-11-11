package com.twispan.create_encapsulated.block.movement_behaviour;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.block.renderer.SlugmaBurnerRenderer;
import com.twispan.create_encapsulated.util.slugma_burner.BlockPokemonState;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class SlugmaBurnerMovementBehaviour implements MovementBehaviour {

    private static class ClientData {
        final LerpedFloat headAngle;
        @Nullable Pokemon pokemon;
        @Nullable BlockPokemonState pokemonState;

        ClientData(float startAngle) {
            this.headAngle = LerpedFloat.angular().startWithValue(startAngle);
        }
    }

    @Override
    public @Nullable ItemStack canBeDisabledVia(MovementContext context) {
        return null;
    }

    @Override
    public void tick(MovementContext context) {
        if (!context.world.isClientSide) return;
        if (shouldRender(context)) return;

        ClientData data = getData(context);
        data.headAngle.chase(getTargetAngle(context), .25f, LerpedFloat.Chaser.exp(5));
        data.headAngle.tickChaser();
    }

    private ClientData getData(MovementContext context) {
        if (context.temporaryData instanceof ClientData existing) return existing;

        ClientData data = new ClientData(getTargetAngle(context));
        loadPokemon(context, data);
        context.temporaryData = data;
        return data;
    }

    private boolean shouldRender(MovementContext context) {
        return context.state.getOptionalValue(BlazeBurnerBlock.HEAT_LEVEL)
                .orElse(BlazeBurnerBlock.HeatLevel.NONE) == BlazeBurnerBlock.HeatLevel.NONE;
    }

    private float getTargetAngle(MovementContext context) {
        if (shouldRenderHat(context) && !Mth.equal(context.relativeMotion.length(), 0)
                && context.contraption.entity instanceof CarriageContraptionEntity cce) {

            float angle = AngleHelper.deg(-Mth.atan2(context.relativeMotion.y, context.relativeMotion.z));
            return cce.getInitialOrientation()
                    .getAxis() == Direction.Axis.X ? angle + 180 : angle;
        }

        Entity player = Minecraft.getInstance().cameraEntity;
        if (player != null && !player.isInvisible() && context.position != null) {
            Vec3 applyRotation = context.contraption.entity.reverseRotation(player.position()
                    .subtract(context.position), 1);
            double dx = applyRotation.x;
            double dz = applyRotation.z;
            return AngleHelper.deg(-Mth.atan2(dz, dx)) - 90;
        }
        return 0;
    }

    private boolean shouldRenderHat(MovementContext context) {
        CompoundTag data = context.data;
        if (!data.contains("Conductor"))
            data.putBoolean("Conductor", determineIfConducting(context));
        return data.getBoolean("Conductor") && (context.contraption.entity instanceof CarriageContraptionEntity cce)
                && cce.hasSchedule();
    }

    private boolean determineIfConducting(MovementContext context) {
        Contraption contraption = context.contraption;
        if (!(contraption instanceof CarriageContraption carriageContraption))
            return false;
        Direction assemblyDirection = carriageContraption.getAssemblyDirection();
        for (Direction direction : Iterate.directionsInAxis(assemblyDirection.getAxis()))
            if (carriageContraption.inControl(context.localPos, direction))
                return true;
        return false;
    }

    private void loadPokemon(MovementContext context, ClientData data) {
        CompoundTag nbt = context.blockEntityData;
        if (nbt == null) return;

        data.pokemon = SlugmaBurnerBlockEntity.readPokemon(nbt, context.world.registryAccess());
        if (data.pokemon != null)
            data.pokemonState = new BlockPokemonState();
    }

    @Override
    public boolean disableBlockEntityRendering() {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                    ContraptionMatrices matrices, MultiBufferSource buffer) {
        if (shouldRender(context)) return;

        ClientData data = getData(context);
        if (data.pokemon == null || data.pokemonState == null) return;

        SlugmaBurnerRenderer.renderInContraption(context, matrices, buffer, data.headAngle,
                shouldRenderHat(context), data.pokemon, data.pokemonState);
    }
}
