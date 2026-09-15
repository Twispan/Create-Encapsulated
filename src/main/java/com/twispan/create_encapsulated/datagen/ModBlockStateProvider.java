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

        simpleBlockWithItem(ModBlocks.POLISHED_ORIGIN_ALLOY.get(), cubeAll(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        simpleBlockWithItem(ModBlocks.ORIGIN_ALLOY_BRICKS.get(), cubeAll(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.POLISHED_MEGA_SHARD.get(), cubeAll(ModBlocks.POLISHED_MEGA_SHARD.get()));
        simpleBlockWithItem(ModBlocks.MEGA_SHARD_BRICKS.get(), cubeAll(ModBlocks.MEGA_SHARD_BRICKS.get()));

        stairsBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_STAIRS.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        stairsBlock(ModBlocks.ORIGIN_ALLOY_BRICK_STAIRS.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));
        stairsBlock(ModBlocks.POLISHED_MEGA_SHARD_STAIRS.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD.get()));
        stairsBlock(ModBlocks.MEGA_SHARD_BRICK_STAIRS.get(), blockTexture(ModBlocks.MEGA_SHARD_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_ALLOY_STAIRS);
        blockItem(ModBlocks.ORIGIN_ALLOY_BRICK_STAIRS);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_STAIRS);
        blockItem(ModBlocks.MEGA_SHARD_BRICK_STAIRS);

        slabBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        slabBlock(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));
        slabBlock(ModBlocks.POLISHED_MEGA_SHARD_SLAB.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD.get()), blockTexture(ModBlocks.POLISHED_MEGA_SHARD.get()));
        slabBlock(ModBlocks.MEGA_SHARD_BRICK_SLAB.get(), blockTexture(ModBlocks.MEGA_SHARD_BRICKS.get()), blockTexture(ModBlocks.MEGA_SHARD_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB);
        blockItem(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB);
        blockItem(ModBlocks.POLISHED_MEGA_SHARD_SLAB);
        blockItem(ModBlocks.MEGA_SHARD_BRICK_SLAB);

        wallBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_WALL.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        wallBlock(ModBlocks.ORIGIN_ALLOY_BRICK_WALL.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));
        wallBlock(ModBlocks.POLISHED_MEGA_SHARD_WALL.get(), blockTexture(ModBlocks.POLISHED_MEGA_SHARD.get()));
        wallBlock(ModBlocks.MEGA_SHARD_BRICK_WALL.get(), blockTexture(ModBlocks.MEGA_SHARD_BRICKS.get()));

        axisBlock(ModBlocks.CHISELED_ORIGIN_ALLOY.get(),
                modLoc("block/chiseled_origin_block_side"),
                modLoc("block/chiseled_origin_block_top"));
        blockItem(ModBlocks.CHISELED_ORIGIN_ALLOY);

        axisBlock(ModBlocks.CHISELED_MEGA_SHARD.get(),
                modLoc("block/chiseled_mega_shard_side"),
                modLoc("block/chiseled_mega_shard_top"));
        blockItem(ModBlocks.CHISELED_MEGA_SHARD);
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(),
                models().getExistingFile(modLoc("block/" + deferredBlock.getId().getPath())));
    }
}
