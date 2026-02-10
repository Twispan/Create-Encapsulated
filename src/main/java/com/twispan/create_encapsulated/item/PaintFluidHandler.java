package com.twispan.create_encapsulated.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class PaintFluidHandler implements IFluidHandlerItem {
    private ItemStack container;
    private final FluidStack fluidStack;
    private static final int CAPACITY = 250; // 250mb = 1/4 bucket

    public PaintFluidHandler(ItemStack container, FluidStack fluidStack) {
        this.container = container;
        this.fluidStack = fluidStack;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return tank == 0 ? fluidStack.copy() : FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank) {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return false; // Cannot fill this item
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return 0; // Cannot fill this item
    }

    // Required to add the correct version of this method for it to work
    @Override
    public @NotNull FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty() || !FluidStack.isSameFluidSameComponents(resource, fluidStack)) {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    // Method that is cool and awesome and actually does what I'm asking for
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if (maxDrain <= 0 || fluidStack.isEmpty()) {
            return FluidStack.EMPTY; // Still returns nothing if the container is empty
        }

        // Values to check for what should be drained, and to store the result right after execution
        int drained = Math.min(maxDrain, fluidStack.getAmount());
        FluidStack result = fluidStack.copy();
        result.setAmount(drained);

        if (action.execute()) {
            fluidStack.shrink(drained);

            // Transform the paint item into an empty bottle
            if (container.getCount() == 1) {
                container = new ItemStack(Items.GLASS_BOTTLE);
            } else {
                container.shrink(1);
            }
        }

        return result;
    }
}