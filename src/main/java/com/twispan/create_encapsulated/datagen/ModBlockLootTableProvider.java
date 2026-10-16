package com.twispan.create_encapsulated.datagen;

import com.twispan.create_encapsulated.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ORIGIN_ALLOY_BLOCK.get());

        dropSelf(ModBlocks.POLISHED_ORIGIN_ALLOY.get());
        dropSelf(ModBlocks.ORIGIN_ALLOY_BRICKS.get());

        dropSelf(ModBlocks.POLISHED_ORIGIN_ALLOY_STAIRS.get());
        dropSelf(ModBlocks.ORIGIN_ALLOY_BRICK_STAIRS.get());

        add(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB.get()));
        add(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB.get()));

        dropSelf(ModBlocks.POLISHED_ORIGIN_ALLOY_WALL.get());
        dropSelf(ModBlocks.ORIGIN_ALLOY_BRICK_WALL.get());

        dropSelf(ModBlocks.CHISELED_ORIGIN_ALLOY.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(entry -> (Block) entry.get())
                .toList();
    }
}
