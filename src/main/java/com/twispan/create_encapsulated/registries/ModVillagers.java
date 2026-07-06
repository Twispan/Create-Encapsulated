package com.twispan.create_encapsulated.registries;

import com.google.common.collect.ImmutableSet;
import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.registries.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, CreateEncapsulated.MODID);
    public static DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, CreateEncapsulated.MODID);

    public static Holder<PoiType> CARPENTER_POI = POI_TYPES.register("carpenter_poi",
            () -> new PoiType(ImmutableSet.copyOf
                    (ModBlocks.CARVING_TABLE.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static Holder<VillagerProfession> CARPENTER = VILLAGER_PROFESSIONS.register("carpenter",
            () -> new VillagerProfession("carpenter",
                    holder -> holder.value() == CARPENTER_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == CARPENTER_POI.value(),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.AXE_STRIP));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
