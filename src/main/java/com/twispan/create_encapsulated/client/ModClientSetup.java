package com.twispan.create_encapsulated.client;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.other_medicine.OMedicineFluidType;
import com.twispan.create_encapsulated.fluid.potions.PotionFluidType;
import com.twispan.create_encapsulated.fluid.vitamins.VitaminFluidType;
import com.twispan.create_encapsulated.registries.ModFluids;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

public class ModClientSetup {

    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        ResourceLocation LIQUID_SOFT_STILL = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_soft_still");
        ResourceLocation LIQUID_SOFT_FLOW = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_soft_flow");
        ResourceLocation LIQUID_MEDIUM_STILL = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_medium_still");
        ResourceLocation LIQUID_MEDIUM_FLOW = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_medium_flow");
        ResourceLocation LIQUID_STRONG_STILL = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_strong_still");
        ResourceLocation LIQUID_STRONG_FLOW = ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "block/liquid_strong_flow");

        registerMedicinalBrewRendering(event, ModFluids.MEDICINAL_BREW_TYPE.get(), LIQUID_SOFT_STILL, LIQUID_SOFT_FLOW);

        registerPotionFluidRendering(event, ModFluids.POTION_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerPotionFluidRendering(event, ModFluids.SUPER_POTION_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerPotionFluidRendering(event, ModFluids.HYPER_POTION_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerPotionFluidRendering(event, ModFluids.MAX_POTION_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerPotionFluidRendering(event, ModFluids.FULL_RESTORE_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);

        registerVitaminFluidRendering(event, ModFluids.CARBOS_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.CALCIUM_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.HP_UP_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.IRON_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.PP_UP_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.PP_MAX_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.PROTEIN_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);
        registerVitaminFluidRendering(event, ModFluids.ZINC_TYPE.get(), LIQUID_STRONG_STILL, LIQUID_STRONG_FLOW);

        registerOMedicineFluidRendering(event, ModFluids.ANTIDOTE_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.PARALYZE_HEAL_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.AWAKENING_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.FULL_HEAL_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.BURN_HEAL_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.ICE_HEAL_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.ETHER_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.MAX_ETHER_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.ELIXIR_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
        registerOMedicineFluidRendering(event, ModFluids.MAX_ELIXIR_TYPE.get(), LIQUID_MEDIUM_STILL, LIQUID_MEDIUM_FLOW);
    }

    private static void registerMedicinalBrewRendering(
            RegisterClientExtensionsEvent event,
            MedicinalBrewFluidType fluidType,
            ResourceLocation still,
            ResourceLocation flow
    ) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() { return still; }

            @Override
            public ResourceLocation getOverlayTexture() { return still; }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() { return flow; }

            @Override
            public int getTintColor() {
                // Medicinal brew is somewhat transparent.
                return 0xAA2EBCA2;
            }
        }, fluidType);
    }

    private static void registerPotionFluidRendering(
            RegisterClientExtensionsEvent event,
            PotionFluidType fluidType,
            ResourceLocation still,
            ResourceLocation flow
    ) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() { return still; }

            @Override
            public ResourceLocation getOverlayTexture() { return still; }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() { return flow; }

            @Override
            public int getTintColor() {
                // Potion fluids are somewhat transparent
                return 0xCC000000 | fluidType.getType().getType();
            }
        }, fluidType);
    }

    private static void registerVitaminFluidRendering(
            RegisterClientExtensionsEvent event,
            VitaminFluidType fluidType,
            ResourceLocation still,
            ResourceLocation flow
    ) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() { return still; }

            @Override
            public ResourceLocation getOverlayTexture() { return still; }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() { return flow; }

            @Override
            public int getTintColor() {
                return 0xFF000000 | fluidType.getType().getType();
            }
        }, fluidType);
    }

    private static void registerOMedicineFluidRendering(
            RegisterClientExtensionsEvent event,
            OMedicineFluidType fluidType,
            ResourceLocation still,
            ResourceLocation flow
    ) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public @NotNull ResourceLocation getStillTexture() { return still; }

            @Override
            public ResourceLocation getOverlayTexture() { return still; }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() { return flow; }

            @Override
            public int getTintColor() { return 0xFF000000 | fluidType.getType().getMedicineType(); }
        }, fluidType);
    }
}