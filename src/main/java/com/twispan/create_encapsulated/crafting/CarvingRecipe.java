package com.twispan.create_encapsulated.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.twispan.create_encapsulated.registries.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CarvingRecipe implements Recipe<SingleRecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;

    public CarvingRecipe(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput input, @NotNull Level level) {
        return ingredient.test(input.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider registries) {
        return result;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CARVING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CARVING.get();
    }

    public static class Serializer implements RecipeSerializer<CarvingRecipe> {

        private static final MapCodec<CarvingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(r -> r.ingredient),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result)
                ).apply(instance, CarvingRecipe::new)
        );

        private static final StreamCodec<RegistryFriendlyByteBuf, CarvingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.ingredient,
                        ItemStack.STREAM_CODEC, r -> r.result,
                        CarvingRecipe::new
                );

        @Override
        public @NotNull MapCodec<CarvingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, CarvingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
