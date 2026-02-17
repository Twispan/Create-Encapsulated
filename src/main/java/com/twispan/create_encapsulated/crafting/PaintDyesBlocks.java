package com.twispan.create_encapsulated.crafting;

import com.twispan.create_encapsulated.item.Paint;
import com.twispan.create_encapsulated.util.PaintColorMapper;
import com.twispan.create_encapsulated.util.PaintColorMapperModded;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

// This class doesn't hold every single dyeable item, only those that can have NBT in them, such as shulker boxes.
// This is so the items inside don't go missing whenever dyeing it.

public class PaintDyesBlocks extends ShapelessRecipe {

    private final ItemStack result;

    public PaintDyesBlocks(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.result = result;
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        ItemStack targetStack = ItemStack.EMPTY;
        boolean hasPaint = false;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof Paint) {
                hasPaint = true;
            } else if (PaintColorMapper.isRecolorable(stack) || PaintColorMapperModded.isRecolorable(stack)) {
                if (!targetStack.isEmpty()) return false;
                targetStack = stack;
            } else {
                return false;
            }
        }
        return !targetStack.isEmpty() && hasPaint;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider provider) {
        ItemStack targetStack = ItemStack.EMPTY;
        ItemStack paintStack = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof Paint) paintStack = stack;
            else if (PaintColorMapper.isRecolorable(stack) || PaintColorMapperModded.isRecolorable(stack))
                targetStack = stack;
        }

        if (targetStack.isEmpty() || paintStack.isEmpty()) return ItemStack.EMPTY;

        Paint paintItem = (Paint) paintStack.getItem();
        if (paintItem.getColor() == null) return ItemStack.EMPTY;

        ItemStack finalTargetStack = targetStack;
        return PaintColorMapper.recolor(targetStack, paintItem.getColor())
                .or(() -> PaintColorMapperModded.recolor(finalTargetStack, paintItem.getColor()))
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).getItem() instanceof Paint) {
                remaining.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remaining;
    }


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINT_BLOCK_DYE.get();
    }

    public ItemStack getResult() {
        return result;
    }
}
