package com.twispan.create_encapsulated.util.dynamo_podium;

import com.cobblemon.mod.common.api.berry.Berry;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.twispan.create_encapsulated.util.BerryHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DynamoPodiumHelperServer {
    private static final Set<String> VALID_SPECIES = Set.of("pikachu", "voltorb");

    public static Pokemon getDefaultPokemon(@Nullable ServerPlayer player) {
        try {
            PokemonProperties props = new PokemonProperties();
            props.setSpecies("Pikachu");
            props.setOriginalTrainer(player != null ? player.getGameProfile().getName() : "MissingNo");
            return props.create();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean isValidMon(Pokemon pokemon) {
        return VALID_SPECIES.contains(pokemon.getSpecies().getName().toLowerCase());
    }

    public static boolean getPreferredBerryByNature(Berry berry, Pokemon pokemon) {
        return BerryHelper.likesBerry(berry, pokemon.getNature());
    }

    public static boolean getDislikedBerryByNature(Berry berry, Pokemon pokemon) {
        return BerryHelper.dislikesBerry(berry, pokemon.getNature());
    }

    public static void savePokemon(CompoundTag tag, RegistryAccess registryAccess, Pokemon pokemon) {
        tag.put("pokemon", pokemon.saveToNBT(registryAccess, new CompoundTag()));
    }
}
