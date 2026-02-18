package com.twispan.create_encapsulated.crafting;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.twispan.create_encapsulated.CreateEncapsulated;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateEncapsulated.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<PaintDyesArmor>> PAINT_ARMOR_DYE =
            RECIPE_SERIALIZERS.register("paint_armor_dye",
                    () -> new SimpleCraftingRecipeSerializer<>(PaintDyesArmor::new));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PaintDyesBlocks>> PAINT_BLOCK_DYE =
            RECIPE_SERIALIZERS.register("paintable",
                    () -> new RecipeSerializer<>() {

                        private final MapCodec<PaintDyesBlocks> CODEC = RecordCodecBuilder.mapCodec(instance ->
                                instance.group(
                                        CraftingBookCategory.CODEC.fieldOf("category")
                                                .forGetter(r -> CraftingBookCategory.MISC),
                                        Codec.STRING.optionalFieldOf("group", "")
                                                .forGetter(r -> ""),
                                        ItemStack.STRICT_CODEC.fieldOf("result")
                                                .forGetter(r -> ItemStack.EMPTY), // Default value, can be replaced later
                                        FLAT_INGREDIENT_LIST_CODEC.fieldOf("ingredients")
                                                .forGetter(ShapelessRecipe::getIngredients) // Empty list, can be replaced later
                                ).apply(instance, (category, group, result, ingredients) -> {
                                    NonNullList<Ingredient> ingredientNonNullList = NonNullList.create();
                                    ingredientNonNullList.addAll(ingredients);
                                    return new PaintDyesBlocks(group, category, result, ingredientNonNullList);
                                })
                        );

                        private final StreamCodec<RegistryFriendlyByteBuf, PaintDyesBlocks> STREAM_CODEC =
                                StreamCodec.composite(
                                        CraftingBookCategory.STREAM_CODEC, r -> CraftingBookCategory.MISC,
                                        ByteBufCodecs.STRING_UTF8, r -> "",
                                        ItemStack.STREAM_CODEC, PaintDyesBlocks::getResult, // ItemStack.EMPTY is not valid in this situation
                                        FLAT_INGREDIENT_STREAM_CODEC, ShapelessRecipe::getIngredients,
                                        (category, group, result, ingredients) -> {
                                            NonNullList<Ingredient> ingredientNonNullList = NonNullList.create();
                                            ingredientNonNullList.addAll(ingredients);
                                            return new PaintDyesBlocks(group, category, result, ingredientNonNullList);
                                        }
                                );

                        private static final Codec<List<Ingredient>> FLAT_INGREDIENT_LIST_CODEC =
                                Codec.either(
                                                Ingredient.CODEC_NONEMPTY,
                                                Ingredient.CODEC_NONEMPTY.listOf()
                                        )
                                        .listOf()
                                        .xmap(
                                                list -> list.stream()
                                                        .flatMap(either -> either.map(
                                                                Stream::of,
                                                                Collection::stream
                                                        ))
                                                        .collect(java.util.stream.Collectors.toList()),
                                                list -> list.stream()
                                                        .<com.mojang.datafixers.util.Either<Ingredient, java.util.List<Ingredient>>>map(
                                                                Either::left
                                                        )
                                                        .collect(java.util.stream.Collectors.toList())
                                        );

                        private static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> FLAT_INGREDIENT_STREAM_CODEC =
                                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list());

                        @Override
                        public @NotNull MapCodec<PaintDyesBlocks> codec() {
                            return CODEC;
                        }

                        @Override
                        public @NotNull StreamCodec<RegistryFriendlyByteBuf, PaintDyesBlocks> streamCodec() {
                            return STREAM_CODEC;
                        }
                    });
}
