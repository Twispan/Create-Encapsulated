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

        simpleBlockWithItem(ModBlocks.POLISHED_ORIGIN_ALLOY.get(), cubeAll(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        simpleBlockWithItem(ModBlocks.ORIGIN_ALLOY_BRICKS.get(), cubeAll(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));

        stairsBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_STAIRS.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        stairsBlock(ModBlocks.ORIGIN_ALLOY_BRICK_STAIRS.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_ALLOY_STAIRS);
        blockItem(ModBlocks.ORIGIN_ALLOY_BRICK_STAIRS);

        slabBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        slabBlock(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_ORIGIN_ALLOY_SLAB);
        blockItem(ModBlocks.ORIGIN_ALLOY_BRICK_SLAB);

        wallBlock(ModBlocks.POLISHED_ORIGIN_ALLOY_WALL.get(), blockTexture(ModBlocks.POLISHED_ORIGIN_ALLOY.get()));
        wallBlock(ModBlocks.ORIGIN_ALLOY_BRICK_WALL.get(), blockTexture(ModBlocks.ORIGIN_ALLOY_BRICKS.get()));

        axisBlock(ModBlocks.CHISELED_ORIGIN_ALLOY.get(),
                modLoc("block/chiseled_origin_block_side"),
                modLoc("block/chiseled_origin_block_top"));
        blockItem(ModBlocks.CHISELED_ORIGIN_ALLOY);

    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(),
                models().getExistingFile(modLoc("block/" + deferredBlock.getId().getPath())));
    }
}
