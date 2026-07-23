package com.twispan.create_encapsulated.screen.carving_table;

import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import com.twispan.create_encapsulated.block.entities.CarvingTableBlockEntity;
import com.twispan.create_encapsulated.crafting.CarvingRecipe;
import com.twispan.create_encapsulated.registries.ModMenuTypes;
import com.twispan.create_encapsulated.registries.ModRecipeTypes;
import com.twispan.create_encapsulated.registries.items.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    private final ContainerData data;

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
        this.addSlot(new Slot(resultContainer, 0, 143, 33) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return CarvingTableMenu.this.isValidRecipeIndex(
                        CarvingTableMenu.this.selectedRecipeIndex
                );
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());

                blockEntity.itemStackHandler.extractItem(0, 1, false);

                if (!level.isClientSide) {
                    level.playSound(null, blockEntity.getBlockPos(),
                            SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                if (!blockEntity.itemStackHandler.getStackInSlot(0).isEmpty()
                        && isValidRecipeIndex(selectedRecipeIndex)) {
                    setupResultSlot(selectedRecipeIndex);
                } else {
                    selectedRecipeIndex = -1;
                    resultContainer.setItem(0, ItemStack.EMPTY);
                }

                super.onTake(player, stack);
            }
        });

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> selectedRecipeIndex;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    selectedRecipeIndex = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }

        this.addDataSlots(this.data);

        this.slotsChanged(new SimpleContainer());
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
                if (!this.recipes.isEmpty() && this.selectedRecipeIndex == -1) {
                }
            } else {
                this.recipes = List.of();
                this.selectedRecipeIndex = -1;
                this.resultContainer.setItem(0, ItemStack.EMPTY);
            }
        }

        if (!hasTool && hadRecipes) {
            this.recipes = List.of();
            this.selectedRecipeIndex = -1;
            this.resultContainer.setItem(0, ItemStack.EMPTY);
        }
    }

    @Override
    public void clicked(int slotId, int button, @NotNull ClickType clickType, @NotNull Player player) {
        if (slotId == 2 && button == 0 && clickType == ClickType.PICKUP) {
            Slot outputSlot = this.slots.get(2);
            ItemStack carried = this.getCarried();
            ItemStack result = outputSlot.getItem();

            if (!result.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex)) {
                if (carried.isEmpty() || (ItemStack.isSameItemSameComponents(carried, result) &&
                        carried.getCount() + result.getCount() <= carried.getMaxStackSize())) {

                    if (carried.isEmpty()) {
                        this.setCarried(result.copy());
                    } else {
                        carried.grow(result.getCount());
                    }

                    blockEntity.itemStackHandler.extractItem(0, 1, false);

                    if (!level.isClientSide) {
                        level.playSound(null, blockEntity.getBlockPos(),
                                SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }

                    if (!blockEntity.itemStackHandler.getStackInSlot(0).isEmpty() &&
                            this.isValidRecipeIndex(this.selectedRecipeIndex)) {
                        this.setupResultSlot(this.selectedRecipeIndex);
                    } else {
                        this.selectedRecipeIndex = -1;
                        outputSlot.set(ItemStack.EMPTY);
                    }

                    return;
                }
            }
        }

        super.clicked(slotId, button, clickType, player);
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

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipeIndex = id;
            this.data.set(0, id);
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
                this.slots.get(2).set(result);
            } else {
                this.resultContainer.setItem(0, ItemStack.EMPTY);
            }
        } else {
            this.resultContainer.setItem(0, ItemStack.EMPTY);
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
                && blockEntity.itemStackHandler.getStackInSlot(1).is(ModItems.CARVING_BLADE.get())
                && !this.recipes.isEmpty();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack empty = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) return empty;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index == 2) {
            if (!this.isValidRecipeIndex(this.selectedRecipeIndex)) return empty;

            ItemStack input = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack tool = blockEntity.itemStackHandler.getStackInSlot(1);

            if (input.isEmpty() || !tool.is(ModItems.CARVING_BLADE.get())) return empty;

            int craftCount = input.getCount();
            RecipeHolder<CarvingRecipe> recipe = this.recipes.get(this.selectedRecipeIndex);
            ItemStack result = recipe.value().getResultItem(this.level.registryAccess());

            for (int i = 0; i < craftCount; i++) {
                ItemStack crafted = result.copy();

                if (!player.getInventory().add(crafted)) {
                    player.drop(crafted, false);
                    break;
                }

                blockEntity.itemStackHandler.extractItem(0, 1, false);
            }

            if (!level.isClientSide) {
                level.playSound(null, blockEntity.getBlockPos(),
                        SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            this.slotsChanged(new SimpleContainer());
            return copy;
        }

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