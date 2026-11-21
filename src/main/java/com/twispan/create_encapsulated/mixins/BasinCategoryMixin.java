package com.twispan.create_encapsulated.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.twispan.create_encapsulated.registries.items.ModItems;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BasinCategory.class)
public class BasinCategoryMixin {
    @WrapOperation(
            method = "setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lmezz/jei/api/gui/builder/IRecipeSlotBuilder;addItemStack(Lnet/minecraft/world/item/ItemStack;)Lmezz/jei/api/gui/builder/IIngredientAcceptor;"))

    private IIngredientAcceptor create_encapsulated$addSlugmaBurnerToItemSlot(IRecipeSlotBuilder instance,
                                                                              ItemStack itemStack,
                                                                              Operation<IIngredientAcceptor> original) {

        if (itemStack.is(AllBlocks.BLAZE_BURNER.asItem())) {
            original.call(instance, itemStack);
            return instance.addItemStack(ModItems.SLUGMA_BURNER.toStack());
        }
        return original.call(instance, itemStack);
    }
}
