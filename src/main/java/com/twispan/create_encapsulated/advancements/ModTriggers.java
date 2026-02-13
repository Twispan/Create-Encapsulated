package com.twispan.create_encapsulated.advancements;

import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, CreateEncapsulated.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, DrinkingPaintTrigger> DRANK_PAINT =
            TRIGGERS.register("drank_paint", DrinkingPaintTrigger::new);
}
