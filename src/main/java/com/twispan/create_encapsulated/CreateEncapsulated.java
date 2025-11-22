package com.twispan.create_encapsulated;

import com.twispan.create_encapsulated.item.ModCreativeModeTabs;
import com.twispan.create_encapsulated.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateEncapsulated.MODID)
public class CreateEncapsulated {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_encapsulated";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateEncapsulated(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.POKEBALLBASE);
            event.accept(ModItems.SPLITREDAPRICORN);
            event.accept(ModItems.SPLITBLUEAPRICORN);
            event.accept(ModItems.SPLITYELLOWAPRICORN);
            event.accept(ModItems.SPLITGRNAPRICORN);
            event.accept(ModItems.SPLITPNKAPRICORN);
            event.accept(ModItems.SPLITBLKAPRICORN);
            event.accept(ModItems.SPLITWHTAPRICORN);
            event.accept(ModItems.GREATBALLLID);
            event.accept(ModItems.ULTRABALLLID);
            event.accept(ModItems.SAFARIBALLLID);
            event.accept(ModItems.FASTBALLLID);
            event.accept(ModItems.LEVELBALLLID);
            event.accept(ModItems.FRIENDBALLLID);
            event.accept(ModItems.LUREBALLLID);
            event.accept(ModItems.HEAVYBALLLID);
            event.accept(ModItems.LOVEBALLLID);
            event.accept(ModItems.MOONBALLLID);
            event.accept(ModItems.SPORTBALLLID);
            event.accept(ModItems.PARKBALLLID);
            event.accept(ModItems.NETBALLLID);
            event.accept(ModItems.DIVEBALLLID);
            event.accept(ModItems.NESTBALLLID);
            event.accept(ModItems.REPEATBALLLID);
            event.accept(ModItems.TIMERBALLLID);
            event.accept(ModItems.LUXURYBALLLID);
            event.accept(ModItems.DUSKBALLLID);
            event.accept(ModItems.HEALBALLLID);
            event.accept(ModItems.QUICKBALLLID);
            event.accept(ModItems.DREAMBALLLID);
            event.accept(ModItems.MASTERBALLLID);
            event.accept(ModItems.BEASTBALLLID);
            event.accept(ModItems.REDPAINT);
            event.accept(ModItems.BLUEPAINT);
            event.accept(ModItems.YELLOWPAINT);
            event.accept(ModItems.GREENPAINT);
            event.accept(ModItems.PINKPAINT);
            event.accept(ModItems.BLACKPAINT);
            event.accept(ModItems.WHITEPAINT);
        }

    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
