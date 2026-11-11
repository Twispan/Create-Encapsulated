package com.twispan.create_encapsulated.registries;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.components.StoredPokemon;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CreateEncapsulated.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StoredPokemon>> POKEMON =
            DATA_COMPONENTS.register("pokemon", () -> DataComponentType.<StoredPokemon>builder()
                    .persistent(StoredPokemon.CODEC)
                    .networkSynchronized(ByteBufCodecs.fromCodec(StoredPokemon.CODEC))
                    .build());
}
