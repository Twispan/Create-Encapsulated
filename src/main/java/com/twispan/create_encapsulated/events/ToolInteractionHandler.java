package com.twispan.create_encapsulated.events;

import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.twispan.create_encapsulated.config.CEConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "create_encapsulated")
public class ToolInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        if (!CEConfig.ALLOW_AXE_STRIPPING.get()
                && stack.getItem() instanceof AxeItem) {

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
            return;
        }

        if (!CEConfig.ALLOW_WRENCH_UNCASING.get()
                && stack.getItem() instanceof WrenchItem
                && event.getEntity().isCrouching()) {

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }
}