package com.twispan.create_encapsulated.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoodProperties {
    public static final FoodProperties REDPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties BLUEPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties YELLOWPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties GREENPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties PINKPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties BLACKPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties WHITEPAINT = new FoodProperties.Builder().nutrition(0).saturationModifier(0)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1200), 1.0f).effect(new MobEffectInstance(MobEffects.POISON, 500), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
}
