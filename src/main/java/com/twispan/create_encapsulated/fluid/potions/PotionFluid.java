package com.twispan.create_encapsulated.fluid.potions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class PotionFluid extends Fluid {
    private final DeferredHolder<FluidType, PotionFluidType> type;

    protected PotionFluid(DeferredHolder<FluidType, PotionFluidType> type) { this.type = type; }

    @Override
    public @NotNull FluidType getFluidType() {
        return type.get();
    }

    @Override
    public @NotNull Item getBucket() {
        return Items.AIR; // No bucket
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    public int getAmount(FluidState state) {
        return 8;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return Blocks.AIR.defaultBlockState(); // No world block
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        // No states needed for non-flowing fluid
    }

    @Override
    public @NotNull Vec3 getFlow(BlockGetter blockGetter, BlockPos pos, FluidState fluidState) {
        return Vec3.ZERO; // No flow since this fluid doesn't exist in world
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 0;
    }

    @Override
    public float getHeight(FluidState fluidState, BlockGetter blockGetter, BlockPos pos) {
        return 0.0F; // No height since this fluid doesn't exist in world
    }

    @Override
    public float getOwnHeight(FluidState fluidState) {
        return 0.0F; // No height
    }

    @Override
    public VoxelShape getShape(FluidState fluidState, BlockGetter blockGetter, BlockPos pos) {
        return Shapes.empty(); // No collision shape
    }

    public static class Source extends PotionFluid {
        public Source(DeferredHolder<FluidType, PotionFluidType> type) { super(type); }
    }
}
