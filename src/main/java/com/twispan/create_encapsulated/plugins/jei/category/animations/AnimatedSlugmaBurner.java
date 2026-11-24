package com.twispan.create_encapsulated.plugins.jei.category.animations;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.twispan.create_encapsulated.client.slugma_burner.*;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import com.twispan.create_encapsulated.util.BlockPokemonState;
import com.twispan.create_encapsulated.util.slugma_burner.SlugmaBurnerHelperClient;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;

import java.util.HashSet;
import java.util.Set;

public class AnimatedSlugmaBurner extends AnimatedKinetics {

    private BlazeBurnerBlock.HeatLevel heatLevel;
    private final BlockPokemonState state = new BlockPokemonState();

    public void withHeat(BlazeBurnerBlock.HeatLevel level) {
        this.heatLevel = level;
    }

    @Override
    public void draw(GuiGraphics g, int xOffset, int yOffset) {
        PoseStack ms = g.pose();
        ms.pushPose();
        ms.translate(xOffset, yOffset, 200);
        ms.mulPose(Axis.XP.rotationDegrees(-15.5f));
        ms.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 23;

        blockElement(ModBlocks.SLUGMA_BURNER.get().defaultBlockState()).atLocal(0, 1.65, 0)
                .scale(scale)
                .render(g);

        ClientLevel level = Minecraft.getInstance().level;

        Species species = PokemonSpecies.getByName("slugma");

        boolean superHeated = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING;
        Set<String> aspects = new HashSet<>();
        aspects.add(superHeated ? "super_heated" : "burner");

        String pose = "active";

        if (level == null || species == null) return;

        PartialModel slugma_base = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING
                                ? SlugmaPartialModels.BASE_SUPERHEATED
                                : SlugmaPartialModels.BASE;
        
        blockElement(slugma_base).atLocal(0, 1.07, -0.2)
                .scale(scale + 7)
                .render(g);
        
        ms.scale(scale, -scale, scale);
        ms.translate(0, -1.8, 0);

        SpriteShiftEntry spriteShift = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING
                                ? SlugmaSpriteShifts.SUPER_SLUGMA_BURNER_FLAME
                                : null;

        if (spriteShift != null) {
            float spriteWidth = spriteShift.getTarget()
                    .getU1()
                    - spriteShift.getTarget()
                    .getU0();

            float spriteHeight = spriteShift.getTarget()
                    .getV1()
                    - spriteShift.getTarget()
                    .getV0();

            float time = AnimationTickHolder.getRenderTime(Minecraft.getInstance().level);
            float speed = 1 / 32f + 1 / 64f * heatLevel.ordinal();

            double vScroll = speed * time;
            vScroll = vScroll - Math.floor(vScroll);
            vScroll = vScroll * spriteHeight / 2;

            double uScroll = speed * time / 2;
            uScroll = uScroll - Math.floor(uScroll);
            uScroll = uScroll * spriteWidth / 2;

            CachedBuffers.partial(AllPartialModels.BLAZE_BURNER_FLAME, Blocks.AIR.defaultBlockState())
                    .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll)
                    .light(LightTexture.FULL_BRIGHT)
                    .renderInto(ms, g.bufferSource().getBuffer(RenderType.cutoutMipped()));
        }

        SlugmaBurnerHelperClient.drawPokemon(level, species, aspects, pose, ms, state,
                g.bufferSource(), 180f, 180f, 0f, -0.3f, 0.2f,
                AnimationTickHolder.getPartialTicks(level), LightTexture.FULL_BRIGHT);

        g.flush();

        ms.popPose();
    }
}
