package com.twispan.create_encapsulated.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CEConfig {

    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ALLOW_AXE_STRIPPING;
    public static final ModConfigSpec.BooleanValue ALLOW_WRENCH_UNCASING;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        ALLOW_AXE_STRIPPING = builder
                .comment("Allow vanilla axes to strip logs")
                .define("allowAxeStripping", false);

        ALLOW_WRENCH_UNCASING = builder
                .comment("Allow Create wrenches to remove casings")
                .define("allowWrenchUncasing", false);

        SPEC = builder.build();
    }
}