package com.twispan.create_encapsulated.events;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.renderer.SlugmaBurnerRenderer;
import com.twispan.create_encapsulated.registries.ModParticles;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
}
