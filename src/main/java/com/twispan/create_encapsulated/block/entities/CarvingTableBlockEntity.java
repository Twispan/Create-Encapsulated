package com.twispan.create_encapsulated.block.entities;

import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import com.twispan.create_encapsulated.registries.items.ModItems;
import com.twispan.create_encapsulated.screen.CarvingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CarvingTableBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if(!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int TOOL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    public CarvingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CARVING_TABLE.get(), pos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.create_encapsulated.carving_table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new CarvingTableMenu(i, inventory, this);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    public void clearContents() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithFullMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

}
