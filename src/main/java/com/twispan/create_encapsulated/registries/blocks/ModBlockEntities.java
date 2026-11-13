package com.twispan.create_encapsulated.registries.blocks;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.entity.SlugmaBurnerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateEncapsulated.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SlugmaBurnerBlockEntity>> SLUGMA_BURNER =
            BLOCK_ENTITIES.register("slugma_burner_be",
                    () -> BlockEntityType.Builder.of(
                            SlugmaBurnerBlockEntity::new,
                            ModBlocks.SLUGMA_BURNER.get()
                            ).build(null));
}
