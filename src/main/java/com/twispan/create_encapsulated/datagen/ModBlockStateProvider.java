package com.twispan.create_encapsulated.datagen;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateEncapsulated.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.ORIGIN_ALLOY_BLOCK.get(), cubeAll(ModBlocks.ORIGIN_ALLOY_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.MEGA_SHARD_BLOCK.get(), cubeAll(ModBlocks.MEGA_SHARD_BLOCK.get()));

        simpleBlockWithItem(ModBlocks.POLISHED_ORIGIN_BLOCK.get(), cubeAll(ModBlocks.POLISHED_ORIGIN_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.POLISHED_ORIGIN_BRICKS.get(), cubeAll(ModBlocks.POLISHED_ORIGIN_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get(), cubeAll(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get(), cubeAll(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get()));

        stairsBlock(ModBlocks.POLISHED_ORIGIN_STAIRS.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BLOCK.get()));
        stairsBlock(ModBlocks.POLISHED_ORIGIN_BRICK_STAIRS.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BRICKS.get()));
        stairsBlock(ModBlocks.POLISHED_MEGA_SHARD_STAIRS.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get()));
        stairsBlock(ModBlocks.POLISHED_MEGA_SHARD_BRICK_STAIRS.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_STAIRS);
        blockItem(ModBlocks.POLISHED_ORIGIN_BRICK_STAIRS);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_STAIRS);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_BRICK_STAIRS);

        slabBlock(ModBlocks.POLISHED_ORIGIN_SLAB.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BLOCK.get()), blockTexture(ModBlocks.POLISHED_ORIGIN_BLOCK.get()));
        slabBlock(ModBlocks.POLISHED_ORIGIN_BRICK_SLAB.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BRICKS.get()), blockTexture(ModBlocks.POLISHED_ORIGIN_BRICKS.get()));
        slabBlock(ModBlocks.POLISHED_MEGA_SHARD_SLAB.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get()), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get()));
        slabBlock(ModBlocks.POLISHED_MEGA_SHARD_BRICK_SLAB.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get()), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_SLAB);
        blockItem(ModBlocks.POLISHED_ORIGIN_BRICK_SLAB);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_SLAB);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_BRICK_SLAB);

        wallBlock(ModBlocks.POLISHED_ORIGIN_WALL.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BLOCK.get()));
        wallBlock(ModBlocks.POLISHED_ORIGIN_BRICK_WALL.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_BRICKS.get()));
        wallBlock(ModBlocks.POLISHED_MEGA_SHARD_WALL.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BLOCK.get()));
        wallBlock(ModBlocks.POLISHED_MEGA_SHARD_BRICK_WALL.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD_BRICKS.get()));

        axisBlock(ModBlocks.CHISELED_ORIGIN_BLOCK.get(),
                modLoc("block/chiseled_origin_block_side"),
                modLoc("block/chiseled_origin_block_top"));
        blockItem(ModBlocks.CHISELED_ORIGIN_BLOCK);

        axisBlock(ModBlocks.CHISELED_MEGA_SHARD_BLOCK.get(),
                modLoc("block/chiseled_mega_shard_block_side"),
                modLoc("block/chiseled_mega_shard_block_top"));
        blockItem(ModBlocks.CHISELED_MEGA_SHARD_BLOCK);
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(),
                models().getExistingFile(modLoc("block/" + deferredBlock.getId().getPath())));
    }
}
