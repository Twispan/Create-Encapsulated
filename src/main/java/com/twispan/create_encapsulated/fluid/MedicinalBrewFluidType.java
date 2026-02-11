package com.twispan.create_encapsulated.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

public class MedicinalBrewFluidType extends FluidType {
    public MedicinalBrewFluidType(Properties props) {
        super(props);
    }

    @Override
    public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canDrownIn(LivingEntity entity) {
        return true;
    }

    @Override
    public boolean canExtinguish(Entity entity) {
        return true;
    }

    @Override
    public boolean canHydrate(Entity entity) {
        return false;
    }
}
