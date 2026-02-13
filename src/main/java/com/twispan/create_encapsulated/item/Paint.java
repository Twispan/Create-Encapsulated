package com.twispan.create_encapsulated.item;

import com.twispan.create_encapsulated.advancements.ModTriggers;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import com.twispan.create_encapsulated.fluid.paint.PaintFluidType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Paint extends Item {
    private final Supplier<? extends Fluid> fluid;

    public Paint (Properties props, Supplier<? extends Fluid> fluid) {
        super(props);
        this.fluid = fluid;
    }

    public Supplier<? extends Fluid> getFluid() {
        return fluid;
    }

    public PaintColor getColor() {
        return ((PaintFluidType) this.fluid.get().getFluidType()).getPaintColor();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) { return 32; }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public boolean hasCraftingRemainingItem(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(@NotNull ItemStack stack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800));

            if (entity instanceof ServerPlayer serverPlayer) {
                ModTriggers.DRANK_PAINT.get().trigger(serverPlayer, this.getColor());
            }
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);

            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }
}
