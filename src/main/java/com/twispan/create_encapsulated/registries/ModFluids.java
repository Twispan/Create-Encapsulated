package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluid;
import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import com.twispan.create_encapsulated.fluid.paint.PaintFluid;
import com.twispan.create_encapsulated.fluid.paint.PaintFluidType;
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

    // Red Paint
    public static final DeferredHolder<FluidType, PaintFluidType> RED_PAINT_TYPE =
            FLUID_TYPES.register("red_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.RED
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> RED_PAINT =
            FLUIDS.register("red_paint", () -> new PaintFluid.Source(RED_PAINT_TYPE));

    // Blue Paint
    public static final DeferredHolder<FluidType, PaintFluidType> BLUE_PAINT_TYPE =
            FLUID_TYPES.register("blue_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.BLUE
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> BLUE_PAINT =
            FLUIDS.register("blue_paint", () -> new PaintFluid.Source(BLUE_PAINT_TYPE));

    // Green Paint
    public static final DeferredHolder<FluidType, PaintFluidType> GREEN_PAINT_TYPE =
            FLUID_TYPES.register("green_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.GREEN
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> GREEN_PAINT =
            FLUIDS.register("green_paint", () -> new PaintFluid.Source(GREEN_PAINT_TYPE));

    // Yellow Paint
    public static final DeferredHolder<FluidType, PaintFluidType> YELLOW_PAINT_TYPE =
            FLUID_TYPES.register("yellow_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.YELLOW
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> YELLOW_PAINT =
            FLUIDS.register("yellow_paint", () -> new PaintFluid.Source(YELLOW_PAINT_TYPE));

    // Pink Paint
    public static final DeferredHolder<FluidType, PaintFluidType> PINK_PAINT_TYPE =
            FLUID_TYPES.register("pink_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.PINK
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> PINK_PAINT =
            FLUIDS.register("pink_paint", () -> new PaintFluid.Source(PINK_PAINT_TYPE));

    // Black Paint
    public static final DeferredHolder<FluidType, PaintFluidType> BLACK_PAINT_TYPE =
            FLUID_TYPES.register("black_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.BLACK
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> BLACK_PAINT =
            FLUIDS.register("black_paint", () -> new PaintFluid.Source(BLACK_PAINT_TYPE));

    // White Paint
    public static final DeferredHolder<FluidType, PaintFluidType> WHITE_PAINT_TYPE =
            FLUID_TYPES.register("white_paint", () -> new PaintFluidType(
                    FluidType.Properties.create()
                            .density(2000)
                            .viscosity(6000)
                            .temperature(300),
                    PaintColor.WHITE
            ));

    public static final DeferredHolder<Fluid, PaintFluid.Source> WHITE_PAINT =
            FLUIDS.register("white_paint", () -> new PaintFluid.Source(WHITE_PAINT_TYPE));

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
}