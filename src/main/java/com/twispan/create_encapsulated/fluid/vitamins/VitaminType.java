package com.twispan.create_encapsulated.fluid.vitamins;

public enum VitaminType {
    CALCIUM(0xCC7025),
    CARBOS(0x8ADCDC),
    HP_UP(0x359D35),
    IRON(0x9AA400),
    PP_UP(0xB360A8),
    PP_MAX(0xF4EA75),
    PROTEIN(0xB44548),
    ZINC(0xB57A04);

    private final int type;

    VitaminType (int type) { this.type = type; }

    public int getType() { return type; }
}
