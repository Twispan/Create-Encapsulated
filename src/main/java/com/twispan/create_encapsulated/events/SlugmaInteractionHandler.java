package com.twispan.create_encapsulated.events;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.SlugmaBurnerBlock;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.registries.ModSounds;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@EventBusSubscriber(modid = CreateEncapsulated.MODID)
public class SlugmaInteractionHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack held = event.getItemStack();
        if (!held.is(AllItems.EMPTY_BLAZE_BURNER.get())) return;
        if (!(event.getTarget() instanceof PokemonEntity pokemon)) return;
        if (!SlugmaBurnerHelper.isSlugma(pokemon.getPokemon())) return;

        Player player = event.getEntity();
        Level level = event.getLevel();
        UUID owner = pokemon.getOwnerUUID();

        boolean allowed = owner != null && owner.equals(player.getUUID());
        if (!allowed || pokemon.isBattling() || pokemon.isBusy()) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
            return;
        }

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pokemon.blockPosition(), ModSounds.SLUGMA_CRY.get(),
                    SoundSource.NEUTRAL, 0.5f, 0.8f);
            serverLevel.playSound(null, pokemon.blockPosition(), SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.NEUTRAL, 0.7f, 0.6f);
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    pokemon.getX(), pokemon.getY() + pokemon.getBbHeight() / 2, pokemon.getZ(),
                    25,
                    0.05, 0.05 ,0.05,
                    0.03);
            ItemStack filled = ModBlocks.SLUGMA_BURNER.toStack();
            SlugmaBurnerHelper.popPokemonEntityToItemStack(filled, pokemon);
            if (!player.isCreative()) held.shrink(1);
            if (held.isEmpty()) player.setItemInHand(event.getHand(), filled);
            else player.getInventory().placeItemBackInInventory(filled);
        }

        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(
                RetrieveSlugmaPayload.TYPE,
                RetrieveSlugmaPayload.STREAM_CODEC,
                ((payload, context) ->
                        SlugmaInteractionHandler.handleHotkey(
                                (ServerPlayer) context.player(), payload.pos(), payload.slot())));
    }

    private static void handleHotkey(ServerPlayer player, BlockPos pos, int slot) {
        if (player == null || pos == null) return;
        if (!player.canInteractWithBlock(pos, 1.0)) return;
        ServerLevel level = player.serverLevel();
        if (!level.isLoaded(pos)) return;

        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof SlugmaBurnerBlock) retrieveSlugma(player, pos);
        else if (AllBlocks.BLAZE_BURNER.has(state)) storeSlugma(player, pos, slot);
    }


    private static void retrieveSlugma(ServerPlayer player, BlockPos pos) {
        ServerLevel level = player.serverLevel();
        BlockState state = level.getBlockState(pos);
        if (!(level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity burner)) return;

        if (!player.getUUID().equals(burner.getOwnerUUID())) return;

        Pokemon pokemon = burner.getPokemon();
        if (pokemon == null) return;

        PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        PCStore pcStore = Cobblemon.INSTANCE.getStorage().getPC(player);

        BlockState empty = AllBlocks.BLAZE_BURNER.getDefaultState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.NONE)
                .setValue(BlazeBurnerBlock.FACING, state.getValue(BlazeBurnerBlock.FACING));

        if (!level.setBlock(pos, empty, 3)) return;

        if (!partyStore.add(pokemon) && !pcStore.add(pokemon)) {
            level.setBlock(pos, state, 3);
            if (level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity blockEntity) {
                blockEntity.setPokemon(pokemon);
                blockEntity.setOwner(player.getUUID(), player.getGameProfile().getName());
                blockEntity.notifyUpdate();
            }
            player.displayClientMessage(Component.literal("Your Party and PC are full!"), true);
            player.playSound(SoundEvents.NOTE_BLOCK_HARP.value(), 1.0f, 0.5f);
        }
//        level.playSound(null, pos, ModSoundEvents, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private static void storeSlugma(ServerPlayer player, BlockPos pos, int slot) {
        if (!player.canInteractWithBlock(pos, 4.0)) return;
        ServerLevel level = player.serverLevel();
        if (!level.isLoaded(pos)) return;

        BlockState state = level.getBlockState(pos);
        if (!AllBlocks.BLAZE_BURNER.has(state)
                || state.getValue(BlazeBurnerBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.NONE) return;

        PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player.getUUID(), level.registryAccess());
        if (slot < 0 || slot >= 6) return;
        Pokemon pokemon = partyStore.get(slot);
        if (pokemon == null || !SlugmaBurnerHelper.isSlugma(pokemon)) return;
        if (pokemon.getEntity() != null) return;

        BlockState filled = ModBlocks.SLUGMA_BURNER.get().defaultBlockState()
                .setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING)
                .setValue(BlazeBurnerBlock.FACING, state.getValue(BlazeBurnerBlock.FACING));

        if (!partyStore.remove(pokemon)) return;
        if (!level.setBlock(pos, filled, 3)) { partyStore.add(pokemon); return; }

        if (level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity blockEntity) {
            blockEntity.setPokemon(pokemon);
            blockEntity.setOwner(player.getUUID(), player.getGameProfile().getName());
            blockEntity.notifyUpdate();
        }
    }

    public record RetrieveSlugmaPayload(BlockPos pos, int slot) implements CustomPacketPayload {
        public static final Type<RetrieveSlugmaPayload> TYPE = new Type<>(
                ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "retrieve_slugma"));

        public static final StreamCodec<ByteBuf, RetrieveSlugmaPayload> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, RetrieveSlugmaPayload::pos,
                ByteBufCodecs.INT, RetrieveSlugmaPayload::slot,
                RetrieveSlugmaPayload::new);

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
    }
}
