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

import java.util.List;
import java.util.Optional;

public record TradeIngredient(Optional<ResourceLocation> item, Optional<List<ResourceLocation>> items, Optional<TagKey<Item>> tag, int count) {
    public static final Codec<TradeIngredient> CODEC = RecordCodecBuilder.<TradeIngredient>create(instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("id").forGetter(TradeIngredient::item),
            ResourceLocation.CODEC.listOf().optionalFieldOf("ids").forGetter(TradeIngredient::items),
            TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(TradeIngredient::tag),
            Codec.INT.optionalFieldOf("count", 1).forGetter(TradeIngredient::count)
    ).apply(instance, TradeIngredient::new)).validate(TradeIngredient::validate);

    private static DataResult<TradeIngredient> validate(TradeIngredient ingredient) {
        int optionsSet = (ingredient.item().isPresent() ? 1 : 0)
                + (ingredient.items().isPresent() ? 1 : 0)
                + (ingredient.tag().isPresent() ? 1 : 0);

        if (optionsSet != 1) {
            return DataResult.error(() -> "buy_item, buy_itemB and result must specify EXACTLY one of 'id', 'ids' or 'tag'");
        }

        if (ingredient.item().isPresent() && !BuiltInRegistries.ITEM.containsKey(ingredient.item.get())) {
            return DataResult.error(() -> "Unknown item id: " + ingredient.item().get());
        }

        if (ingredient.items().isPresent()) {
            for (ResourceLocation id : ingredient.items().get()) {
                if (!BuiltInRegistries.ITEM.containsKey(id)) {
                    return DataResult.error(() -> "Unknown item id in 'ids' list: " + id);
                }
            }
        }
        return DataResult.success(ingredient);
    }

    public ItemStack resolve(RandomSource randomSource) {
        if(item.isPresent()) {
            Item resolved = BuiltInRegistries.ITEM.get((item.get()));
            return new ItemStack(resolved, count);
        }

        if (items.isPresent()) {
            List<ResourceLocation> list = items.get();
            ResourceLocation chosen = list.get(randomSource.nextInt(list.size()));
            return new ItemStack(BuiltInRegistries.ITEM.get(chosen), count);
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
        return ItemStack.EMPTY; // I would prefer to add like a placeholder item here but I think a crash is better lol
    }
}
