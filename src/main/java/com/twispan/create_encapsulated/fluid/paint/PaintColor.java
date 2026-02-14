package com.twispan.create_encapsulated.fluid.paint;

public enum PaintColor {
    RED(0xFF5A3A),
    BLUE(0x3789FD),
    GREEN(0x0FB009),
    YELLOW(0xFDDE1A),
    PINK(0xE89DC9),
    PURPLE(0x9B5DCC),
    MAGENTA(0xD673D0),
    LIME(0x84F725),
    CYAN(0x4CBCC0),
    LIGHTBLUE(0x72BEFF),
    ORANGE(0xFEAC3A),
    BROWN(0xB9754E),
    LIGHTGRAY(0xAAAAB0),
    GRAY(0x808086),
    BLACK(0x50597C),
    WHITE(-1);

    private final int color;

    PaintColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}
