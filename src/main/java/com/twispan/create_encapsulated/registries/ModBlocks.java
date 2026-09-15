package com.twispan.create_encapsulated.registries;

import com.cobblemon.mod.common.pokemon.ai.BlockBehavior;
import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateEncapsulated.MODID);

    // ==================================
    // ==         Origin Alloy         ==
    // ==================================
    public static final DeferredBlock<Block> ORIGIN_ALLOY_BLOCK =
            BLOCKS.register("origin_alloy_block", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .destroyTime(4.0F)
                            .explosionResistance(6.0F)
                            .sound(SoundType.DEEPSLATE) // Perhaps we could even work on our own sounds for this?
                            .requiresCorrectToolForDrops()));

    // -- Polished variant --
    public static final DeferredBlock<Block> POLISHED_ORIGIN_ALLOY =
            BLOCKS.register("polished_origin_alloy", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .destroyTime(4.0F)
                            .explosionResistance(6.0F)
                            .sound(SoundType.DEEPSLATE_TILES)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> POLISHED_ORIGIN_ALLOY_STAIRS =
            BLOCKS.register("polished_origin_alloy_stairs", () -> new StairBlock(
                    POLISHED_ORIGIN_ALLOY.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_ORIGIN_ALLOY.get())));

    public static final DeferredBlock<SlabBlock> POLISHED_ORIGIN_ALLOY_SLAB =
            BLOCKS.register("polished_origin_alloy_slab", () -> new SlabBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_ORIGIN_ALLOY.get())));

    public static final DeferredBlock<WallBlock> POLISHED_ORIGIN_ALLOY_WALL =
            BLOCKS.register("polished_origin_alloy_wall", () -> new WallBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_ORIGIN_ALLOY.get())));

    // -- Brick variant --
    public static final DeferredBlock<Block> ORIGIN_ALLOY_BRICKS =
            BLOCKS.register("origin_alloy_bricks", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .destroyTime(4.0F)
                            .explosionResistance(6.0F)
                            .sound(SoundType.DEEPSLATE_BRICKS)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> ORIGIN_ALLOY_BRICK_STAIRS =
            BLOCKS.register("origin_alloy_brick_stairs", () -> new StairBlock(
                    ORIGIN_ALLOY_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(ORIGIN_ALLOY_BRICKS.get())));

    public static final DeferredBlock<SlabBlock> ORIGIN_ALLOY_BRICK_SLAB =
            BLOCKS.register("origin_alloy_brick_slab", () -> new SlabBlock(
                    BlockBehaviour.Properties.ofFullCopy(ORIGIN_ALLOY_BRICKS.get())));

    public static final DeferredBlock<WallBlock> ORIGIN_ALLOY_BRICK_WALL =
            BLOCKS.register("origin_alloy_brick_wall", () -> new WallBlock(
                    BlockBehaviour.Properties.ofFullCopy(ORIGIN_ALLOY_BRICKS.get())));

    // -- Chiseled variant --
    public static final DeferredBlock<RotatedPillarBlock> CHISELED_ORIGIN_ALLOY =
            BLOCKS.register("chiseled_origin_alloy", () -> new RotatedPillarBlock(
                    BlockBehaviour.Properties.ofFullCopy(ORIGIN_ALLOY_BRICKS.get())));

    // ================================
    // ==         Mega Shard         ==
    // ================================
    public static final DeferredBlock<Block> MEGA_SHARD_BLOCK =
            BLOCKS.register("mega_shard_block", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .destroyTime(4.0F)
                            .explosionResistance(6.0F)
                            .sound(SoundType.AMETHYST_CLUSTER)
                            .requiresCorrectToolForDrops()));

    // -- Polished variant --
    public static final DeferredBlock<Block> POLISHED_MEGA_SHARD =
            BLOCKS.register("polished_mega_shard", () -> new Block(
                    BlockBehaviour.Properties.of()
                            .destroyTime(4.0F)
                            .explosionResistance(6.0F)
                            .sound(SoundType.AMETHYST)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> POLISHED_MEGA_SHARD_STAIRS =
            BLOCKS.register("polished_mega_shard_stairs", () -> new StairBlock(
                    POLISHED_MEGA_SHARD.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    public static final DeferredBlock<SlabBlock> POLISHED_MEGA_SHARD_SLAB =
            BLOCKS.register("polished_mega_shard_slab", () -> new SlabBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    public static final DeferredBlock<WallBlock> POLISHED_MEGA_SHARD_WALL =
            BLOCKS.register("polished_mega_shard_wall", () -> new WallBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    // -- Brick variant --
    public static final DeferredBlock<Block> MEGA_SHARD_BRICKS =
            BLOCKS.register("mega_shard_bricks", () -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    public static final DeferredBlock<StairBlock> MEGA_SHARD_BRICK_STAIRS =
            BLOCKS.register("mega_shard_brick_stairs", () -> new StairBlock(
                    MEGA_SHARD_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    public static final DeferredBlock<SlabBlock> MEGA_SHARD_BRICK_SLAB =
            BLOCKS.register("mega_shard_brick_slab", () -> new SlabBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    public static final DeferredBlock<WallBlock> MEGA_SHARD_BRICK_WALL =
            BLOCKS.register("mega_shard_brick_wall", () -> new WallBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));

    // -- Chiseled variant --
    public static final DeferredBlock<RotatedPillarBlock> CHISELED_MEGA_SHARD =
            BLOCKS.register("chiseled_mega_shard", () -> new RotatedPillarBlock(
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_MEGA_SHARD.get())));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
