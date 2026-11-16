package com.twispan.create_encapsulated.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.logistics.stockTicker.StockKeeperRequestScreen;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.twispan.create_encapsulated.block.slugma_burner.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.block.slugma_burner.renderer.SlugmaBurnerRenderer;
import com.twispan.create_encapsulated.client.slugma_burner.SlugmaPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StockKeeperRequestScreen.class)
public class StockKeeperRequestScreenMixin {

    @WrapOperation(
            method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lcom/simibubi/create/content/processing/burner/BlazeBurnerRenderer;renderShared(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lcom/simibubi/create/content/processing/burner/BlazeBurnerBlock$HeatLevel;FFZZLdev/engine_room/flywheel/lib/model/baked/PartialModel;I)V"))
    private void create_encapsulated$renderSlugma(
            PoseStack ms, PoseStack modelTransform, MultiBufferSource bufferSource, Level level, BlockState blockState,
            BlazeBurnerBlock.HeatLevel heatLevel, float animation, float horizontalAngle, boolean canDrawFlame,
            boolean drawGoggles, PartialModel drawHat, int hashCode, Operation<Void> original,
            @Local(name = "keeperBE") BlazeBurnerBlockEntity keeperBE) {
        drawGoggles = false;

        if (keeperBE instanceof SlugmaBurnerBlockEntity blockEntity && blockEntity.getPokemon() != null) {
            SlugmaBurnerRenderer.renderShared(ms, null, bufferSource, level, blockState, heatLevel,
                    blockEntity.getPokemon(), blockEntity.pokemonState, animation, 270f, horizontalAngle,
                    SlugmaPartialModels.LOGISTICS_HAT, AnimationTickHolder.getPartialTicks());
            return;
        }
        original.call(ms, modelTransform, bufferSource, level, blockState, heatLevel, animation, horizontalAngle,
                canDrawFlame, drawGoggles, drawHat, hashCode);
    }

    @WrapOperation(
            method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/createmod/catnip/render/SuperByteBuffer;renderInto(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V"))
    private void encapsulated$skipCage(SuperByteBuffer buffer, PoseStack ms, VertexConsumer vc,
                                       Operation<Void> original,
                                       @Local(name = "keeperBE") BlazeBurnerBlockEntity keeperBE) {
        if (keeperBE instanceof SlugmaBurnerBlockEntity) return;
        original.call(buffer, ms, vc);
    }
}
