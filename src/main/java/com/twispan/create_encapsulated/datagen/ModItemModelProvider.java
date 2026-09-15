package com.twispan.create_encapsulated.datagen;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateEncapsulated.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        wallItem(ModBlocks.POLISHED_ORIGIN_WALL, ModBlocks.POLISHED_ORIGIN_BLOCK);
        wallItem(ModBlocks.POLISHED_ORIGIN_BRICK_WALL, ModBlocks.POLISHED_ORIGIN_BRICKS);
        wallItem(ModBlocks.POLISHED_MEGA_SHARD_WALL, ModBlocks.POLISHED_MEGA_SHARD_BLOCK);
        wallItem(ModBlocks.POLISHED_MEGA_SHARD_BRICK_WALL, ModBlocks.POLISHED_MEGA_SHARD_BRICKS);
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(CreateEncapsulated.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
