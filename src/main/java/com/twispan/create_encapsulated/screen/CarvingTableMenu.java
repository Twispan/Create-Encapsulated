package com.twispan.create_encapsulated.screen;

import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import com.twispan.create_encapsulated.block.entities.CarvingTableBlockEntity;
import com.twispan.create_encapsulated.crafting.CarvingRecipe;
import com.twispan.create_encapsulated.registries.ModMenuTypes;
import com.twispan.create_encapsulated.registries.ModRecipeTypes;
import com.twispan.create_encapsulated.registries.items.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarvingTableMenu extends AbstractContainerMenu {

    private final CarvingTableBlockEntity blockEntity;
    private final Level level;

    private final ResultContainer resultContainer = new ResultContainer();
    private List<RecipeHolder<CarvingRecipe>> recipes = List.of();
    private ItemStack lastInput = ItemStack.EMPTY;
    private int selectedRecipeIndex = -1;

    public CarvingTableMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public CarvingTableMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(ModMenuTypes.CARVING_TABLE.get(), pContainerId);
        this.blockEntity = ((CarvingTableBlockEntity) entity);
        this.level = inv.player.level();

        checkContainerSize(inv, 3);

        // Input slot (material)
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 0, 20, 17) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return !stack.is(ModItems.CARVING_BLADE);
            }

            @Override
            public void setChanged() {
                super.setChanged();
                CarvingTableMenu.this.slotsChanged(new SimpleContainer());
            }
        });

        // Tool slot
        this.addSlot(new SlotItemHandler(blockEntity.itemStackHandler, 1, 20, 51) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ModItems.CARVING_BLADE);
            }

            @Override
            public void setChanged() {
                super.setChanged();
                CarvingTableMenu.this.slotsChanged(new SimpleContainer());
            }
        });

        // Output slot (result)
        this.addSlot(new Slot(resultContainer, 2, 143, 33) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());

                blockEntity.itemStackHandler.extractItem(0, 1, false);

                ItemStack tool = blockEntity.itemStackHandler.getStackInSlot(1);
                if (!tool.isEmpty() && tool.isDamageableItem()) {
                    tool.hurtAndBreak(1, player, player.getEquipmentSlotForItem(tool));
                    if (tool.isEmpty()) {
                        blockEntity.itemStackHandler.setStackInSlot(1, ItemStack.EMPTY);
                    }
                }

                CarvingTableMenu.this.slotsChanged(new SimpleContainer());

                player.playSound(SoundEvents.AXE_STRIP, 1.0F, 1.0F);

                super.onTake(player, stack);
            }

        });

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        ItemStack input = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack tool = blockEntity.itemStackHandler.getStackInSlot(1);

        boolean inputChanged = !ItemStack.isSameItemSameComponents(input, this.lastInput);
        boolean hasTool = tool.is(ModItems.CARVING_BLADE.get());
        boolean hadRecipes = !this.recipes.isEmpty();

        if (inputChanged) {
            this.lastInput = input.copy();
        }

        if (inputChanged || (hasTool && !hadRecipes)) {
            if (hasTool && !input.isEmpty()) {
                this.setupRecipeList(input);
            } else {
                this.recipes = List.of();
                this.selectedRecipeIndex = -1;
                this.resultContainer.setItem(0, ItemStack.EMPTY);
            }
        }

        if (!tool.is(ModItems.CARVING_BLADE.get()) && !this.recipes.isEmpty()) {
            this.recipes = List.of();
            this.selectedRecipeIndex = -1;
            this.resultContainer.setItem(0, ItemStack.EMPTY);
        }
    }

    private void setupRecipeList(ItemStack input) {
        this.recipes = List.of();
        this.selectedRecipeIndex = -1;
        this.resultContainer.setItem(0, ItemStack.EMPTY);

        if (!input.isEmpty()) {
            this.recipes = this.level.getRecipeManager()
                    .getRecipesFor(ModRecipeTypes.CARVING.get(),
                            new SingleRecipeInput(input), this.level);
        }
    }

    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipeIndex = id;
            this.setupResultSlot(id);
        }
        return true;
    }

    private boolean isValidRecipeIndex(int index) {
        return index >= 0 && index < this.recipes.size();
    }

    private void setupResultSlot(int selectedRecipe) {
        if (this.isValidRecipeIndex(selectedRecipe)) {
            RecipeHolder<CarvingRecipe> recipe = this.recipes.get(selectedRecipe);

            ItemStack result = recipe.value().assemble(
                    new SingleRecipeInput(blockEntity.itemStackHandler.getStackInSlot(0)),
                    this.level.registryAccess()
            );

            if (result.isItemEnabled(this.level.enabledFeatures())) {
                this.resultContainer.setRecipeUsed(recipe);
                this.resultContainer.setItem(0, result);
            }
        }
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex;
    }

    public List<RecipeHolder<CarvingRecipe>> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return !blockEntity.itemStackHandler.getStackInSlot(0).isEmpty()
                && blockEntity.itemStackHandler.getStackInSlot(1).is(ModItems.CARVING_BLADE.get()) // Check tool slot
                && !this.recipes.isEmpty();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) return empty;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        // Player inventory -> machine
        if (index >= 3) {
            if (stack.is(ModItems.CARVING_BLADE.get())) {
                if (!moveItemStackTo(stack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }
        // Machine -> player
        else if (!moveItemStackTo(stack, 3, this.slots.size(), false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.CARVING_TABLE.get());
    }
}
