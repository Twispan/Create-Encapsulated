package com.twispan.create_encapsulated.client;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.paint.PaintFluidType;
import com.twispan.create_encapsulated.registries.ModFluids;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public class ModClientSetup {

    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        ResourceLocation LIQUID_SOFT_STILL = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_soft_still");

        registerPaintFluidRendering(event, ModFluids.RED_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.BLUE_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.GREEN_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.YELLOW_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.PINK_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.BLACK_PAINT_TYPE.get(), LIQUID_SOFT_STILL);
        registerPaintFluidRendering(event, ModFluids.WHITE_PAINT_TYPE.get(), LIQUID_SOFT_STILL);

        registerMedicinalBrewRendering(event, ModFluids.MEDICINAL_BREW_TYPE.get(), LIQUID_SOFT_STILL);
    }

    private static void registerPaintFluidRendering(
        RegisterClientExtensionsEvent event,
        PaintFluidType fluidType,
        ResourceLocation still) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return still;
            }

            @Override
            /* ARGB Format: 0xAARRGGBB
            * AA: Alpha || RR: Red || GG: Green || BB: Blue
            * We want paint to be fully opaque since it is a dense liquid.
            */
            public int getTintColor() {
                return 0xFF000000 | fluidType.getPaintColor().getColor();
            }
        }, fluidType);
    }

    private static void registerMedicinalBrewRendering(
            RegisterClientExtensionsEvent event,
            MedicinalBrewFluidType fluidType,
            ResourceLocation still
    ) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() { return still; }

            @Override
            public ResourceLocation getOverlayTexture() { return still; }

            @Override
            public int getTintColor() {
                // Medicinal brew is somewhat transparent.
                return 0xAA2EBCA2;
            }
        }, fluidType);
    }
}