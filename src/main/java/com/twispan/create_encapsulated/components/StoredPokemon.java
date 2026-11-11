package com.twispan.create_encapsulated.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

public record StoredPokemon(CompoundTag pokemon, UUID owner, String ownerName) {
    public static final Codec<StoredPokemon> CODEC = RecordCodecBuilder.create(i -> i.group(
            CompoundTag.CODEC.fieldOf("pokemon").forGetter(StoredPokemon::pokemon),
            UUIDUtil.CODEC.fieldOf("owner").forGetter(StoredPokemon::owner),
            Codec.STRING.fieldOf("owner_name").forGetter(StoredPokemon::ownerName)
    ).apply(i, StoredPokemon::new));

    public static final StreamCodec<ByteBuf, StoredPokemon> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, StoredPokemon::pokemon,
            UUIDUtil.STREAM_CODEC, StoredPokemon::owner,
            ByteBufCodecs.STRING_UTF8, StoredPokemon::ownerName,
            StoredPokemon::new
    );
}
