package com.twispan.create_encapsulated.item;

import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateEncapsulated.MODID);

    public static final DeferredItem<Item> POKEBALLBASE = ITEMS.register("poke_ball_base",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITREDAPRICORN = ITEMS.register("split_red_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITBLUEAPRICORN = ITEMS.register("split_blue_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITYELLOWAPRICORN = ITEMS.register("split_yellow_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITGRNAPRICORN = ITEMS.register("split_green_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITPNKAPRICORN = ITEMS.register("split_pink_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITBLKAPRICORN = ITEMS.register("split_black_apricorn",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPLITWHTAPRICORN = ITEMS.register("split_white_apricorn",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> REDPAINT = ITEMS.register("red_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.REDPAINT)));
    public static final DeferredItem<Item> BLUEPAINT = ITEMS.register("blue_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BLUEPAINT)));
    public static final DeferredItem<Item> YELLOWPAINT = ITEMS.register("yellow_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.YELLOWPAINT)));
    public static final DeferredItem<Item> GREENPAINT = ITEMS.register("green_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.GREENPAINT)));
    public static final DeferredItem<Item> PINKPAINT = ITEMS.register("pink_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.PINKPAINT)));
    public static final DeferredItem<Item> BLACKPAINT = ITEMS.register("black_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BLACKPAINT)));
    public static final DeferredItem<Item> WHITEPAINT = ITEMS.register("white_paint",
            () -> new Item(new Item.Properties().food(ModFoodProperties.WHITEPAINT)));

    public static final DeferredItem<Item> SPICYAPRIJUICE = ITEMS.register("spicy_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SPICYAPRIJUICE)));
    public static final DeferredItem<Item> DRYAPRIJUICE = ITEMS.register("dry_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.DRYAPRIJUICE)));
    public static final DeferredItem<Item> SOURAPRIJUICE = ITEMS.register("sour_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SOURAPRIJUICE)));
    public static final DeferredItem<Item> BITTERAPRIJUICE = ITEMS.register("bitter_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BITTERAPRIJUICE)));
    public static final DeferredItem<Item> SWEETAPRIJUICE = ITEMS.register("sweet_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SWEETAPRIJUICE)));
    public static final DeferredItem<Item> UMAMIAPRIJUICE = ITEMS.register("umami_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.UMAMIAPRIJUICE)));
    public static final DeferredItem<Item> SALTYAPRIJUICE = ITEMS.register("salty_aprijuice",
            () -> new Item(new Item.Properties().food(ModFoodProperties.SALTYAPRIJUICE)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
