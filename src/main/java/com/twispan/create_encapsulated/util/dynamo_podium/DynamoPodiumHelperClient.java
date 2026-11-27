package com.twispan.create_encapsulated.util.dynamo_podium;

import com.cobblemon.mod.common.client.render.models.blockbench.PosableState;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.VaryingModelRepository;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.twispan.create_encapsulated.block.dynamo_podium.entity.DynamoPodiumBlockEntity;
import com.twispan.create_encapsulated.util.BlockPokemonState;
import kotlin.Unit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import java.util.Set;

public class DynamoPodiumHelperClient {
    public static void drawPokemon(Level level, Species species, Set<String> aspects, String pose,
                                   PoseStack ms, PosableState state, MultiBufferSource bufferSource,
                                   float headYaw, float bodyYaw, float xOffset, float yOffset,
                                   float zOffset, float partialTicks, int packedLight) {
        ResourceLocation id = species.getResourceIdentifier();

        state.setCurrentAspects(aspects);

        float animationTicks = level.getGameTime() + partialTicks;

        if (state instanceof BlockPokemonState blockPokemonState) {
            blockPokemonState.advance(animationTicks);
            animationTicks = blockPokemonState.totalPartialTicks;
        } else {
            state.updatePartialTicks(partialTicks);
        }

        var model = VaryingModelRepository.INSTANCE.getPoser(id, state);
        ResourceLocation texture = VaryingModelRepository.INSTANCE.getTexture(id, state);

        RenderContext context = new RenderContext();
        model.setContext(context);
        context.put(RenderContext.Companion.getTEXTURE(),
                VaryingModelRepository.INSTANCE.getTextureNoSubstitute(id, state));
        context.put(RenderContext.Companion.getSCALE(), species.getForm(aspects).getBaseScale());
        context.put(RenderContext.Companion.getSPECIES(), id);
        context.put(RenderContext.Companion.getRENDER_STATE(), RenderContext.RenderState.WORLD);
        context.put(RenderContext.Companion.getPOSABLE_STATE(), state);
        context.put(RenderContext.Companion.getDO_QUIRKS(), true);

        state.setCurrentModel(model);

        var poseDef = model.getPose(pose);
        if (poseDef != null && !poseDef.getPoseName().equals(state.getCurrentPose())) {
            state.setPose(poseDef.getPoseName());
        }

        float headTurnMin = -DynamoPodiumBlockEntity.MAX_HEAD_TURN;
        float headTurnMax = DynamoPodiumBlockEntity.MAX_HEAD_TURN;

        float relativeHeadYaw = Mth.clamp(Mth.wrapDegrees(bodyYaw - headYaw), headTurnMin, headTurnMax);

        model.applyAnimations(null, state, 0F, 0F,
                animationTicks, relativeHeadYaw, 0F);

        RenderSystem.applyModelViewMatrix();

        ms.pushPose();
        applyPokeTransForm(ms, bodyYaw, xOffset, yOffset, zOffset);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(texture));

        model.withLayerContext(bufferSource, state,
                VaryingModelRepository.INSTANCE.getLayers(id, state), () -> {
                    model.render(context, ms, consumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
                    return Unit.INSTANCE;
                });

        ms.popPose();
        model.setDefault();
    }

    private static void applyPokeTransForm(PoseStack ms, float bodyYaw, float xOffset, float yOffset, float zOffset) {
        ms.scale(1, 1, 1);
        ms.scale(0.5f, 0.5f, 0.5f);
        ms.mulPose(Axis.XP.rotationDegrees(180.0f));
        ms.mulPose(Axis.YP.rotationDegrees(180.0f - bodyYaw));
        ms.translate(xOffset, yOffset, zOffset);
    }
}
