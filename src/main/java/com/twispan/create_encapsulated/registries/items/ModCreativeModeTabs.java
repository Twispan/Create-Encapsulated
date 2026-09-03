package com.twispan.create_encapsulated.registries.items;

import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = CreateEncapsulated.MODID, value = Dist.CLIENT)
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, CreateEncapsulated.MODID);

    public static final Supplier<CreativeModeTab> CREATEENCAPSULATED_ITEMS_TAB = CREATIVE_MODE_TAB.register(
            "createencapsulated_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.POKEBALLBASE.get()))
                    .title(Component.translatable("creativetab.create_encapsulated.createencapsulated_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.POKEBALLBASE);
                        output.accept(ModItems.SPLITREDAPRICORN);
                        output.accept(ModItems.SPLITBLUEAPRICORN);
                        output.accept(ModItems.SPLITYELLOWAPRICORN);
                        output.accept(ModItems.SPLITGRNAPRICORN);
                        output.accept(ModItems.SPLITPNKAPRICORN);
                        output.accept(ModItems.SPLITBLKAPRICORN);
                        output.accept(ModItems.SPLITWHTAPRICORN);
                        output.accept(ModItems.GREATBALLLID);
                        output.accept(ModItems.ULTRABALLLID);
                        output.accept(ModItems.SAFARIBALLLID);
                        output.accept(ModItems.FASTBALLLID);
                        output.accept(ModItems.LEVELBALLLID);
                        output.accept(ModItems.FRIENDBALLLID);
                        output.accept(ModItems.LUREBALLLID);
                        output.accept(ModItems.HEAVYBALLLID);
                        output.accept(ModItems.LOVEBALLLID);
                        output.accept(ModItems.MOONBALLLID);
                        output.accept(ModItems.SPORTBALLLID);
                        output.accept(ModItems.PARKBALLLID);
                        output.accept(ModItems.NETBALLLID);
                        output.accept(ModItems.DIVEBALLLID);
                        output.accept(ModItems.NESTBALLLID);
                        output.accept(ModItems.REPEATBALLLID);
                        output.accept(ModItems.TIMERBALLLID);
                        output.accept(ModItems.LUXURYBALLLID);
                        output.accept(ModItems.DUSKBALLLID);
                        output.accept(ModItems.HEALBALLLID);
                        output.accept(ModItems.QUICKBALLLID);
                        output.accept(ModItems.DREAMBALLLID);
                        output.accept(ModItems.MASTERBALLLID);
                        output.accept(ModItems.BEASTBALLLID);
                        output.accept(ModItems.FEATHERBALLLID);
                        output.accept(ModItems.WINGBALLLID);
                        output.accept(ModItems.JETBALLLID);
                        output.accept(ModItems.ANCIENTHEAVYBALLLID);
                        output.accept(ModItems.LEADENBALLLID);
                        output.accept(ModItems.GIGATONBALLLID);
                        output.accept(ModItems.ANCIENTGREATBALLLID);
                        output.accept(ModItems.ANCIENTULTRABALLLID);
                        output.accept(ModItems.ORIGINBALLLID);
                        output.accept(ModItems.ORIGINBALLBASE);
                        output.accept(ModItems.ORIGINALLOY);
                    }).build()
    );

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}