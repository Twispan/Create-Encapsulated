package com.twispan.create_encapsulated.util.slugma_burner;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

// Server-side helper
public class SlugmaBurnerHelperServer {
    public static Pokemon getDefaultSlugma(@Nullable ServerPlayer player) {
        try {
            PokemonProperties props = new PokemonProperties();
            props.setSpecies("Slugma");
            props.setOriginalTrainer(player != null ? player.getGameProfile().getName() : "MissingNo.");
            return props.create();
        } catch(Exception ignored) {
            return null;
        }
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
}
