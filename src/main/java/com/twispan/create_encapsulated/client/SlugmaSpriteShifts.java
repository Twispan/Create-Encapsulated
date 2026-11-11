package com.twispan.create_encapsulated.client;

import com.simibubi.create.Create;
import com.twispan.create_encapsulated.CreateEncapsulated;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.resources.ResourceLocation;

public class SlugmaSpriteShifts {

    public static final SpriteShiftEntry
            SUPER_SLUGMA_BURNER_FLAME = get("block/slugma_burner/slugma_burner_flame_scroll"),
            SUPER_SHINY_SLUGMA_BURNER_FLAME = get("block/slugma_burner/slugma_burner_flame_scroll_shiny");

    private static SpriteShiftEntry get(String targetLocation) {
        return SpriteShifter.get(
                Create.asResource("block/blaze_burner_flame"),
                ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, targetLocation));
    }

    public static void init() {}
}
