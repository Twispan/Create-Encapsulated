package com.twispan.create_encapsulated.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 *
 * @author itsfirestorm
 */
public class Aprijuice extends Item {
    String flavor;
    
    public Aprijuice (Properties props, String flavor) {
        super(props);
        this.flavor = flavor;
    }
    
    /**
     *
     * @param stack
     * @param entity
     * @return
     */
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }
    
    /**
     *
     * @param stack
     * @return
     */
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
    
    /**
     *
     * @param level
     * @param player
     * @param hand
     * @return
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            level.playSound(
                null,
                player.getZ(),
                player.getX(),
                player.getY(),
                SoundEvents.GENERIC_DRINK,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
            );
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
    
    /**
     *
     * @param stack
     * @param level
     * @param entity
     * @return
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Apply potion effect
        if (!level.isClientSide) {
            switch(flavor) {
                case("spicy") -> entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200));
                case("dry") -> entity.addEffect(new MobEffectInstance(MobEffects.LUCK, 200));
                case("sour") -> entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 200));
                case("bitter") -> entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200));
                case("sweet") -> entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
                case("umami") -> entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200));
                case("salty") -> entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200));
            }
        }
        
        // Apply hunger/saturation
        if (entity instanceof Player player) {
            player.getFoodData().eat(4, 0.3f);
        }

        // Return bottle
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
