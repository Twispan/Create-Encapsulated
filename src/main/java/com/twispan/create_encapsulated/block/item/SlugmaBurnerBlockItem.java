package com.twispan.create_encapsulated.block.item;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.twispan.create_encapsulated.block.SlugmaBurnerBlock;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlugmaBurnerBlockItem extends BlockItem {

    public SlugmaBurnerBlockItem(SlugmaBurnerBlock block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        Level level = context.level();
        if (level == null) return;

        StoredPokemon stored = stack.get(ModComponents.POKEMON.get());
        if (stored == null) return;

        Pokemon pokemon = null;
        try {
            pokemon = Pokemon.Companion.loadFromNBT(level.registryAccess(), stored.pokemon());
        } catch (Exception ignored) {
        }
        if (pokemon == null) return;

        MutableComponent nickname = pokemon.getNickname();
        boolean hasNickname = nickname != null;
        String speciesName = pokemon.getSpecies().getName();
        int pokemonLevel = pokemon.getLevel();

        ChatFormatting color;
        if (pokemonLevel < 25) color = ChatFormatting.RED;
        else if (pokemonLevel < 50) color = ChatFormatting.YELLOW;
        else if (pokemonLevel <= 99) color = ChatFormatting.GREEN;
        else color = ChatFormatting.AQUA;

        if (hasNickname) {
            tooltipComponents.add(nickname.copy()
                    .append(Component.literal(" ("+speciesName+")").withStyle(ChatFormatting.GRAY)));
        } else {
            tooltipComponents.add(Component.nullToEmpty(speciesName));
        }
        tooltipComponents.add(Component.nullToEmpty("OT: ").copy().withStyle(ChatFormatting.GRAY)
                .append(Component.literal(stored.ownerName()).withStyle(ChatFormatting.WHITE)));
        tooltipComponents.add(Component.nullToEmpty("Lv. ").copy()
                .append(Component.literal(String.valueOf(pokemonLevel)).withStyle(color)));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level,
                              @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;
        if (stack.has(ModComponents.POKEMON.get())) return;

        Pokemon pokemon = SlugmaBurnerHelper.getDefaultSlugma(player);
        CompoundTag tag = pokemon.saveToNBT(level.registryAccess(), new CompoundTag());
        stack.set(ModComponents.POKEMON.get(),
                new StoredPokemon(tag, player.getUUID(), player.getGameProfile().getName()));
    }
}
