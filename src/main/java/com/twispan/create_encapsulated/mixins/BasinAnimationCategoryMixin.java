package com.twispan.create_encapsulated.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.jei.category.MixingCategory;
import com.simibubi.create.compat.jei.category.PackingCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.twispan.create_encapsulated.plugins.jei.category.animations.AnimatedSlugmaBurner;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({MixingCategory.class, PackingCategory.class})
public class BasinAnimationCategoryMixin {
    @Unique
    private static final AnimatedSlugmaBurner create_Encapsulated$burner = new AnimatedSlugmaBurner();

    @WrapOperation(
            method = "draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/client/gui/GuiGraphics;DD)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/compat/jei/category/animations/AnimatedBlazeBurner;draw(Lnet/minecraft/client/gui/GuiGraphics;II)V"
            )
    )
    private void create_encapsulated$drawMixerCategoryWithSlugma(AnimatedBlazeBurner instance, GuiGraphics graphics,
                                                                 int xOffset, int yOffset, Operation<Void> original,
                                                                 @Local HeatCondition heatCondition) {

        int PHASE_TICKS = 40;
        int phase = (AnimationTickHolder.getTicks() / PHASE_TICKS) % 2;

        if (phase == 0) {
            original.call(instance, graphics, xOffset, yOffset);
        } else if (phase == 1) {
            create_Encapsulated$burner.withHeat(heatCondition.visualizeAsBlazeBurner());
            create_Encapsulated$burner.draw(graphics, xOffset, yOffset);
        }
    }
}
