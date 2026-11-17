package com.twispan.create_encapsulated.block.dynamo_podium;

import com.cobblemon.mod.common.api.item.Berry;
import com.cobblemon.mod.common.item.berry.BerryItem;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.twispan.create_encapsulated.block.dynamo_podium.entity.DynamoPodiumBlockEntity;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DynamoPodiumBlock extends KineticBlock implements IBE<DynamoPodiumBlockEntity> {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public DynamoPodiumBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<DynamoPodiumBlockEntity> getBlockEntityClass() {
        return DynamoPodiumBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DynamoPodiumBlockEntity> getBlockEntityType() {
        return ModBlockEntities.DYNAMO_PODIUM.get();
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state,
                                                       @NotNull Level level, @NotNull BlockPos pos,
                                                       @NotNull Player player, @NotNull InteractionHand hand,
                                                       @NotNull BlockHitResult hitResult) {
        if (!(stack.getItem() instanceof BerryItem))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (level.isClientSide)
            return ItemInteractionResult.SUCCESS;

        InteractionResultHolder<ItemStack> res = tryUseItem(level, pos, stack, false);
        if (res.getResult() == InteractionResult.FAIL)
            return ItemInteractionResult.FAIL;

        if (!player.isCreative()) stack.shrink(1);
        return ItemInteractionResult.SUCCESS;
    }

     public static InteractionResultHolder<ItemStack> tryUseItem(Level level, BlockPos pos,
                                                         ItemStack stack, boolean simulate) {
         if (!(level.getBlockEntity(pos) instanceof DynamoPodiumBlockEntity podium))
             return InteractionResultHolder.fail(ItemStack.EMPTY);
         if (!podium.tryFeedBerry(stack, simulate))
             return InteractionResultHolder.fail(ItemStack.EMPTY);

         ItemStack remainder = stack.copy();
         remainder.shrink(1);
         return InteractionResultHolder.success(remainder);
    }
}
