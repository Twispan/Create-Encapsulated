package com.twispan.create_encapsulated.util;

import com.cobblemon.mod.common.CobblemonItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaintColorMapperModded {
    private static final Map<Item, Item[]> COLOR_FAMILIES = new HashMap<>();

    /*
     * To add items or blocks, you may want to know how the items/blocks and stored and what available colors there are
     * for said values, since Create stores them in DyedBlockList, if you want to add them, make sure to register them
     * with registerDyedBlockList, this method grabs every available color of the block and registers them in the
     * COLOR_FAMILIES HashMap.
     *
     * Cobblemon is a bit trickier, sure there's plaques, which are identical to every vanilla colored block, but since
     * there are a select few apricorn colors, I just hardcoded the colors inside the method, simple and straight to
     * the point, to register, use the registerSparse method, which registers them based off the apricorn colors
     * available.
     */

    static {
        registerDyedBlockList(AllBlocks.SEATS);
        registerDyedBlockList(AllBlocks.TOOLBOXES);
        registerDyedBlockList(AllBlocks.TABLE_CLOTHS);
        registerDyedBlockList(AllBlocks.PACKAGE_POSTBOXES);
        registerDyedBlockList(AllBlocks.DYED_VALVE_HANDLES);

        register(
        );

        registerSparse(
                CobblemonItems.POKEDEX_RED, CobblemonItems.POKEDEX_YELLOW, CobblemonItems.POKEDEX_GREEN,
                CobblemonItems.POKEDEX_BLUE, CobblemonItems.POKEDEX_PINK, CobblemonItems.POKEDEX_BLACK,
                CobblemonItems.POKEDEX_WHITE
        );

        registerSparse(
                CobblemonItems.CAMPFIRE_POT_RED, CobblemonItems.CAMPFIRE_POT_YELLOW, CobblemonItems.CAMPFIRE_POT_GREEN,
                CobblemonItems.CAMPFIRE_POT_BLUE, CobblemonItems.CAMPFIRE_POT_PINK, CobblemonItems.CAMPFIRE_POT_BLACK,
                CobblemonItems.CAMPFIRE_POT_WHITE
        );
        Item[] valveFamily = COLOR_FAMILIES.get(AllBlocks.DYED_VALVE_HANDLES.get(DyeColor.WHITE).asItem());
        COLOR_FAMILIES.put(AllBlocks.COPPER_VALVE_HANDLE.asItem(), valveFamily);
    }

    private static void register() {
        for (Item item : new Item[]{CobblemonItems.WHITE_PLAQUE, CobblemonItems.ORANGE_PLAQUE, CobblemonItems.MAGENTA_PLAQUE, CobblemonItems.LIGHT_BLUE_PLAQUE, CobblemonItems.YELLOW_PLAQUE, CobblemonItems.LIME_PLAQUE, CobblemonItems.PINK_PLAQUE, CobblemonItems.GRAY_PLAQUE, CobblemonItems.LIGHT_GRAY_PLAQUE, CobblemonItems.CYAN_PLAQUE, CobblemonItems.PURPLE_PLAQUE, CobblemonItems.BLUE_PLAQUE, CobblemonItems.BROWN_PLAQUE, CobblemonItems.GREEN_PLAQUE, CobblemonItems.RED_PLAQUE, CobblemonItems.BLACK_PLAQUE}) {
            COLOR_FAMILIES.put(item, new Item[]{CobblemonItems.WHITE_PLAQUE, CobblemonItems.ORANGE_PLAQUE, CobblemonItems.MAGENTA_PLAQUE, CobblemonItems.LIGHT_BLUE_PLAQUE, CobblemonItems.YELLOW_PLAQUE, CobblemonItems.LIME_PLAQUE, CobblemonItems.PINK_PLAQUE, CobblemonItems.GRAY_PLAQUE, CobblemonItems.LIGHT_GRAY_PLAQUE, CobblemonItems.CYAN_PLAQUE, CobblemonItems.PURPLE_PLAQUE, CobblemonItems.BLUE_PLAQUE, CobblemonItems.BROWN_PLAQUE, CobblemonItems.GREEN_PLAQUE, CobblemonItems.RED_PLAQUE, CobblemonItems.BLACK_PLAQUE});
        }
    }

    private static void registerDyedBlockList(DyedBlockList<?> dyedBlockList) {
        Item[] family = new Item[16];
        for (DyeColor color : DyeColor.values()) {
            family[color.getId()] = dyedBlockList.get(color).asItem();
        }
        for (Item item : family) {
            if (item != null) COLOR_FAMILIES.put(item, family);
        }
    }

    private static void registerSparse(Item red, Item yellow, Item green, Item blue,
                                       Item pink, Item black, Item white) {
        Item[] family = new Item[16];
        family[DyeColor.RED.getId()]    = red;
        family[DyeColor.YELLOW.getId()] = yellow;
        family[DyeColor.GREEN.getId()]  = green;
        family[DyeColor.BLUE.getId()]   = blue;
        family[DyeColor.PINK.getId()]   = pink;
        family[DyeColor.BLACK.getId()]  = black;
        family[DyeColor.WHITE.getId()]  = white;

        for (Item item : family) {
            if (item != null) COLOR_FAMILIES.put(item, family);
        }
    }

    public static boolean isRecolorable(ItemStack stack) {
        return COLOR_FAMILIES.containsKey(stack.getItem());
    }

    public static Optional<ItemStack> recolor(ItemStack stack, PaintColor color) {
        Item[] family = COLOR_FAMILIES.get(stack.getItem());
        if (family == null) return Optional.empty();

        // To avoid duplication, static import toDyeColor from PaintColorMapper
        DyeColor dyeColor = PaintColorMapper.toDyeColor(color);
        if (dyeColor == null) return Optional.empty();

        Item result = family[dyeColor.getId()];
        if (result == null) return Optional.empty();

        ItemStack resultStack = new ItemStack(result, 1);

        // Preserve NBT
        resultStack.applyComponentsAndValidate(stack.getComponentsPatch());
        return Optional.of(resultStack);
    }
}
