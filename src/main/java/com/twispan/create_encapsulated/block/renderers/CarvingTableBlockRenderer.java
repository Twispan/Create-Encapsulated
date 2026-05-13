package com.twispan.create_encapsulated.block.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.twispan.create_encapsulated.block.entities.CarvingTableBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CarvingTableBlockRenderer extends SafeBlockEntityRenderer<CarvingTableBlockEntity> {
    public void CarvingTableRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    protected void renderSafe(CarvingTableBlockEntity be,
                              float partialTicks,
                              PoseStack poseStack,
                              MultiBufferSource buffer,
                              int light,
                              int overlay)
    {

    }
}
