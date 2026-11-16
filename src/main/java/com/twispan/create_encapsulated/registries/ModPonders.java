package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.plugins.ponder.ModScenes;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ModPonders implements PonderPlugin {
    @Override
    public @NotNull String getModId() {
        return CreateEncapsulated.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModBlocks.SLUGMA_BURNER.getId())
                .addStoryBoard("slugma_burner", ModScenes::slugma_burner);
    }
}
