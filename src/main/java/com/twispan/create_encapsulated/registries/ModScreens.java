package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.screen.CarvingTableScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = CreateEncapsulated.MODID, value = Dist.CLIENT)
public class ModScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(
                ModMenuTypes.CARVING_TABLE.get(),
                CarvingTableScreen::new
        );
    }
}
