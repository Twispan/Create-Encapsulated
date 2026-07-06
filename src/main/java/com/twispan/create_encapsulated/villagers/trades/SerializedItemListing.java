package com.twispan.create_encapsulated.villagers.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record SerializedItemListing(TradeDefinition definition) implements VillagerTrades.ItemListing {

    @Override
    public MerchantOffer getOffer(@NotNull Entity trader, @NotNull RandomSource randomSource) {
        ItemStack resolvedA = definition.buyItemA().resolve(randomSource);
        Optional<ItemStack> resolvedB = definition.buyItemB().map(ingredient -> ingredient.resolve(randomSource));
        ItemStack resolvedSell = definition.sellItem().resolve(randomSource);

        ItemCost costA = toItemCost(resolvedA);
        Optional<ItemCost> costB = resolvedB.map(SerializedItemListing::toItemCost);

        return new MerchantOffer(
                costA,
                costB,
                resolvedSell,
                definition.maxUses(),
                definition().xp(),
                definition.priceMultiplier()
        );
    }

    private static ItemCost toItemCost(ItemStack stack) {
        return new ItemCost(stack.getItem(), stack.getCount());
    }
}
