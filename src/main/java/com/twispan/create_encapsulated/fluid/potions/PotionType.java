package com.twispan.create_encapsulated.fluid.potions;

public enum PotionType {
    POTION(0x916DB8),
    SUPER(0xCE7B6B),
    HYPER(0xE47AB9),
    MAX(0x7595FF),
    FULL_RESTORE(0x61A935);

    private final int type;

    PotionType(int type) { this.type = type; }

    public int getType() { return this.type; }
}
