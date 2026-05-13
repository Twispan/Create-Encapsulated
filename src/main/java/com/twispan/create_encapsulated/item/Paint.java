package com.twispan.create_encapsulated.item;

import com.twispan.create_encapsulated.registries.ModDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import com.twispan.create_encapsulated.advancements.ModTriggers;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import com.twispan.create_encapsulated.fluid.paint.PaintFluidType;
import com.twispan.create_encapsulated.registries.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.neoforged.bus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class Paint extends Item {
    private final Optional<Supplier<? extends Fluid>> fluid;

    public Paint (Properties props, Supplier<? extends Fluid> fluid) {
        super(props);
        this.fluid = Optional.ofNullable(fluid);
    }

    public Optional<Supplier<? extends Fluid>> getFluid() {
        return fluid;
    }

    public PaintColor getColor() {
        return fluid.map(f -> ((PaintFluidType) f.get().getFluidType()).getPaintColor())
                .orElse(null);
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
            if (stack.is(ModItems.RAINBOW_PAINT)) {
                DamageSource tasteRainbow = new DamageSource(
                        level.registryAccess()
                                .registryOrThrow(Registries.DAMAGE_TYPE)
                                .getHolderOrThrow(ModDamageTypes.TASTE_RAINBOW)
                );
                entity.hurt(tasteRainbow, Float.MAX_VALUE);
            } else {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800));
            }

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

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if(stack.is(ModItems.RAINBOW_PAINT.get())) {
            event.getToolTip().add(
                    Component.literal("Where did you get this?")
                            .withStyle(ChatFormatting.GRAY)
            );
            event.getToolTip().add(
                    Component.literal("Drinking it may lead to unexpected consequences.")
                            .withStyle(ChatFormatting.DARK_PURPLE)
            );
        }
    }
}
