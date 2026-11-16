package com.twispan.create_encapsulated.block.arm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SlugmaBurnerArmType extends ArmInteractionPointType {
    public SlugmaBurnerArmType() {
    }

    @Override
    public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
        return state.is(ModBlocks.SLUGMA_BURNER.get());
    }

    @Override
    public @Nullable ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
        return new SlugmaBurnerPoint(this, level, pos, state);
    }

    public static class SlugmaBurnerPoint extends ArmInteractionPoint {
        public SlugmaBurnerPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        public ItemStack insert(ArmBlockEntity armBlockEntity, ItemStack stack, boolean simulate) {
            ItemStack input = stack.copy();
            InteractionResultHolder<ItemStack> resultHolder =
                    BlazeBurnerBlock.tryInsert(cachedState, level, pos, input, false, false, simulate);
            ItemStack remainder = resultHolder.getObject();
            if (input.isEmpty()) return remainder;
            if (!remainder.isEmpty()) input.grow(remainder.getCount());
            return input;
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
