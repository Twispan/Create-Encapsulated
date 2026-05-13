package com.twispan.create_encapsulated.registries.blocks;

import com.twispan.create_encapsulated.CreateEncapsulated;
import com.twispan.create_encapsulated.block.entities.CarvingTableBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateEncapsulated.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CarvingTableBlockEntity>> CARVING_TABLE =
            BLOCK_ENTITIES.register("carving_table_be",
                    () -> BlockEntityType.Builder.of(
                            CarvingTableBlockEntity::new,
                            ModBlocks.CARVING_TABLE.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}