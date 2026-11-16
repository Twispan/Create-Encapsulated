package com.twispan.create_encapsulated.advancements.slugma;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.registries.ModTriggers;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SlugmaBurnerHooks {
    public static void onSlugmaStore(ServerPlayer player, ServerLevel level, BlockPos pos, Pokemon pokemon) {
        if (player == null || level == null || player.hasDisconnected()) return;
        if (!SlugmaBurnerHelper.isSlugma(pokemon)) return;
        if (!(level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity)) return;

        ModTriggers.STORE_SLUGMA.get().trigger(player);
    }

    public static void onSlugmaFeed(ServerPlayer player, ItemStack stack, Pokemon pokemon) {
        if (player == null || player.hasDisconnected()) return;
        if (stack.isEmpty()) return;

        if (stack.is(Items.LAVA_BUCKET) && pokemon.getLevel() == 100) {
            ModTriggers.FEED_MAX_LEVEL_SLUGMA_LAVA.get().trigger(player);
        }
        if (stack.is(AllItems.BLAZE_CAKE.get())) {
            ModTriggers.FEED_SLUGMA_BLAZE_CAKE.get().trigger(player);
        }
    }
}
