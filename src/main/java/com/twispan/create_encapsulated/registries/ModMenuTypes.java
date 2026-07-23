package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.screen.carving_blade.CarvingBladeMenu;
import com.twispan.create_encapsulated.screen.carving_table.CarvingTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, CreateEncapsulated.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CarvingTableMenu>> CARVING_TABLE =
            MENU_TYPES.register("carving_table", () -> IMenuTypeExtension.create(CarvingTableMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<CarvingBladeMenu>> CARVING_BLADE =
            MENU_TYPES.register("carving_blade", () -> IMenuTypeExtension.create(CarvingBladeMenu::new));

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
