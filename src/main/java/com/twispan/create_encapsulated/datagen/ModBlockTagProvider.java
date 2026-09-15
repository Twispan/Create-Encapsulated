package com.twispan.create_encapsulated.datagen;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateEncapsulated.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.WALLS)
                .add(ModBlocks.POLISHED_ORIGIN_WALL.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_WALL.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ORIGIN_ALLOY_BLOCK.get())
                .add(ModBlocks.MEGA_SHARD_BLOCK.get())
                .add(ModBlocks.POLISHED_ORIGIN_BLOCK.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICKS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get())
                .add(ModBlocks.POLISHED_ORIGIN_STAIRS.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_STAIRS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_STAIRS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_STAIRS.get())
                .add(ModBlocks.POLISHED_ORIGIN_SLAB.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_SLAB.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_SLAB.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_SLAB.get())
                .add(ModBlocks.POLISHED_ORIGIN_WALL.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_WALL.get())
                .add(ModBlocks.CHISELED_ORIGIN_BLOCK.get())
                .add(ModBlocks.CHISELED_MEGA_SHARD_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ORIGIN_ALLOY_BLOCK.get())
                .add(ModBlocks.MEGA_SHARD_BLOCK.get())
                .add(ModBlocks.POLISHED_ORIGIN_BLOCK.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICKS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get())
                .add(ModBlocks.CHISELED_ORIGIN_BLOCK.get())
                .add(ModBlocks.CHISELED_MEGA_SHARD_BLOCK.get())
                .add(ModBlocks.POLISHED_ORIGIN_STAIRS.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_STAIRS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_STAIRS.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_STAIRS.get())
                .add(ModBlocks.POLISHED_ORIGIN_SLAB.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_SLAB.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_SLAB.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_SLAB.get())
                .add(ModBlocks.POLISHED_ORIGIN_WALL.get())
                .add(ModBlocks.POLISHED_ORIGIN_BRICK_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_WALL.get())
                .add(ModBlocks.POLISHED_MEGA_SHARD_BRICK_WALL.get());
    }
}
