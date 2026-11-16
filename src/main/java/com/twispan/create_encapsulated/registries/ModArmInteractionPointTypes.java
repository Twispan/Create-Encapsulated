package com.twispan.create_encapsulated.registries;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.twispan.create_encapsulated.block.slugma_burner.arm.SlugmaBurnerArmType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModArmInteractionPointTypes {

    public static final DeferredRegister<ArmInteractionPointType> ARM_POINTS =
            DeferredRegister.create(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, "create_encapsulated");

    public static final DeferredHolder<ArmInteractionPointType, SlugmaBurnerArmType> SLUGMA_BURNER =
            ARM_POINTS.register("slugma_burner", SlugmaBurnerArmType::new);
}
