package com.twispan.create_encapsulated.events;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.common.util.TriState;

@EventBusSubscriber(modid = CreateEncapsulated.MODID, value = Dist.CLIENT)
public class HideStoringPokemon {
    private static boolean isStoringSlugma(PokemonEntity p) {
        return p.isNoAi() && p.isNoGravity() && p.getBeamMode() != 3
                && SlugmaBurnerHelper.isSlugma(p.getPokemon());
    }

    @SubscribeEvent
    public static void onRender(RenderLivingEvent.Pre<?, ?> event) {
        if (event.getEntity() instanceof PokemonEntity p && isStoringSlugma(p)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onNameTagRender(RenderNameTagEvent event) {
        if (event.getEntity() instanceof PokemonEntity p && p.isNoAi()
                && SlugmaBurnerHelper.isSlugma(p.getPokemon())) {
            event.setCanRender(TriState.FALSE);
        }
    }
}
