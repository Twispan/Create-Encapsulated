package com.twispan.create_encapsulated.events;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.slugma_burner.renderer.SlugmaBurnerRenderer;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;
import com.twispan.create_encapsulated.registries.ModParticles;
import com.twispan.create_encapsulated.registries.ModPonders;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import com.twispan.create_encapsulated.registries.items.ModItems;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = CreateEncapsulated.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.SLUGMA_BURNER.get(),
                SlugmaBurnerRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SLUGMA_SHINY_FLAMES.get(),
                FlameParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SLUGMA_SHINY_SUPERHEATED_FLAMES.get(),
                FlameParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SLUGMA_SHINY_SMALL_FLAMES.get(),
                FlameParticle.SmallFlameProvider::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Predicate
        event.enqueueWork(() ->
                ItemProperties.register(
                        ModItems.SLUGMA_BURNER.get(),
                        ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID, "shiny"),
                        (stack, level, entity, seed) -> {
                            StoredPokemon stored = stack.get(ModComponents.POKEMON.get());
                            if (stored == null) return 0.0F;
                            return stored.pokemon().getBoolean("Shiny") ? 1.0F : 0.0F;
                        }));

        // Ponder
        event.enqueueWork(() -> PonderIndex.addPlugin(new ModPonders()));
    }
}
