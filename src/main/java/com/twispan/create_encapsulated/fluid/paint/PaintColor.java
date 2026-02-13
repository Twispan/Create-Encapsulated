package com.twispan.create_encapsulated.fluid.paint;

public enum PaintColor {
    RED(0xFF5A3A),
    BLUE(0x3789FD),
    GREEN(0x60C10D),
    YELLOW(0xF8D332),
    PINK(0xE89DC9),
    PURPLE(0xA35DCC),
    MAGENTA(0xD673D0),
    LIME(0x84F725),
    CYAN(0x4CBCC0),
    LIGHTBLUE(0x80B4FF),
    ORANGE(0xFEAC3A),
    BROWN(0xB9754E),
    LIGHTGRAY(0xB0B0B0),
    GRAY(0x898989),
    BLACK(0x656C8B),
    WHITE(-1);

    private final int color;

    PaintColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}
