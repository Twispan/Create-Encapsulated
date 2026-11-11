package com.twispan.create_encapsulated.events;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.SlugmaBurnerBlock;
import com.twispan.create_encapsulated.block.item.SlugmaBurnerBlockItem;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import com.twispan.create_encapsulated.components.StoredPokemon;
import com.twispan.create_encapsulated.registries.ModComponents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

import java.util.UUID;
import java.util.function.Predicate;

@EventBusSubscriber(modid = CreateEncapsulated.MODID)
public class SlugmaBurnerRetrieval {
    @SubscribeEvent
    public static void BurnerDestroyed (EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof ItemEntity entity)) return;

        Entity.RemovalReason reason = entity.getRemovalReason();
        if (reason == null || !reason.shouldDestroy()) return;

        ItemStack burner = entity.getItem();
        if (burner.isEmpty()) return;
        StoredPokemon stored = burner.get(ModComponents.POKEMON.get());
        if (stored == null) return;

        RegistryAccess registryAccess = event.getLevel().registryAccess();
        try {
            Pokemon pokemon = Pokemon.Companion.loadFromNBT(registryAccess, stored.pokemon());
            PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(stored.owner(), registryAccess);
            if (!partyStore.add(pokemon)) {
                Cobblemon.INSTANCE.getStorage().getPC(stored.owner(), registryAccess).add(pokemon);
            }
            burner.remove(ModComponents.POKEMON.get());
        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public static void onPick(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isPickBlock()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;
        if (!(mc.hitResult instanceof BlockHitResult hit) || hit.getType() != HitResult.Type.BLOCK) return;
        BlockPos pos = hit.getBlockPos();
        if (!(mc.level.getBlockState(pos).getBlock() instanceof SlugmaBurnerBlock)) return;

        boolean ctrl = Screen.hasControlDown();
        if (ctrl && mc.player.getAbilities().instabuild) return;

        Inventory inv = mc.player.getInventory();
        int slot = -1;
        if (ctrl && mc.level.getBlockEntity(pos) instanceof SlugmaBurnerBlockEntity blockEntity
                && blockEntity.getPokemon() != null) {
            UUID target = blockEntity.getPokemon().getUuid();
            slot = findSlot(inv, stack -> target.equals(storedUuid(stack)));
        }
        if (slot == -1) slot = findSlot(inv, stack -> stack.getItem() instanceof SlugmaBurnerBlockItem);
        if (slot == -1) return;

        if (Inventory.isHotbarSlot(slot)) inv.selected = slot;
        else mc.gameMode.handlePickItem(slot);
        event.setCanceled(true);
    }

    private static int findSlot(Inventory inv, Predicate<ItemStack> test) {
        for (int i = 0; i < inv.items.size(); i++) {
            ItemStack stack = inv.items.get(i);
            if (!stack.isEmpty() && test.test(stack)) return i;
        }
        return -1;
    }

    private static UUID storedUuid(ItemStack stack) {
        StoredPokemon stored = stack.get(ModComponents.POKEMON.get());
        if (stored == null || !stored.pokemon().contains("UUID", Tag.TAG_INT_ARRAY)) return null;
        return UUIDUtil.uuidFromIntArray(stored.pokemon().getIntArray("UUID"));
    }
}
