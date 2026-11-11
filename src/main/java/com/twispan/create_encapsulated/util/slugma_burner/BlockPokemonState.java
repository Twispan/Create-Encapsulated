package com.twispan.create_encapsulated.util.slugma_burner;

import com.cobblemon.mod.common.api.scheduling.SchedulingTracker;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPokemonState extends PosableState {
    public float totalPartialTicks = 0f;

    private final SchedulingTracker schedulingTracker = new SchedulingTracker();

    public BlockPokemonState() {
        this(-1, 0f);
    }

    public BlockPokemonState(int startAge, float startPartialTicks) {
        setAge(startAge >= 0 ? startAge : (int) (200F * Math.random()));
        setCurrentPartialTicks(startAge > 0 ? startPartialTicks : 0);
    }

    @Override
    public @Nullable Entity getEntity() {
        return null;
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        setCurrentPartialTicks(getCurrentPartialTicks() + partialTicks / 2);
        totalPartialTicks += partialTicks / 2;
    }

    @Override
    public @NotNull SchedulingTracker getSchedulingTracker() {
        return schedulingTracker;
    }
}
