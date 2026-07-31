package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluid;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.other_medicine.OMedicineFluid;
import com.twispan.create_encapsulated.fluid.other_medicine.OMedicineFluidType;
import com.twispan.create_encapsulated.fluid.other_medicine.OMedicines;
import com.twispan.create_encapsulated.fluid.potions.PotionFluid;
import com.twispan.create_encapsulated.fluid.potions.PotionFluidType;
import com.twispan.create_encapsulated.fluid.potions.PotionType;
import com.twispan.create_encapsulated.fluid.vitamins.VitaminFluid;
import com.twispan.create_encapsulated.fluid.vitamins.VitaminFluidType;
import com.twispan.create_encapsulated.fluid.vitamins.VitaminType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, CreateEncapsulated.MODID);

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, CreateEncapsulated.MODID);

    // Medicinal Brew
    public static final DeferredHolder<FluidType, MedicinalBrewFluidType> MEDICINAL_BREW_TYPE =
            FLUID_TYPES.register("medicinal_brew", () -> new MedicinalBrewFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300)
            ));

    public static final DeferredHolder<Fluid, MedicinalBrewFluid.Source> MEDICINAL_BREW =
            FLUIDS.register("medicinal_brew", () -> new MedicinalBrewFluid.Source(MEDICINAL_BREW_TYPE));

    // Potion
    public static final DeferredHolder<FluidType, PotionFluidType> POTION_TYPE =
            FLUID_TYPES.register("potion", () -> new PotionFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PotionType.POTION
            ));

    public static final DeferredHolder<Fluid, PotionFluid> POTION =
            FLUIDS.register("potion", () -> new PotionFluid.Source(POTION_TYPE));

    // Super Potion
    public static final DeferredHolder<FluidType, PotionFluidType> SUPER_POTION_TYPE =
            FLUID_TYPES.register("super_potion", () -> new PotionFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PotionType.SUPER
            ));

    public static final DeferredHolder<Fluid, PotionFluid> SUPER_POTION =
            FLUIDS.register("super_potion", () -> new PotionFluid.Source(SUPER_POTION_TYPE));

    // Hyper Potion
    public static final DeferredHolder<FluidType, PotionFluidType> HYPER_POTION_TYPE =
            FLUID_TYPES.register("hyper_potion", () -> new PotionFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PotionType.HYPER
            ));

    public static final DeferredHolder<Fluid, PotionFluid> HYPER_POTION =
            FLUIDS.register("hyper_potion", () -> new PotionFluid.Source(HYPER_POTION_TYPE));

    // Max Potion
    public static final DeferredHolder<FluidType, PotionFluidType> MAX_POTION_TYPE =
            FLUID_TYPES.register("max_potion", () -> new PotionFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PotionType.MAX
            ));

    public static final DeferredHolder<Fluid, PotionFluid> MAX_POTION =
            FLUIDS.register("max_potion", () -> new PotionFluid.Source(MAX_POTION_TYPE));

    // Full Restore
    public static final DeferredHolder<FluidType, PotionFluidType> FULL_RESTORE_TYPE =
            FLUID_TYPES.register("full_restore", () -> new PotionFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PotionType.FULL_RESTORE
            ));

    public static final DeferredHolder<Fluid, PotionFluid> FULL_RESTORE =
            FLUIDS.register("full_restore", () -> new PotionFluid.Source(FULL_RESTORE_TYPE));

    // Calcium
    public static final DeferredHolder<FluidType, VitaminFluidType> CALCIUM_TYPE =
            FLUID_TYPES.register("calcium", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.CALCIUM
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> CALCIUM =
            FLUIDS.register("calcium", () -> new VitaminFluid.Source(CALCIUM_TYPE));

    // Carbos
    public static final DeferredHolder<FluidType, VitaminFluidType> CARBOS_TYPE =
                FLUID_TYPES.register("carbos", () -> new VitaminFluidType(
                        FluidType.Properties.create()
                                .density(2000)
                                .viscosity(6000)
                                .temperature(300),
                        VitaminType.CARBOS
                ));

    public static final DeferredHolder<Fluid, VitaminFluid> CARBOS =
            FLUIDS.register("carbos", () -> new VitaminFluid.Source(CARBOS_TYPE));

    // HP UP
    public static final DeferredHolder<FluidType, VitaminFluidType> HP_UP_TYPE =
                FLUID_TYPES.register("hp_up", () -> new VitaminFluidType(
                        FluidType.Properties.create()
                                .density(2000)
                                .viscosity(6000)
                                .temperature(300),
                        VitaminType.HP_UP
                ));

    public static final DeferredHolder<Fluid, VitaminFluid> HP_UP =
            FLUIDS.register("hp_up", () -> new VitaminFluid.Source(HP_UP_TYPE));

    // Iron
    public static final DeferredHolder<FluidType, VitaminFluidType> IRON_TYPE =
            FLUID_TYPES.register("iron", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.IRON
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> IRON =
            FLUIDS.register("iron", () -> new VitaminFluid.Source(IRON_TYPE));

    // PP UP
    public static final DeferredHolder<FluidType, VitaminFluidType> PP_UP_TYPE =
            FLUID_TYPES.register("pp_up", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.PP_UP
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> PP_UP =
            FLUIDS.register("pp_up", () -> new VitaminFluid.Source(PP_UP_TYPE));

    // PP MAX
    public static final DeferredHolder<FluidType, VitaminFluidType> PP_MAX_TYPE =
            FLUID_TYPES.register("pp_max", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.PP_MAX
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> PP_MAX =
            FLUIDS.register("pp_max", () -> new VitaminFluid.Source(PP_MAX_TYPE));

    // Protein
    public static final DeferredHolder<FluidType, VitaminFluidType> PROTEIN_TYPE =
            FLUID_TYPES.register("protein", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.PROTEIN
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> PROTEIN =
            FLUIDS.register("protein", () -> new VitaminFluid.Source(PROTEIN_TYPE));

    // Zinc
    public static final DeferredHolder<FluidType, VitaminFluidType> ZINC_TYPE =
            FLUID_TYPES.register("zinc", () -> new VitaminFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    VitaminType.ZINC
            ));

    public static final DeferredHolder<Fluid, VitaminFluid> ZINC =
            FLUIDS.register("zinc", () -> new VitaminFluid.Source(ZINC_TYPE));

    // Antidote
    public static final DeferredHolder<FluidType, OMedicineFluidType> ANTIDOTE_TYPE =
            FLUID_TYPES.register("antidote", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.ANTIDOTE
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> ANTIDOTE =
            FLUIDS.register("antidote", () -> new OMedicineFluid.Source(ANTIDOTE_TYPE));

    // Para heal
    public static final DeferredHolder<FluidType, OMedicineFluidType> PARALYZE_HEAL_TYPE =
            FLUID_TYPES.register("paralyze_heal", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.PARALYZE_HEAL
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> PARALYZE_HEAL =
            FLUIDS.register("paralyze_heal", () -> new OMedicineFluid.Source(PARALYZE_HEAL_TYPE));

    // Awakening
    public static final DeferredHolder<FluidType, OMedicineFluidType> AWAKENING_TYPE =
            FLUID_TYPES.register("awakening", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.AWAKENING
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> AWAKENING =
            FLUIDS.register("awakening", () -> new OMedicineFluid.Source(AWAKENING_TYPE));

    // Full heal
    public static final DeferredHolder<FluidType, OMedicineFluidType> FULL_HEAL_TYPE =
            FLUID_TYPES.register("full_heal", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.FULL_HEAL
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> FULL_HEAL =
            FLUIDS.register("full_heal", () -> new OMedicineFluid.Source(FULL_HEAL_TYPE));

    // Burn heal
    public static final DeferredHolder<FluidType, OMedicineFluidType> BURN_HEAL_TYPE =
            FLUID_TYPES.register("burn_heal", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.BURN_HEAL
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> BURN_HEAL =
            FLUIDS.register("burn_heal", () -> new OMedicineFluid.Source(BURN_HEAL_TYPE));

    // Ice heal
    public static final DeferredHolder<FluidType, OMedicineFluidType> ICE_HEAL_TYPE =
            FLUID_TYPES.register("ice_heal", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.ICE_HEAL
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> ICE_HEAL =
            FLUIDS.register("ice_heal", () -> new OMedicineFluid.Source(ICE_HEAL_TYPE));

    // Ether
    public static final DeferredHolder<FluidType, OMedicineFluidType> ETHER_TYPE =
            FLUID_TYPES.register("ether", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.ETHER
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> ETHER =
            FLUIDS.register("ether", () -> new OMedicineFluid.Source(ETHER_TYPE));

    // Max ether
    public static final DeferredHolder<FluidType, OMedicineFluidType> MAX_ETHER_TYPE =
            FLUID_TYPES.register("max_ether", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.MAX_ETHER
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> MAX_ETHER =
            FLUIDS.register("max_ether", () -> new OMedicineFluid.Source(MAX_ETHER_TYPE));

    // Elixir
    public static final DeferredHolder<FluidType, OMedicineFluidType> ELIXIR_TYPE =
            FLUID_TYPES.register("elixir", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.ELIXIR
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> ELIXIR =
            FLUIDS.register("elixir", () -> new OMedicineFluid.Source(ELIXIR_TYPE));

    // Max elixir
    public static final DeferredHolder<FluidType, OMedicineFluidType> MAX_ELIXIR_TYPE =
            FLUID_TYPES.register("max_elixir", () -> new OMedicineFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    OMedicines.MAX_ELIXIR
            ));

    public static final DeferredHolder<Fluid, OMedicineFluid> MAX_ELIXIR =
            FLUIDS.register("max_elixir", () -> new OMedicineFluid.Source(MAX_ELIXIR_TYPE));
}