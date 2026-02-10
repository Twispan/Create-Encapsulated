package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.fluid.PaintColor;
import com.twispan.create_encapsulated.fluid.PaintFluid;
import com.twispan.create_encapsulated.item.Paint;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.twispan.create_encapsulated.registries.ModFluids.*;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateEncapsulated.MODID);

    public static final DeferredItem<Item> MODLOGO = ITEMS.register("create_encapsulated_logo",
            () -> new Item(new Item.Properties()));

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
    public static final DeferredItem<Item> BEASTBALLLID = ITEMS.register("beast_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHERISHBALLLID = ITEMS.register("cherish_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FEATHERBALLLID = ITEMS.register("ancient_feather_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WINGBALLLID = ITEMS.register("ancient_wing_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JETBALLLID = ITEMS.register("ancient_jet_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ANCIENTHEAVYBALLLID = ITEMS.register("ancient_heavy_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEADENBALLLID = ITEMS.register("ancient_leaden_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GIGATONBALLLID = ITEMS.register("ancient_gigaton_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ANCIENTGREATBALLLID = ITEMS.register("ancient_great_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ANCIENTULTRABALLLID = ITEMS.register("ancient_ultra_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORIGINBALLLID = ITEMS.register("origin_ball_lid",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORIGINBALLBASE = ITEMS.register("origin_ball_base",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ORIGINALLOY = ITEMS.register("origin_alloy",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> REDPAINT = ITEMS.register("red_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), RED_PAINT));
    public static final DeferredItem<Item> BLUEPAINT = ITEMS.register("blue_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), BLUE_PAINT));
    public static final DeferredItem<Item> YELLOWPAINT = ITEMS.register("yellow_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), YELLOW_PAINT));
    public static final DeferredItem<Item> GREENPAINT = ITEMS.register("green_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), GREEN_PAINT));
    public static final DeferredItem<Item> PINKPAINT = ITEMS.register("pink_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), PINK_PAINT));
    public static final DeferredItem<Item> BLACKPAINT = ITEMS.register("black_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), BLACK_PAINT));
    public static final DeferredItem<Item> WHITEPAINT = ITEMS.register("white_paint",
            () -> new Paint(new Item.Properties().stacksTo(16), WHITE_PAINT));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
