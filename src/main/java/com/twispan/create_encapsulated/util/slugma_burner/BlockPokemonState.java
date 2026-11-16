package com.twispan.create_encapsulated.util.slugma_burner;

import com.cobblemon.mod.common.api.scheduling.SchedulingTracker;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPokemonState extends PosableState {
    public float totalPartialTicks = 0f;
    private float lastRenderTime = Float.NaN;

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

    public void advance(float absoluteTicks) {
        if (Float.isNaN(lastRenderTime)) { lastRenderTime = absoluteTicks; return; }
        float delta = absoluteTicks - lastRenderTime;
        if (delta <= 0f) return;
        lastRenderTime = absoluteTicks;
        if (delta > 40f) return;

        totalPartialTicks += delta;
        float currentPartial = getCurrentPartialTicks() + delta;
        int whole = (int) currentPartial;
        setAge(getAge() + whole);
        setCurrentPartialTicks(currentPartial - whole);
    }

    @Override
    public @NotNull SchedulingTracker getSchedulingTracker() {
        return schedulingTracker;
    }
}
