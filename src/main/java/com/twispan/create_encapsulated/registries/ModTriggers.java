package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.advancements.slugma.FeedMaxLevelSlugmaLavaTrigger;
import com.twispan.create_encapsulated.advancements.slugma.FeedSlugmaBlazeCakeTrigger;
import com.twispan.create_encapsulated.advancements.slugma.StoreSlugmaTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTriggers {
    public static DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, CreateEncapsulated.MODID);

    public static DeferredHolder<CriterionTrigger<?>, StoreSlugmaTrigger> STORE_SLUGMA =
            TRIGGERS.register("store_slugma", StoreSlugmaTrigger::new);

    public static DeferredHolder<CriterionTrigger<?>, FeedSlugmaBlazeCakeTrigger> FEED_SLUGMA_BLAZE_CAKE =
            TRIGGERS.register("feed_slugma_blaze_cake", FeedSlugmaBlazeCakeTrigger::new);

    public static DeferredHolder<CriterionTrigger<?>, FeedMaxLevelSlugmaLavaTrigger> FEED_MAX_LEVEL_SLUGMA_LAVA =
            TRIGGERS.register("feed_max_level_slugma_lava", FeedMaxLevelSlugmaLavaTrigger::new);
}
