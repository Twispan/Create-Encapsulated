package com.twispan.create_encapsulated.util;

import com.twispan.create_encapsulated.fluid.paint.PaintColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaintColorMapper {
    private static final Map<Item, Item[]> COLOR_FAMILIES = new HashMap<>();

    static {
        register(
                Items.WHITE_WOOL, Items.ORANGE_WOOL, Items.MAGENTA_WOOL, Items.LIGHT_BLUE_WOOL,
                Items.YELLOW_WOOL, Items.LIME_WOOL, Items.PINK_WOOL, Items.GRAY_WOOL,
                Items.LIGHT_GRAY_WOOL, Items.CYAN_WOOL, Items.PURPLE_WOOL, Items.BLUE_WOOL,
                Items.BROWN_WOOL, Items.GREEN_WOOL, Items.RED_WOOL, Items.BLACK_WOOL
        );
        register(
                Items.WHITE_CARPET, Items.ORANGE_CARPET, Items.MAGENTA_CARPET, Items.LIGHT_BLUE_CARPET,
                Items.YELLOW_CARPET, Items.LIME_CARPET, Items.PINK_CARPET, Items.GRAY_CARPET,
                Items.LIGHT_GRAY_CARPET, Items.CYAN_CARPET, Items.PURPLE_CARPET, Items.BLUE_CARPET,
                Items.BROWN_CARPET, Items.GREEN_CARPET, Items.RED_CARPET, Items.BLACK_CARPET
        );
        register(
                Items.WHITE_CONCRETE, Items.ORANGE_CONCRETE, Items.MAGENTA_CONCRETE, Items.LIGHT_BLUE_CONCRETE,
                Items.YELLOW_CONCRETE, Items.LIME_CONCRETE, Items.PINK_CONCRETE, Items.GRAY_CONCRETE,
                Items.LIGHT_GRAY_CONCRETE, Items.CYAN_CONCRETE, Items.PURPLE_CONCRETE, Items.BLUE_CONCRETE,
                Items.BROWN_CONCRETE, Items.GREEN_CONCRETE, Items.RED_CONCRETE, Items.BLACK_CONCRETE
        );
        register(
                Items.WHITE_CONCRETE_POWDER, Items.ORANGE_CONCRETE_POWDER, Items.MAGENTA_CONCRETE_POWDER, Items.LIGHT_BLUE_CONCRETE_POWDER,
                Items.YELLOW_CONCRETE_POWDER, Items.LIME_CONCRETE_POWDER, Items.PINK_CONCRETE_POWDER, Items.GRAY_CONCRETE_POWDER,
                Items.LIGHT_GRAY_CONCRETE_POWDER, Items.CYAN_CONCRETE_POWDER, Items.PURPLE_CONCRETE_POWDER, Items.BLUE_CONCRETE_POWDER,
                Items.BROWN_CONCRETE_POWDER, Items.GREEN_CONCRETE_POWDER, Items.RED_CONCRETE_POWDER, Items.BLACK_CONCRETE_POWDER
        );
        register(
                Items.WHITE_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.MAGENTA_SHULKER_BOX, Items.LIGHT_BLUE_SHULKER_BOX,
                Items.YELLOW_SHULKER_BOX, Items.LIME_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.GRAY_SHULKER_BOX,
                Items.LIGHT_GRAY_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, Items.BLUE_SHULKER_BOX,
                Items.BROWN_SHULKER_BOX, Items.GREEN_SHULKER_BOX, Items.RED_SHULKER_BOX, Items.BLACK_SHULKER_BOX
        );
        register(
                Items.WHITE_BED, Items.ORANGE_BED, Items.MAGENTA_BED, Items.LIGHT_BLUE_BED,
                Items.YELLOW_BED, Items.LIME_BED, Items.PINK_BED, Items.GRAY_BED,
                Items.LIGHT_GRAY_BED, Items.CYAN_BED, Items.PURPLE_BED, Items.BLUE_BED,
                Items.BROWN_BED, Items.GREEN_BED, Items.RED_BED, Items.BLACK_BED
        );
        register(
                Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER,
                Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER,
                Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER,
                Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER
        );
        register(
                Items.WHITE_CANDLE, Items.ORANGE_CANDLE, Items.MAGENTA_CANDLE, Items.LIGHT_BLUE_CANDLE,
                Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.PINK_CANDLE, Items.GRAY_CANDLE,
                Items.LIGHT_GRAY_CANDLE, Items.CYAN_CANDLE, Items.PURPLE_CANDLE, Items.BLUE_CANDLE,
                Items.BROWN_CANDLE, Items.GREEN_CANDLE, Items.RED_CANDLE, Items.BLACK_CANDLE
        );
        COLOR_FAMILIES.put(Items.SHULKER_BOX, COLOR_FAMILIES.get(Items.WHITE_SHULKER_BOX));
        COLOR_FAMILIES.put(Items.TERRACOTTA, COLOR_FAMILIES.get(Items.WHITE_TERRACOTTA));
        COLOR_FAMILIES.put(Items.GLASS, COLOR_FAMILIES.get(Items.WHITE_STAINED_GLASS));
        COLOR_FAMILIES.put(Items.GLASS_PANE, COLOR_FAMILIES.get(Items.WHITE_STAINED_GLASS_PANE));
        COLOR_FAMILIES.put(Items.CANDLE, COLOR_FAMILIES.get(Items.WHITE_CANDLE));
    }

    private static void register(Item... items) {
        if (items.length != 16) throw new IllegalArgumentException("Color family must have exactly 16 items.");
        for (Item item : items) {
            COLOR_FAMILIES.put(item, items);
        }
    }

    public static boolean isRecolorable(ItemStack stack) {
        return COLOR_FAMILIES.containsKey(stack.getItem());
    }

    public static Optional<ItemStack> recolor(ItemStack stack, PaintColor paintColor) {
        Item[] family = COLOR_FAMILIES.get(stack.getItem());
        if (family == null) return Optional.empty();

        DyeColor dyeColor = toDyeColor(paintColor);
        if (dyeColor == null) return Optional.empty();

        Item result = family[dyeColor.getId()];
        ItemStack resultStack = new ItemStack(result, 1);

        // Preserve NBT
        resultStack.applyComponentsAndValidate(stack.getComponentsPatch());

        return Optional.of(resultStack);
    }

    public static DyeColor toDyeColor(PaintColor paintColor) {
        return switch (paintColor) {
            case WHITE -> DyeColor.WHITE;
            case ORANGE -> DyeColor.ORANGE;
            case MAGENTA -> DyeColor.MAGENTA;
            case LIGHTBLUE -> DyeColor.LIGHT_BLUE;
            case YELLOW -> DyeColor.YELLOW;
            case LIME -> DyeColor.LIME;
            case PINK -> DyeColor.PINK;
            case GRAY -> DyeColor.GRAY;
            case LIGHTGRAY -> DyeColor.LIGHT_GRAY;
            case CYAN -> DyeColor.CYAN;
            case PURPLE -> DyeColor.PURPLE;
            case BLUE -> DyeColor.BLUE;
            case BROWN -> DyeColor.BROWN;
            case GREEN -> DyeColor.GREEN;
            case RED -> DyeColor.RED;
            case BLACK -> DyeColor.BLACK;
        };
    }
}
