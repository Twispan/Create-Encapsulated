package com.twispan.create_encapsulated.item;

import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateEncapsulated.MODID);

public static final Supplier<CreativeModeTab> POKEBALLCOMPONENTS_ITEMS_TAB = CREATIVE_MODE_TAB.register("pokeballcomponents_items_tab",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SPLITREDAPRICORN.get()))
                .title(Component.translatable("creativetab.create_encapsulated.pokeballcomponents_items"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModItems.POKEBALLBASE);
                    output.accept(ModItems.SPLITREDAPRICORN);
                    output.accept(ModItems.SPLITBLUEAPRICORN);
                    output.accept(ModItems.SPLITYELLOWAPRICORN);
                    output.accept(ModItems.SPLITGRNAPRICORN);
                    output.accept(ModItems.SPLITPNKAPRICORN);
                    output.accept(ModItems.SPLITBLKAPRICORN);
                    output.accept(ModItems.SPLITWHTAPRICORN);
                    output.accept(ModItems.REDPAINT);
                    output.accept(ModItems.BLUEPAINT);
                    output.accept(ModItems.YELLOWPAINT);
                    output.accept(ModItems.GREENPAINT);
                    output.accept(ModItems.PINKPAINT);
                    output.accept(ModItems.BLACKPAINT);
                    output.accept(ModItems.WHITEPAINT);
                }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
