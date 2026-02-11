package com.twispan.create_encapsulated.item;

import com.twispan.create_encapsulated.fluid.MedicinalBrewFluidType;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import com.twispan.create_encapsulated.fluid.paint.PaintFluidType;
import com.twispan.create_encapsulated.registries.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import com.cobblemon.mod.common.CobblemonItems;

public class EmptyBottleFluidHandler implements IFluidHandlerItem {
    private ItemStack container;
    private static final int CAPACITY = 250; // 250mb = 1/4 bucket

    public EmptyBottleFluidHandler(ItemStack container) {
        this.container = container;
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
        return FluidStack.EMPTY; // Empty bottle has no fluid
    }

    @Override
    public int getTankCapacity(int tank) {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        // Only accept paint fluids
        return stack.getFluidType() instanceof PaintFluidType;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        // This is called when the bottle extracts fluid FROM a container
        if (resource.isEmpty()) {
            return 0;
        }
        int fillAmount = Math.min(CAPACITY, resource.getAmount());

        if (action.execute() && fillAmount == CAPACITY) { // Only fill if you can fill completely
            if ((resource.getFluidType() instanceof PaintFluidType paintFluidType)) {
                // Get the paint color from the fluid type
                PaintColor color = paintFluidType.getPaintColor();

                // Transform the empty bottle into the corresponding paint item
                ItemStack paintItem = switch (color) {
                    case RED -> new ItemStack(ModItems.REDPAINT.get());
                    case BLUE -> new ItemStack(ModItems.BLUEPAINT.get());
                    case GREEN -> new ItemStack(ModItems.GREENPAINT.get());
                    case YELLOW -> new ItemStack(ModItems.YELLOWPAINT.get());
                    case PINK -> new ItemStack(ModItems.PINKPAINT.get());
                    case BLACK -> new ItemStack(ModItems.BLACKPAINT.get());
                    case WHITE -> new ItemStack(ModItems.WHITEPAINT.get());
                };

                // Transform the container
                container = paintItem.copy();
            }

            if ((resource.getFluidType() instanceof MedicinalBrewFluidType)) {
                container = new ItemStack(CobblemonItems.MEDICINAL_BREW);
            }

        }

        return fillAmount;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        return FluidStack.EMPTY; // Cannot drain from empty bottle
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY; // Cannot drain from empty bottle
    }
}