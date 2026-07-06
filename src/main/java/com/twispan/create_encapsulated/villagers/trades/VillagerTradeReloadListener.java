package com.twispan.create_encapsulated.villagers.trades;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;

import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class VillagerTradeReloadListener extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(VillagerTradeReloadListener.class);
    private static final FileToIdConverter LISTER = FileToIdConverter.json("villager_trades");

    private static final Map<ResourceLocation, Map<Integer, List<TradeDefinition>>> TRADES = new HashMap<>();

    public VillagerTradeReloadListener() {
        super(new Gson(), "villager_trades");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        TRADES.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation fileId = entry.getKey();

            TradeDefinition.CODEC.parse(JsonOps.INSTANCE, entry.getValue())
                    .resultOrPartial(error -> LOGGER.error("Couldn't parse villager trade '{}':{}", fileId, error))
                    .ifPresent(tradeDefinition ->
                            TRADES.computeIfAbsent(tradeDefinition.profession(), key -> new HashMap<>())
                                    .computeIfAbsent(tradeDefinition.level(), key -> new ArrayList<>())
                                    .add(tradeDefinition)
                    );
        }

        LOGGER.info("Loaded {} custom villager definitions", object.size());
    }

    public static List<TradeDefinition> getTrades(ResourceLocation profession, int level) {
        return TRADES.getOrDefault(profession, Map.of()).getOrDefault(level, List.of());
    }
}
