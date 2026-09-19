package com.twispan.create_encapsulated;

import com.cobblemon.mod.common.CobblemonItems;
import com.twispan.create_encapsulated.client.ModClientSetup;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.other_medicine.OMedicineFluidType;
import com.twispan.create_encapsulated.fluid.potions.PotionFluidType;
import com.twispan.create_encapsulated.fluid.vitamins.VitaminFluidType;
import com.twispan.create_encapsulated.registries.items.ModCreativeModeTabs;
import com.twispan.create_encapsulated.util.FluidMapper;
import com.twispan.create_encapsulated.util.FluidItemHandler;
import com.twispan.create_encapsulated.registries.ModFluids;
import com.twispan.create_encapsulated.registries.items.ModItems;
import com.itsfirestorm.world_of_color.api.BottleFillRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;
import java.util.function.Supplier;

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
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register capabilities
        modEventBus.addListener(this::registerCapabilities);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register creative tab
        ModCreativeModeTabs.register(modEventBus);

        // Register fluids
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);

        // Register items
        ModItems.ITEMS.register(modEventBus);

        // Register client-side events only on the client
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ModClientSetup::onRegisterClientExtensions);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        NeoForgeMod.enableMilkFluid();

        registerPotionBottleFills();
        registerVitaminBottleFills();
        registerOtherMedicineBottleFills();

        BottleFillRegistry.register(
                stack -> stack.getFluidType() instanceof MedicinalBrewFluidType,
                stack -> new ItemStack(CobblemonItems.MEDICINAL_BREW),
                () -> List.of(new FluidStack(ModFluids.MEDICINAL_BREW.get(), 1))
        );
        BottleFillRegistry.register(
                stack -> stack.getFluid() == NeoForgeMod.MILK.get(),
                stack -> new ItemStack(CobblemonItems.MOOMOO_MILK),
                () -> List.of(new FluidStack(NeoForgeMod.MILK.get(), 1))
        );
    }

    private void registerPotionBottleFills() {
        List<Supplier<? extends Fluid>> potions = List.of(
                ModFluids.POTION,
                ModFluids.SUPER_POTION,
                ModFluids.HYPER_POTION,
                ModFluids.MAX_POTION,
                ModFluids.FULL_RESTORE
        );

        for (var fluid : potions) {
            BottleFillRegistry.register(
                    stack -> stack.getFluid() == fluid.get(),
                    stack -> FluidMapper.getPotionItem(
                            (PotionFluidType) stack.getFluidType()
                    ),
                    () -> List.of(new FluidStack(fluid.get(), 1))
            );
        }
    }

    private void registerVitaminBottleFills() {
        List<Supplier<? extends Fluid>> vitamins = List.of(
                ModFluids.CALCIUM,
                ModFluids.CARBOS,
                ModFluids.HP_UP,
                ModFluids.IRON,
                ModFluids.PP_UP,
                ModFluids.PP_MAX,
                ModFluids.PROTEIN,
                ModFluids.ZINC
        );

        for (var fluid : vitamins) {
            BottleFillRegistry.register(
                    stack -> stack.getFluid() == fluid.get(),
                    stack -> FluidMapper.getVitaminItem(
                            (VitaminFluidType) stack.getFluidType()
                    ),
                    () -> List.of(new FluidStack(fluid.get(), 1))
            );
        }
    }

    private void registerOtherMedicineBottleFills() {
        List<Supplier<? extends Fluid>> omedicines = List.of(
                ModFluids.ANTIDOTE,
                ModFluids.PARALYZE_HEAL,
                ModFluids.AWAKENING,
                ModFluids.FULL_HEAL,
                ModFluids.BURN_HEAL,
                ModFluids.ICE_HEAL,
                ModFluids.ETHER,
                ModFluids.MAX_ETHER,
                ModFluids.ELIXIR,
                ModFluids.MAX_ELIXIR
        );

        for (var fluid : omedicines) {
            BottleFillRegistry.register(
                    stack -> stack.getFluid() == fluid.get(),
                    stack -> FluidMapper.getOMedicineItem(
                            (OMedicineFluidType) stack.getFluidType()
                    ),
                    () -> List.of(new FluidStack(fluid.get(), 1))
            );
        }
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
            event.accept(ModItems.FEATHERBALLLID);
            event.accept(ModItems.WINGBALLLID);
            event.accept(ModItems.JETBALLLID);
            event.accept(ModItems.ANCIENTHEAVYBALLLID);
            event.accept(ModItems.LEADENBALLLID);
            event.accept(ModItems.GIGATONBALLLID);
            event.accept(ModItems.ANCIENTGREATBALLLID);
            event.accept(ModItems.ANCIENTULTRABALLLID);
            event.accept(ModItems.ORIGINBALLLID);
            event.accept(ModItems.ORIGINBALLBASE);
            event.accept(ModItems.ORIGINALLOY);
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register fluid handler capability for medicinal brew
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> {
                    FluidStack fluidStack = new FluidStack(ModFluids.MEDICINAL_BREW.get(), 250);
                    return new FluidItemHandler(stack, fluidStack);
                },
                CobblemonItems.MEDICINAL_BREW
        );

        // Register fluid handler capability for potions
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.POTION.get(), 250)),
                CobblemonItems.POTION
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.SUPER_POTION.get(), 250)),
                CobblemonItems.SUPER_POTION
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.HYPER_POTION.get(), 250)),
                CobblemonItems.HYPER_POTION
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.MAX_POTION.get(), 250)),
                CobblemonItems.MAX_POTION
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.FULL_RESTORE.get(), 250)),
                CobblemonItems.FULL_RESTORE
        );

        // Register fluid handler capability for vitamins
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.CARBOS.get(), 250)),
                CobblemonItems.CARBOS
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.CALCIUM.get(), 250)),
                CobblemonItems.CALCIUM
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.HP_UP.get(), 250)),
                CobblemonItems.HP_UP
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.PROTEIN.get(), 250)),
                CobblemonItems.PROTEIN
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.PP_UP.get(), 250)),
                CobblemonItems.PP_UP
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.PP_MAX.get(), 250)),
                CobblemonItems.PP_MAX
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.IRON.get(), 250)),
                CobblemonItems.IRON
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.ZINC.get(), 250)),
                CobblemonItems.ZINC
        );

        // Register fluid handling capability for other medicines
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.ANTIDOTE.get(), 250)),
                CobblemonItems.ANTIDOTE
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.PARALYZE_HEAL.get(), 250)),
                CobblemonItems.PARALYZE_HEAL
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.AWAKENING.get(), 250)),
                CobblemonItems.AWAKENING
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.FULL_HEAL.get(), 250)),
                CobblemonItems.FULL_HEAL
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.BURN_HEAL.get(), 250)),
                CobblemonItems.BURN_HEAL
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.ICE_HEAL.get(), 250)),
                CobblemonItems.ICE_HEAL
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.ETHER.get(), 250)),
                CobblemonItems.ETHER
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.MAX_ETHER.get(), 250)),
                CobblemonItems.MAX_ETHER
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.ELIXIR.get(), 250)),
                CobblemonItems.ELIXIR
        );

        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(ModFluids.MAX_ELIXIR.get(), 250)),
                CobblemonItems.MAX_ELIXIR
        );

        // Milk (for moomoo milk)
        event.registerItem(
                Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidItemHandler(stack,
                        new FluidStack(NeoForgeMod.MILK.get(), 250)),
                CobblemonItems.MOOMOO_MILK
        );
    }

    @SubscribeEvent
    private void onServerStartup(ServerStartingEvent event) {}
}
