package com.twispan.create_encapsulated.item.singular;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CarvingBlade extends Item {

    public CarvingBlade(Properties properties) {
        super(properties);
    }

    private static final Map<Block, Block> STRIPPABLES = new HashMap<>();

    static {
        // Vanilla logs
        STRIPPABLES.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        STRIPPABLES.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        STRIPPABLES.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        STRIPPABLES.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        STRIPPABLES.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        STRIPPABLES.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        STRIPPABLES.put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        STRIPPABLES.put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
        STRIPPABLES.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        STRIPPABLES.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);

        // Vanilla wood (bark blocks)
        STRIPPABLES.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        STRIPPABLES.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        STRIPPABLES.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        STRIPPABLES.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        STRIPPABLES.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        STRIPPABLES.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        STRIPPABLES.put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        STRIPPABLES.put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
        STRIPPABLES.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
        STRIPPABLES.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
    }


    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (state.getBlock() instanceof CasingBlock casingBlock) {
            if (player != null && player.isCrouching() && !level.isClientSide) {
                Item casingIngredient = getCasingIngredient(state);
                assert casingIngredient != null;
                Block.popResource(level, pos, new ItemStack(casingIngredient));

                BlockState replacement = Blocks.STRIPPED_OAK_LOG.defaultBlockState();
                if (casingIngredient.equals(AllItems.STURDY_SHEET.get())) {
                    replacement = AllBlocks.BRASS_CASING.getDefaultState();
                }
                if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                    replacement = replacement.setValue(RotatedPillarBlock.AXIS,
                            state.getValue(RotatedPillarBlock.AXIS));
                }

                level.setBlock(pos, replacement, 3);
                level.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);

                context.getItemInHand().hurtAndBreak(1, player,
                        player.getEquipmentSlotForItem(context.getItemInHand()));

                return InteractionResult.SUCCESS;
            }
        }

        if (state.getBlock() instanceof IWrenchable wrenchable) {
            assert player != null;
            if (player.isCrouching()) {
                InteractionResult result = wrenchable.onSneakWrenched(state, context);

                if (result.consumesAction()) {
                    context.getItemInHand().hurtAndBreak(1, player,
                            player.getEquipmentSlotForItem(context.getItemInHand()));
                    return result;
                }
            }
        }

        Block strippedBlock = STRIPPABLES.get(state.getBlock());
        if (strippedBlock != null) {
            if (!level.isClientSide) {
                BlockState strippedState = strippedBlock.defaultBlockState();

                if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                    strippedState = strippedState.setValue(RotatedPillarBlock.AXIS,
                            state.getValue(RotatedPillarBlock.AXIS));
                }

                level.setBlock(pos, strippedState, 3);

                level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player,
                            player.getEquipmentSlotForItem(context.getItemInHand()));
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.FAIL;
    }

    private Item getCasingIngredient(BlockState state) {
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();

        return switch(id) {
            case "create:andesite_casing" -> AllItems.ANDESITE_ALLOY.get();
            case "create:brass_casing" -> AllItems.BRASS_INGOT.get();
            case "create:railway_casing" -> AllItems.STURDY_SHEET.get();
            case "create:copper_casing" -> Items.COPPER_INGOT;
            default -> null;
        };
    }
}
