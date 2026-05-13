package com.twispan.create_encapsulated.block;

import com.mojang.serialization.MapCodec;
import com.twispan.create_encapsulated.block.entities.CarvingTableBlockEntity;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CarvingTable  extends BaseEntityBlock {
    public static final MapCodec<CarvingTable> CODEC = simpleCodec(CarvingTable::new);

    public CarvingTable(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return codec();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CarvingTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CarvingTableBlockEntity carvingTableBlockEntity) {
                carvingTableBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                                       Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof CarvingTableBlockEntity carvingTableBlockEntity) {
                (pPlayer).openMenu(new SimpleMenuProvider(carvingTableBlockEntity, Component.literal("Carving Table")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }
}

