package com.twispan.create_encapsulated.screen.carving_blade;

import com.twispan.create_encapsulated.crafting.CarvingRecipe;
import com.twispan.create_encapsulated.registries.ModMenuTypes;
import com.twispan.create_encapsulated.registries.ModRecipeTypes;
import com.twispan.create_encapsulated.registries.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class CarvingBladeMenu extends AbstractContainerMenu {

    public static final int CONFIRM_BUTTON_ID = 1000; // This is high to not conflict with any recipe ID.
    public static final int CANCEL_BUTTON_ID = 1001;//   ^^

    private final Level level;
    private final BlockPos targetPos;
    private final Block originalBlock;
    private final InteractionHand hand;

    private final List<RecipeHolder<CarvingRecipe>> recipes;
    private int selectedRecipe = -1;
    private final ContainerData data;

    public CarvingBladeMenu(int id, Inventory inv, Level level, BlockPos targetPos, InteractionHand interactionHand) {
        super(ModMenuTypes.CARVING_BLADE.get(), id);
        this.level = level;
        this.targetPos = targetPos;
        this.hand = interactionHand;
        this.originalBlock = level.getBlockState(targetPos).getBlock();
        this.recipes = buildRecipeList();

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> selectedRecipe;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    selectedRecipe = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        this.addDataSlots(data);
    }

    // Client-side constructor, used for MenuType factory
    public CarvingBladeMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, inv.player.level(), buf.readBlockPos(), InteractionHand.MAIN_HAND);
    }

    private List<RecipeHolder<CarvingRecipe>> buildRecipeList() {
        ItemStack asItem = new ItemStack(this.originalBlock.asItem());
        if (asItem.isEmpty()) {
            return List.of();
        }
        return this.level.getRecipeManager().getRecipesFor(
                ModRecipeTypes.CARVING.get(), new SingleRecipeInput(asItem), this.level)
                .stream()
                .filter(holder -> Block.byItem(
                        holder.value().getResultItem(this.level.registryAccess()).getItem()) != Blocks.AIR)
                .toList();
    }

    public List<RecipeHolder<CarvingRecipe>> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe;
    }

    private boolean isValidRecipe(int id) {
        return id >= 0 && id < this.recipes.size();
    }

    /**
     * @param player - Player Entity
     * @param id - Button ID
     * @return
     *
     * This simply controls the action to take when the player clicks a button in the screen.
     * The most obvious out of these are the confirm and cancel buttons.
     * If the player clicks the check button, it confirms the carve action.
     * Otherwise, it closes the menu.
     * <br/>
     * However, it also controls which recipe the player chooses and promptly selects it so the screen can
     * update accordingly.
     */
    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == CONFIRM_BUTTON_ID) {
            this.confirmCarve(player);
            return true;
        }

        if (id == CANCEL_BUTTON_ID) {
            player.closeContainer();
            return true;
        }

        if (this.isValidRecipe(id)) {
            this.selectedRecipe = id;
            this.data.set(0, id);
            return true;
        }

        return false;
    }

    /**
     * @param player
     *
     * This confirms the carve action, it must confirm that the player is actually in game and if the recipe
     * the player selected is valid.
     * <br/>
     * It will then check if the block is there at all times, if the block is moved, destroyed, or otherwise altered
     * it will close the menu. The same happens if the player isn't actually holding the Carving Blade.
     * <br/>
     * It then tries to collect the block the player selected and place it.
     * If everything is successful, the carve action should finish with the result block placed in the world.
     */
    private void confirmCarve (Player player) {
        if (this.level.isClientSide) return;
        if (!this.isValidRecipe(this.selectedRecipe)) return;

        BlockState currentState = this.level.getBlockState(this.targetPos);
        if (currentState.getBlock() != this.originalBlock) {
            player.closeContainer();
            return;
        }

        ItemStack tool = player.getItemInHand(this.hand);
        if (!tool.is(ModItems.CARVING_BLADE.get())) {
            player.closeContainer();
            return;
        }

        RecipeHolder<CarvingRecipe> recipe = this.recipes.get(this.selectedRecipe);
        ItemStack result = recipe.value().getResultItem(this.level.registryAccess());
        Block resultBlock = Block.byItem(result.getItem());

        if (resultBlock == Blocks.AIR) {
            player.closeContainer();
            return;
        }

        BlockState newState = buildTargetState(resultBlock, currentState, player);

        if (!placeCarvedBlock(this.targetPos, newState, player)) {
            // If a multi-block result can't occupy the space, it exits without damaging the tool.
            player.closeContainer();
            return;
        }

        this.level.playSound(null, this.targetPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS);

        if (!player.getAbilities().instabuild) {
            tool.hurtAndBreak(1, player, player.getEquipmentSlotForItem(tool));
        }

        player.closeContainer();
    }

    // ==================================================================================
    // || State builders, controls the properties that carry over to the build process ||
    // ==================================================================================

    /**
     * @param resultBlock - Result Block
     * @param oldState - Old Block State
     * @param player - Player Entity
     * @return
     *
     * This function controls the properties the block will have when carved. Most importantly, rotation.
     * This of course just returns the state of the block, and will not actually try to build the block, rather,
     * it tells placeCarvedBlock() the properties the block will have.
     * <br/>
     * Controlled properties:
     * - 16-way rotation
     * - Axis
     * - Attached Face (this tells the build function that the block should be attached to an adjacent block, if possible)
     */
    private BlockState buildTargetState(Block resultBlock, BlockState oldState, Player player) {
        BlockState newState = resultBlock.defaultBlockState();

        for (Property<?> property : oldState.getProperties()) {
            if (newState.hasProperty(property)) {
                newState = copyProperty(oldState, newState, property);
            }
        }

        if (newState.hasProperty(BlockStateProperties.ATTACH_FACE)
            && !oldState.hasProperty(BlockStateProperties.ATTACH_FACE)) {
                newState = applyAttachFace(newState, player);
        } else if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
            && !oldState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                Direction wallSupport = findHorizontalSupport(this.targetPos);
                Direction facing = wallSupport != null ? wallSupport : player.getDirection();
                newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }

        if (newState.hasProperty(BlockStateProperties.AXIS)
            && !oldState.hasProperty(BlockStateProperties.AXIS)) {
                newState = newState.setValue(BlockStateProperties.AXIS, playerLookAxis(player));
        }

        if (newState.hasProperty(BlockStateProperties.ROTATION_16)
            && !oldState.hasProperty(BlockStateProperties.ROTATION_16)) {
                newState = newState.setValue(BlockStateProperties.ROTATION_16, rotationTowardsPlayer(this.targetPos, player));
        }

        return newState;
    }

    /**
     * @param state - Block State
     * @param player - Player Entity
     * @return
     *
     * Given the result has the property 'AttachFace' it will try to find any sort of possible support block
     * and give back the complete blockState that contains the property and where it should be placed.
     */
    private BlockState applyAttachFace(BlockState state, Player player) {
        Direction support = findSupportDirection(this.targetPos);

        if (support == null) {
            return state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection());
        }

        return switch (support) {
            case DOWN -> state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection());
            case UP -> state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.CEILING)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection());
            default -> state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.WALL)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, support.getOpposite());
        };
    }

    /**
     * @param pos - Block Position
     * @return
     *
     * Helper function for function applyAttachFace(), this function is responsible for finding a suitable direction
     * the result can attach to given it has the AttachFace property based on a list of priorities.
     */
    private Direction findSupportDirection(BlockPos pos) {
        Direction[] priority = {Direction.DOWN, Direction.NORTH, Direction.EAST,
                Direction.SOUTH, Direction.WEST, Direction.UP};

        for (Direction dir : priority) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = this.level.getBlockState(neighborPos);
            if (neighborState.isFaceSturdy(this.level, neighborPos, dir.getOpposite())) {
                return dir;
            }
        }
        return null;
    }

    /**
     * @param pos
     * @return
     *
     * Same as above, but only controls 4 directions instead of 6. This is used for signs, for example.
     */
    private Direction findHorizontalSupport(BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos neigborPos = pos.relative(dir);
            BlockState neighborState = this.level.getBlockState(neigborPos);
            if (neighborState.isFaceSturdy(this.level, neigborPos, dir.getOpposite())) {
                return dir;
            }
        }
        return null;
    }

    private Direction.Axis playerLookAxis(Player player) {
        Vec3 look = player.getLookAngle();
        return Direction.getNearest(look.x, look.y, look.z).getAxis();
    }

    /**
     * @param pos - Block Position
     * @param player - Player Entity
     * @return
     *
     * Returns the nearest direction available computed from the player's position towards the block's center,
     * this is mainly used for signs or any sort of block that has 16-way rotation.
     */
    private int rotationTowardsPlayer(BlockPos pos, Player player) {
        double dx = player.getX() - (pos.getX() + 0.5D);
        double dz = player.getZ() - (pos.getZ() + 0.5D);
        float yaw = (float) (Mth.atan2(dz, dx) * (180D / Math.PI)) - 90.0F;
        return Mth.floor((yaw * 16.0F / 360.F) + 0.5D) & 15;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }

    // ================================================================================================================
    // || Placement functions, handles the building process, as well as handling things such as multi-block results, ||
    // || correct placement of blocks that shouldn't be airborne, and block connections, such as fences.             ||
    // ================================================================================================================

    private record Placement(BlockPos pos, BlockState state) {}

    /**
     * @param pos - Block Pos
     * @param state - Block State
     * @param player - Player Entity
     * @return
     *
     * a
     */
    private boolean placeCarvedBlock(BlockPos pos, BlockState state, Player player) {
        List<Placement> placements = new ArrayList<>();

        if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
            BlockPos abovePos = pos.above();
            if (!this.level.getBlockState(abovePos).canBeReplaced()) {
                return false;
            }
            placements.add(new Placement(pos, state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF,
                    DoubleBlockHalf.LOWER)));
            placements.add(new Placement(abovePos, state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF,
                    DoubleBlockHalf.UPPER)));
        } else if (state.hasProperty(BlockStateProperties.BED_PART)) {
            // Honestly, we don't even have beds as a possible result per default, but this is more of an
            // 'if it happens, we're prepared for it' situation.
            Direction facing = state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)
                    ? state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                    : player.getDirection();
            BlockPos headPos = pos.relative(facing);
            if (!this.level.getBlockState(headPos).canBeReplaced()) {
                return false;
            }
            BlockState foot = state.setValue(BlockStateProperties.BED_PART, BedPart.FOOT)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                    .setValue(BlockStateProperties.OCCUPIED, false);
            BlockState head = state.setValue(BlockStateProperties.BED_PART, BedPart.HEAD)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                    .setValue(BlockStateProperties.OCCUPIED, false);
            placements.add(new Placement(pos, foot));
            placements.add(new Placement(headPos, head));
        } else {
            placements.add(new Placement(pos, state));
        }

        for (Placement placement : placements) {
            this.level.setBlock(placement.pos(), placement.state(), 3);
        }

        for (Placement placement : placements) {
            BlockPos placedPos = placement.pos();
            BlockState placedState = updateConnections(placedPos, player);

            if (!placedState.canSurvive(this.level, placedPos)) {
                this.level.destroyBlock(placedPos, true, player);
            }
        }
        return true;
    }

    private BlockState updateConnections(BlockPos pos, Player player) {
        BlockState state = this.level.getBlockState(pos);
        BlockState updated = state;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            updated = updated.updateShape(dir, this.level.getBlockState(neighborPos), this.level, pos, neighborPos);
        }

        if (updated.isAir() && !state.isAir()) {
            this.level.destroyBlock(pos, true, player);
            return this.level.getBlockState(pos);
        }

        if (updated != state) {
            this.level.setBlock(pos, updated, 3);
        }

        return updated;
    }

    @Override
    public boolean stillValid(Player player) {
        if (!this.level.getBlockState(this.targetPos).is(this.originalBlock)) {
            return false;
        }
        return this.targetPos.closerToCenterThan(player.position(), 8.0);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }
}
