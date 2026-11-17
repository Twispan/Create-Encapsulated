package com.twispan.create_encapsulated.block.dynamo_podium.arm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.twispan.create_encapsulated.block.dynamo_podium.DynamoPodiumBlock;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DynamoPodiumArmType extends ArmInteractionPointType {
    public DynamoPodiumArmType() {}

    @Override
    public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
        return state.is(ModBlocks.DYNAMO_PODIUM.get());
    }

    @Override
    public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
        return new DynamoPodiumPoint(this, level, pos, state);
    }

    public static class DynamoPodiumPoint extends ArmInteractionPoint {
        public DynamoPodiumPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
            InteractionResultHolder<ItemStack> res =
                    DynamoPodiumBlock.tryUseItem(level, pos, stack, simulate);
            if (res.getResult() == InteractionResult.FAIL) return stack;
            return res.getObject();
        }

        @Override
        public ItemStack extract(ArmBlockEntity armBlockEntity, int slot, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotCount(ArmBlockEntity armBlockEntity) {
            return 1;
        }
    }
}
