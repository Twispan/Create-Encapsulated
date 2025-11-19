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

    public static final DeferredItem<Item> GREATBALLLID = ITEMS.register("great_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ULTRABALLLID = ITEMS.register("ultra_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SAFARIBALLLID = ITEMS.register("safari_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FASTBALLLID = ITEMS.register("fast_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEVELBALLLID = ITEMS.register("level_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FRIENDBALLLID = ITEMS.register("friend_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUREBALLLID = ITEMS.register("lure_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HEAVYBALLLID = ITEMS.register("heavy_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LOVEBALLLID = ITEMS.register("love_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOONBALLLID = ITEMS.register("moon_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPORTBALLLID = ITEMS.register("sport_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PARKBALLLID = ITEMS.register("park_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NETBALLLID = ITEMS.register("net_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DIVEBALLLID = ITEMS.register("dive_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NESTBALLLID = ITEMS.register("nest_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> REPEATBALLLID = ITEMS.register("repeat_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TIMERBALLLID = ITEMS.register("timer_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUXURYBALLLID = ITEMS.register("luxury_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DUSKBALLLID = ITEMS.register("dusk_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HEALBALLLID = ITEMS.register("heal_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUICKBALLLID = ITEMS.register("quick_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DREAMBALLLID = ITEMS.register("dream_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MASTERBALLLID = ITEMS.register("master_ball_lid",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> REDPAINT = ITEMS.register("red_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> BLUEPAINT = ITEMS.register("blue_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> YELLOWPAINT = ITEMS.register("yellow_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> GREENPAINT = ITEMS.register("green_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> PINKPAINT = ITEMS.register("pink_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> BLACKPAINT = ITEMS.register("black_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> WHITEPAINT = ITEMS.register("white_paint",
            () -> new Paint(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> SPICYAPRIJUICE = ITEMS.register("spicy_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "spicy"));
    public static final DeferredItem<Item> DRYAPRIJUICE = ITEMS.register("dry_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "dry"));
    public static final DeferredItem<Item> SOURAPRIJUICE = ITEMS.register("sour_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "sour"));
    public static final DeferredItem<Item> BITTERAPRIJUICE = ITEMS.register("bitter_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "bitter"));
    public static final DeferredItem<Item> SWEETAPRIJUICE = ITEMS.register("sweet_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "sweet"));
    public static final DeferredItem<Item> UMAMIAPRIJUICE = ITEMS.register("umami_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "umami"));
    public static final DeferredItem<Item> SALTYAPRIJUICE = ITEMS.register("salty_aprijuice",
            () -> new Aprijuice(new Item.Properties().stacksTo(16), "salty"));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
