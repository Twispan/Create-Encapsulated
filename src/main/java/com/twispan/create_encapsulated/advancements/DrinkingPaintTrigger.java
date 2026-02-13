package com.twispan.create_encapsulated.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DrinkingPaintTrigger extends SimpleCriterionTrigger<DrinkingPaintTrigger.TriggerInstance> {

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, PaintColor color) {
        this.trigger(player, instance -> instance.matches(color));
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player,
            Optional<PaintColor> color
    ) implements SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                                .forGetter(TriggerInstance::player),
                        Codec.STRING.xmap(s -> PaintColor.valueOf(PaintColor.class, s), PaintColor::name)
                                .optionalFieldOf("color")
                                .forGetter(TriggerInstance::color)
                ).apply(instance, TriggerInstance::new)
        );

        public boolean matches(PaintColor color) {
            return this.color.isEmpty() || this.color.get() == color;
        }

        @Override
        public @NotNull Optional<ContextAwarePredicate> player() {
            return player;
        }
    }
}