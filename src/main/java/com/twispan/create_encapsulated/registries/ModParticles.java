package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, CreateEncapsulated.MODID);

    public static final Supplier<SimpleParticleType> SLUGMA_SHINY_FLAMES =
            PARTICLES.register("slugma_shiny_flames", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SLUGMA_SHINY_SUPERHEATED_FLAMES =
            PARTICLES.register("slugma_shiny_superheated_flames", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> SLUGMA_SHINY_SMALL_FLAMES =
            PARTICLES.register("slugma_shiny_small_flames", () -> new SimpleParticleType(true));

}
