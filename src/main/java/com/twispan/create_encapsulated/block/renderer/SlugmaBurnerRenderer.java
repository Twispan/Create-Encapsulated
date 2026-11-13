package com.twispan.create_encapsulated.block.renderer;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.twispan.create_encapsulated.block.SlugmaBurnerBlock;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.client.SlugmaPartialModels;
import com.twispan.create_encapsulated.client.SlugmaSpriteShifts;
import com.twispan.create_encapsulated.util.slugma_burner.BlockPokemonState;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class SlugmaBurnerRenderer extends SafeBlockEntityRenderer<SlugmaBurnerBlockEntity> {

    private static final boolean WHOLE_SLUGMA_FOLLOWS_HEAD = false;

    private static final float HAT_SCALE = 0.5f;

    private static final float HAT_OFFSET_X = 0.0f;
    private static final float HAT_OFFSET_Y = -0.14f;
    private static final float HAT_OFFSET_Z = -0.02f;

    private static final float HAT_ROTATION_X = -45.0f;
    private static final float HAT_ROTATION_Z = 0.0f;

    public SlugmaBurnerRenderer(BlockEntityRendererProvider.Context context) {
    }


    @Override
    protected void renderSafe(SlugmaBurnerBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        BlazeBurnerBlock.HeatLevel heat = blockEntity.getHeatLevelFromBlock();
        if (heat == BlazeBurnerBlock.HeatLevel.NONE) return;

        Pokemon pokemon = blockEntity.getPokemon();
        if (pokemon == null) return;

        float animation = blockEntity.getHeadAnimation().getValue(partialTicks) * 0.175f;
        float headAngleRad = AngleHelper.rad(blockEntity.getHeadAngle().getValue(partialTicks));
        float bodyAngleDeg = WHOLE_SLUGMA_FOLLOWS_HEAD
                ? AngleHelper.deg(headAngleRad)
                : blockEntity.getBodyAngle().getValue(partialTicks);

        PartialModel hat = blockEntity.hat ? AllPartialModels.TRAIN_HAT
                : blockEntity.stockKeeper ? SlugmaPartialModels.LOGISTICS_HAT
                : null;

        renderShared(ms, null, bufferSource, blockEntity.getLevel(), blockEntity.getBlockState(), heat,
                pokemon, blockEntity.pokemonState, animation, bodyAngleDeg, headAngleRad, hat, partialTicks);
    }

    public static void renderInContraption(MovementContext context, ContraptionMatrices matrices,
                                     MultiBufferSource source, LerpedFloat headAngle,
                                     boolean isConductor, Pokemon pokemon, BlockPokemonState pokemonState) {
        BlockState state = context.state;
        BlazeBurnerBlock.HeatLevel heatLevel = SlugmaBurnerBlock.getHeatLevelOf(state);
        if (heatLevel == BlazeBurnerBlock.HeatLevel.NONE) return;

        if (!heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) heatLevel = BlazeBurnerBlock.HeatLevel.FADING;

        Level level = context.world;
        float partialTicks = AnimationTickHolder.getPartialTicks(level);
        float headAngleRad = AngleHelper.rad(headAngle.getValue(partialTicks));
        boolean drawHat = isConductor || context.blockEntityData.contains("TrainHat");

        renderShared(matrices.getViewProjection(), matrices.getModel(), source, level, state, heatLevel, pokemon,
                pokemonState, 0f, AngleHelper.deg(headAngleRad), headAngleRad,
                drawHat ? AllPartialModels.TRAIN_HAT : null, partialTicks);
    }

    public static void renderShared(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource source,
                                    Level level, BlockState blockState, BlazeBurnerBlock.HeatLevel heat,
                                    Pokemon pokemon, BlockPokemonState pokemonState, float animation,
                                    float bodyAngleDeg, float headAngleRad,
                                    @Nullable PartialModel hat, float partialTicks) {

        boolean inContraption = modelTransform != null;
        boolean awake = inContraption || hat != null ||
                heat.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING);
        boolean superHeated = heat.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING);
        boolean kindled = heat.isAtLeast(BlazeBurnerBlock.HeatLevel.KINDLED);
        boolean blockAbove = animation > 0.125f;
        String pose = !awake ? "sleep" : kindled ? "active" : "standing";

        Set<String> aspects = new HashSet<>(pokemon.getAspects());
        aspects.add(superHeated ? "super_heated" : "burner");
        boolean shiny = aspects.contains("shiny");

        ms.pushPose();
        if (modelTransform != null) {
            ms.last().pose().mul(modelTransform.last().pose());
            ms.last().normal().mul(modelTransform.last().normal());
        }
        var locators = SlugmaBurnerHelper.drawPokemon(
                level, pokemon.getSpecies(), aspects, pose, ms, pokemonState,
                AngleHelper.deg(headAngleRad), bodyAngleDeg,
                0f, -0.1f, 0.2f,
                partialTicks, LightTexture.FULL_BRIGHT);
        ms.popPose();

        if (superHeated && blockAbove) {
            SpriteShiftEntry shift = shiny ? SlugmaSpriteShifts.SUPER_SHINY_SLUGMA_BURNER_FLAME
                    : SlugmaSpriteShifts.SUPER_SLUGMA_BURNER_FLAME;
            assert modelTransform != null;
            renderFlame(ms, modelTransform, source, level, blockState, heat, shift, headAngleRad, partialTicks);
        }

        PartialModel baseModel = shiny
                ? (superHeated ? SlugmaPartialModels.BASE_SUPERHEATED_SHINY : SlugmaPartialModels.BASE_SHINY)
                : (superHeated ? SlugmaPartialModels.BASE_SUPERHEATED : SlugmaPartialModels.BASE);
        SuperByteBuffer base = CachedBuffers.partial(baseModel, blockState);
        if (modelTransform != null) base.transform(modelTransform);
        base.translate(0, 0.251, 0);
        draw(base, 0, ms, source.getBuffer(RenderType.solid()));

        if (hat != null && locators.hat() != null) {
            ms.pushPose();

            if (modelTransform != null) {
                ms.last().pose().mul(modelTransform.last().pose());
                ms.last().normal().mul(modelTransform.last().normal());
            }

            SlugmaBurnerHelper.applyPokeTransform(ms, bodyAngleDeg, 0f, -0.1f, 0.2f);

            ms.mulPose(locators.hat());
            ms.translate(HAT_OFFSET_X, HAT_OFFSET_Y, HAT_OFFSET_Z);
            ms.mulPose(Axis.XP.rotationDegrees(HAT_ROTATION_X));
            ms.mulPose(Axis.YP.rotationDegrees(180f));
            ms.mulPose(Axis.ZP.rotationDegrees(HAT_ROTATION_Z));
            ms.scale(HAT_SCALE, HAT_SCALE, HAT_SCALE);

            SuperByteBuffer hatBuf = CachedBuffers.partial(hat, blockState);

            hatBuf.light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, source.getBuffer(RenderType.cutoutMipped()));
            ms.popPose();
        }
    }

    private static void renderFlame(PoseStack ms, @Nullable PoseStack modelTransform, MultiBufferSource bufferSource,
                                    Level level, BlockState blockState, BlazeBurnerBlock.HeatLevel heatLevel,
                                    SpriteShiftEntry shift, float angle, float partialTicks) {
        float time = level.getGameTime() + partialTicks;

        float spriteWidth = shift.getTarget().getU1() - shift.getTarget().getU0();
        float spriteHeight = shift.getTarget().getV1() - shift.getTarget().getV0();
        float speed = 1 / 32f + 1 / 64f * heatLevel.ordinal();

        double vScroll = speed * time;
        vScroll = (vScroll - Math.floor(vScroll)) * spriteHeight / 2;
        double uScroll = speed * time / 2;
        uScroll = (uScroll - Math.floor(uScroll)) * spriteWidth / 2;

        SuperByteBuffer flame = CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, blockState);
        if (modelTransform != null) flame.transform(modelTransform);
        flame.shiftUVScrolling(shift, (float) uScroll, (float) vScroll);
        draw(flame, angle, ms, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    private static void draw(SuperByteBuffer buffer, float angle, PoseStack ms, VertexConsumer vertexConsumer) {
        buffer.rotateCentered(angle, Direction.UP)
                .light(LightTexture.FULL_BRIGHT)
                .renderInto(ms, vertexConsumer);
    }

}

