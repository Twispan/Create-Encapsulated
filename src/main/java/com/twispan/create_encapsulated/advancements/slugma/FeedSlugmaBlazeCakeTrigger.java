package com.twispan.create_encapsulated.advancements.slugma;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class FeedSlugmaBlazeCakeTrigger extends SimpleCriterionTrigger<FeedSlugmaBlazeCakeTrigger.TriggerInstance> {
    @Override public Codec<FeedSlugmaBlazeCakeTrigger.TriggerInstance> codec() {
        return FeedSlugmaBlazeCakeTrigger.TriggerInstance.CODEC;
    }

    public void trigger (ServerPlayer player) {
        this.trigger(player, inst -> true);
    }

    public record TriggerInstance (Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<FeedSlugmaBlazeCakeTrigger.TriggerInstance> CODEC =
                RecordCodecBuilder.create(i -> i.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(
                        FeedSlugmaBlazeCakeTrigger.TriggerInstance::player)
        ).apply(i, FeedSlugmaBlazeCakeTrigger.TriggerInstance::new));
    }
}
