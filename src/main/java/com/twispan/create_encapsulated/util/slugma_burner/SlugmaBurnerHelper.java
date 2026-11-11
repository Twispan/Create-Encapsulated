package com.twispan.create_encapsulated.util.slugma_burner;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.VaryingModelRepository;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;
import kotlin.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Set;
import java.util.UUID;

public class SlugmaBurnerHelper {
    public static Pokemon getDefaultSlugma(@Nullable ServerPlayer player) {
        PokemonProperties pokemon = new PokemonProperties();
        pokemon.setSpecies("Slugma");
        pokemon.setOriginalTrainer(player != null ? player.getGameProfile().getName() : "MissingNo.");
        return pokemon.create();
    }

    public static boolean isSlugma(Pokemon pokemon) {
        return pokemon.getSpecies().getName().equals("Slugma");
    }

    public static void popPokemonEntityToItemStack(ItemStack stack, PokemonEntity pokemonEntity) {
        Pokemon pokemon = pokemonEntity.getPokemon();
        ServerPlayer player = pokemon.getOwnerPlayer();
        pokemonEntity.discard();

        if (player == null) return;

        CompoundTag tag = pokemon.saveToNBT(pokemonEntity.registryAccess(), new CompoundTag());
        UUID ownerId = player.getUUID();
        String ownerName = player.getGameProfile().getName();
        stack.set(ModComponents.POKEMON.get(), new StoredPokemon(tag, ownerId, ownerName));

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        if (!party.remove(pokemon)) {
            Cobblemon.INSTANCE.getStorage().getPC(player).remove(pokemon);
        }
    }

    public static void savePokemon(CompoundTag tag, RegistryAccess registryAccess, Pokemon pokemon) {
        tag.put("pokemon", pokemon.saveToNBT(registryAccess, new CompoundTag()));
    }

    public record AccessoryLocator(
            @Nullable Matrix4f hat
    ) {}

    public static AccessoryLocator drawPokemon(Species species, Set<String> aspects, String pose, PoseStack ms, PosableState state,
                                   float headYaw, float bodyYaw, float xOffset, float yOffset,
                                   float zOffset, float partialTicks, int packedLight) {
        ResourceLocation id = species.getResourceIdentifier();

        state.setCurrentAspects(aspects);
        state.updatePartialTicks(partialTicks);

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
        context.put(RenderContext.Companion.getDO_QUIRKS(), false);


        state.setCurrentModel(model);

        var poseDef = model.getPose(pose);
        if (poseDef != null && !poseDef.getPoseName().equals(state.getCurrentPose())) {
            state.setPose(poseDef.getPoseName());
        }

        float headTurnMin = -SlugmaBurnerBlockEntity.MAX_HEAD_TURN;
        float headTurnMax = SlugmaBurnerBlockEntity.MAX_HEAD_TURN;

        float relativeHeadYaw = Mth.clamp(Mth.wrapDegrees(bodyYaw - headYaw), headTurnMin, headTurnMax);

        model.applyAnimations(null, state, 0F, 0F,
                state.getAnimationSeconds() * 20F, relativeHeadYaw, 0F);

        var locator = state.getLocatorStates();
        var hatLocator = locator.get("item_hat");

        Matrix4f hatMatrix = null;

        if (hatLocator != null) {

            hatLocator.getOrigin();

            hatMatrix = new Matrix4f(hatLocator.getMatrix());
            hatMatrix.setTranslation(
                    (float) hatLocator.getOrigin().x,
                    (float) hatLocator.getOrigin().y,
                    (float) hatLocator.getOrigin().z
            );
        }

        RenderSystem.applyModelViewMatrix();

        ms.pushPose();
        applyPokeTransform(ms, bodyYaw, xOffset, yOffset, zOffset);

        MultiBufferSource.BufferSource immediate =
                Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = immediate.getBuffer(RenderType.entityCutout(texture));

        model.withLayerContext(immediate, state,
                VaryingModelRepository.INSTANCE.getLayers(id, state), () -> {
                    model.render(context, ms, consumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
                    immediate.endBatch();
                    return Unit.INSTANCE;
                });

        ms.popPose();
        model.setDefault();

        return new AccessoryLocator(hatMatrix);
    }

    public static void applyPokeTransform(PoseStack ms, float bodyYaw, float xOffset, float yOffset, float zOffset) {
        ms.scale(0.7f, 0.7f, 0.7f);
        ms.translate(0.714f, 0.2f, 0.714f);
        ms.mulPose(Axis.XP.rotationDegrees(180.0f));
        ms.mulPose(Axis.YP.rotationDegrees(180.0f - bodyYaw));
        ms.translate(xOffset, yOffset, zOffset);
    }
}
