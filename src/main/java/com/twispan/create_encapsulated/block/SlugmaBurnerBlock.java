package com.twispan.create_encapsulated.block;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.registries.ModComponents;
import com.twispan.create_encapsulated.registries.blocks.ModBlockEntities;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class SlugmaBurnerBlock extends BlazeBurnerBlock {
    public SlugmaBurnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        assert context.getPlayer() != null;
        return getStateForPlacement(context.getPlayer());
    }

    private BlockState getStateForPlacement(Player player) {
        BlockState defaultState = defaultBlockState();
        HeatLevel initialHeat = HeatLevel.SMOULDERING;
        assert player != null;
        return defaultState.setValue(HEAT_LEVEL, initialHeat)
                .setValue(FACING, player.getDirection().getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return super.newBlockEntity(pos, state);
    }

    @Override
    public BlockEntityType<? extends BlazeBurnerBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SLUGMA_BURNER.get();
    }

    public static LootTable.@NotNull Builder buildLootTable() {
        LootItemCondition.Builder survivesExplosion = ExplosionCondition.survivesExplosion();
        SlugmaBurnerBlock block = (SlugmaBurnerBlock) ModBlocks.SLUGMA_BURNER.get();
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder poolBuilder = LootPool.lootPool();
        for (HeatLevel level : HeatLevel.values()) {
            ItemLike drop = level == HeatLevel.NONE ? AllItems.EMPTY_BLAZE_BURNER.get() : ModBlocks.SLUGMA_BURNER.get();
            poolBuilder.add(LootItem.lootTableItem(drop)
                    .when(survivesExplosion)
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(HEAT_LEVEL, level)))
                    .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                            .include(ModComponents.POKEMON.get())));
        }

        builder.withPool(poolBuilder.setRolls(ConstantValue.exactly(1)));
        return builder;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return ModBlocks.SLUGMA_BURNER.toStack();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide || !(placer instanceof ServerPlayer player)) return;
        if (level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity blockEntity && blockEntity.getOwnerUUID() == null) {
            blockEntity.setOwner(player.getUUID(), player.getGameProfile().getName());
            blockEntity.notifyUpdate();
        }
    }
}
