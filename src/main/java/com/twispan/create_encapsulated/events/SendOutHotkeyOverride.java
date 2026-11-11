package com.twispan.create_encapsulated.events;

import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.keybind.CobblemonKeyBinds;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.SlugmaBurnerBlock;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;


@EventBusSubscriber(value = Dist.CLIENT, modid = CreateEncapsulated.MODID)
public class SendOutHotkeyOverride {

    public static volatile boolean suppressRelease = false;

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.screen != null) return;

        KeyMapping sendOut = CobblemonKeyBinds.INSTANCE.getSEND_OUT_POKEMON();
        InputConstants.Key key = InputConstants.getKey(event.getKey(), event.getScanCode());
        if (!sendOut.isActiveAndMatches(key)) return;
        if (event.getAction() != GLFW.GLFW_PRESS) return;

        suppressRelease = false;

        if (!(mc.hitResult instanceof BlockHitResult hitResult) || hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);

        boolean slugmaBurner = state.getBlock() instanceof SlugmaBurnerBlock;
        boolean emptyBurner = AllBlocks.BLAZE_BURNER.has(state);
        if (!slugmaBurner && !emptyBurner) return;

        int slot = CobblemonClient.INSTANCE.getStorage().getSelectedSlot();
        PacketDistributor.sendToServer(new SlugmaInteractionHandler.RetrieveSlugmaPayload(pos, slot));

        if (slugmaBurner) {
            suppressRelease = true;
        } else if (state.getValue(BlazeBurnerBlock.HEAT_LEVEL) == BlazeBurnerBlock.HeatLevel.NONE) {
            Pokemon pokemon = CobblemonClient.INSTANCE.getStorage().getParty().get(slot);
            suppressRelease = pokemon != null && SlugmaBurnerHelper.isSlugma(pokemon);
        }
    }
}
