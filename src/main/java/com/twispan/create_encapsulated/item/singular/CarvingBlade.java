package com.twispan.create_encapsulated.item.singular;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.twispan.create_encapsulated.crafting.CarvingRecipe;
import com.twispan.create_encapsulated.registries.ModRecipeTypes;
import com.twispan.create_encapsulated.screen.carving_blade.CarvingBladeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarvingBlade extends Item {

    public CarvingBlade(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.FAIL;
        }

        ItemStack blockAsItem = new ItemStack(state.getBlock().asItem());
        if (blockAsItem.isEmpty()) {
            return InteractionResult.FAIL;
        }

        List<RecipeHolder<CarvingRecipe>> recipes = level.getRecipeManager()
                .getRecipesFor(ModRecipeTypes.CARVING.get(), new SingleRecipeInput(blockAsItem), level);

        if (recipes.isEmpty()) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new CarvingBladeMenu(id, inv, level, pos, context.getHand()),
                    Component.translatable("menu.create_encapsulated.carving_blade")
            ), pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
