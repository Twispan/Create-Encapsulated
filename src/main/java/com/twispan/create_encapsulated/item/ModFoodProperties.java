package com.twispan.create_encapsulated.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoodProperties {
    public static final FoodProperties SPICYAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties DRYAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties SOURAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 4), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties BITTERAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties SWEETAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties UMAMIAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodProperties SALTYAPRIJUICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200), 1.0f).usingConvertsTo(Items.GLASS_BOTTLE).build();

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
