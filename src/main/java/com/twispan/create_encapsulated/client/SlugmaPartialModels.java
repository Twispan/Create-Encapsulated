package com.twispan.create_encapsulated.client;

import com.twispan.create_encapsulated.CreateEncapsulated;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class SlugmaPartialModels {
    public static final PartialModel BASE = block("slugma_burner/slugma/slugma_base");
    public static final PartialModel BASE_SHINY = block("slugma_burner/slugma/slugma_base_shiny");
    public static final PartialModel BASE_SUPERHEATED = block("slugma_burner/slugma/slugma_base_superheated");
    public static final PartialModel BASE_SUPERHEATED_SHINY = block("slugma_burner/slugma/slugma_base_superheated_shiny");
    public static final PartialModel LOGISTICS_HAT = entity("logistics_hat");

    private static PartialModel block(String path) {
        return PartialModel.of(ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/" + path));
    }

    private static PartialModel entity(String path) {
        return PartialModel.of(ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "entity/" + path));
    }

    public static void init() {
    }
}
