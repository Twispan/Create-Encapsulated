package com.twispan.create_encapsulated.registries;

import com.simibubi.create.api.behaviour.interaction.ConductorBlockInteractionBehavior;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.twispan.create_encapsulated.block.movement_behaviour.SlugmaBurnerMovementBehaviour;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;

public class ModCreateBehaviours {
    public static void register() {
        MovementBehaviour.REGISTRY.register(
                ModBlocks.SLUGMA_BURNER.get(),
                new SlugmaBurnerMovementBehaviour()
        );

        MovingInteractionBehaviour.REGISTRY.register(
                ModBlocks.SLUGMA_BURNER.get(),
                new ConductorBlockInteractionBehavior.BlazeBurner()
        );

        BoilerHeater.REGISTRY.register(ModBlocks.SLUGMA_BURNER.get(), ((level, pos, state) -> {
                BlazeBurnerBlock.HeatLevel heat = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);
                return switch (heat) {
                    case NONE -> BoilerHeater.NO_HEAT;
                    case SMOULDERING -> BoilerHeater.PASSIVE_HEAT;
                    case FADING, KINDLED -> 1f;
                    case SEETHING -> 2f;
                };
        }));
    }
}
