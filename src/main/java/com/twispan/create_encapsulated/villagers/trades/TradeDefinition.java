package com.twispan.create_encapsulated.villagers.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record TradeDefinition (
        ResourceLocation profession,
        int level,
        ItemStack buyItemA,
        Optional<ItemStack> buyItemB,
        ItemStack sellItem,
        int maxUses,
        int xp,
        float priceMultiplier
){
    public static final Codec<TradeDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("profession").forGetter(TradeDefinition::profession),
            Codec.intRange(1, 5).fieldOf("level").forGetter(TradeDefinition::level),
            ItemStack.CODEC.fieldOf("buy_item").forGetter(TradeDefinition::buyItemA),
            ItemStack.CODEC.optionalFieldOf("buy_item_b").forGetter(TradeDefinition::buyItemB),
            ItemStack.CODEC.fieldOf("sell_item").forGetter(TradeDefinition::sellItem),
            Codec.INT.optionalFieldOf("max_uses", 12).forGetter(TradeDefinition::maxUses),
            Codec.INT.optionalFieldOf("xp", 1).forGetter(TradeDefinition::xp),
            Codec.FLOAT.optionalFieldOf("price_multiplier", 0.05F).forGetter(TradeDefinition::priceMultiplier)
    ).apply(instance, TradeDefinition::new));
}
