package com.twispan.create_encapsulated.villagers.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;

public record TradeIngredient(Optional<ResourceLocation> item, Optional<TagKey<Item>> tag, int count) {
    public static final Codec<TradeIngredient> CODEC = RecordCodecBuilder.<TradeIngredient>create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("id").forGetter(TradeIngredient::item),
            TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(TradeIngredient::tag),
            Codec.INT.optionalFieldOf("count", 1).forGetter(TradeIngredient::count)
    ).apply(instance, TradeIngredient::new)).validate(TradeIngredient::validate);

    private static DataResult<TradeIngredient> validate(TradeIngredient ingredient) {
        boolean hasItem = ingredient.item().isPresent();
        boolean hasTag = ingredient.tag().isPresent();

        if (!hasItem && !hasTag) {
            return DataResult.error(() -> "TradeIngredient must specify either 'id' or 'tag'");
        }
        return DataResult.success(ingredient);
    }

    public ItemStack resolve(RandomSource randomSource) {
        if(item.isPresent()) {
            Item resolved = BuiltInRegistries.ITEM.get((item.get()));
            return new ItemStack(resolved, count);
        }

        if (tag.isPresent()) {
            List<Item> matches = BuiltInRegistries.ITEM.getTag(tag.get())
                    .map(named -> named.stream().map(net.minecraft.core.Holder::value).toList())
                    .orElse(List.of());

            if (!matches.isEmpty()) {
                Item chosen = matches.get(randomSource.nextInt(matches.size()));
                return new ItemStack(chosen, count);
            }
        }
        return ItemStack.EMPTY; // I would prefer to add like a placeholder item here, but I think a crash is better lol
    }
}
