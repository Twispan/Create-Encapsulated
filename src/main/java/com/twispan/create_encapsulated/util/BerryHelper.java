package com.twispan.create_encapsulated.util;

import com.cobblemon.mod.common.api.cooking.Flavour;
import com.cobblemon.mod.common.api.berry.Berry;
import com.cobblemon.mod.common.pokemon.Nature;

import java.util.Map;

public class BerryHelper {
    private BerryHelper(){}

    private record Preference(Flavour liked, Flavour disliked) {}

    private static final Map<String, Preference> PREFERENCES = Map.ofEntries(
            // Spicy
            Map.entry("adamant", new Preference(Flavour.SPICY, Flavour.DRY)),
            Map.entry("brave", new Preference(Flavour.SPICY, Flavour.SWEET)),
            Map.entry("naughty", new Preference(Flavour.SPICY, Flavour.BITTER)),
            Map.entry("lonely", new Preference(Flavour.SPICY, Flavour.SOUR)),

            // Dry
            Map.entry("modest", new Preference(Flavour.DRY, Flavour.SPICY)),
            Map.entry("quiet", new Preference(Flavour.DRY, Flavour.SWEET)),
            Map.entry("rash", new Preference(Flavour.DRY, Flavour.BITTER)),
            Map.entry("mild", new Preference(Flavour.DRY, Flavour.SOUR)),

            // Sweet
            Map.entry("timid", new Preference(Flavour.SWEET, Flavour.SPICY)),
            Map.entry("jolly", new Preference(Flavour.SWEET, Flavour.DRY)),
            Map.entry("naive", new Preference(Flavour.SWEET, Flavour.BITTER)),
            Map.entry("hasty", new Preference(Flavour.SWEET, Flavour.SOUR)),

            // Bitter
            Map.entry("calm", new Preference(Flavour.BITTER, Flavour.SPICY)),
            Map.entry("careful", new Preference(Flavour.BITTER, Flavour.DRY)),
            Map.entry("sassy", new Preference(Flavour.BITTER, Flavour.SWEET)),
            Map.entry("gentle", new Preference(Flavour.BITTER, Flavour.SOUR)),

            // Sour
            Map.entry("bold", new Preference(Flavour.SOUR, Flavour.SPICY)),
            Map.entry("impish", new Preference(Flavour.SOUR, Flavour.DRY)),
            Map.entry("relaxed", new Preference(Flavour.SOUR, Flavour.SWEET)),
            Map.entry("lax", new Preference(Flavour.SOUR, Flavour.BITTER))
    );

    private static int getPotency(Berry berry, Flavour flavour) {
        return berry.getFlavours().getOrDefault(flavour, 0);
    }

    public static boolean likesBerry(Berry berry, Nature nature) {
        Preference pref = PREFERENCES.get(nature.getName().getPath());
        return pref != null && getPotency(berry, pref.liked()) > 0;
    }

    public static boolean dislikesBerry(Berry berry, Nature nature) {
        Preference pref = PREFERENCES.get(nature.getName().getPath());
        return pref != null && getPotency(berry, pref.disliked()) > 0;
    }
}
