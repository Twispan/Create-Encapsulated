package com.twispan.create_encapsulated.fluid.paint;

public enum PaintColor {
    RED(0xFF5A3A),
    BLUE(0x3789FD),
    GREEN(0x60C10D),
    YELLOW(0xF8D332),
    PINK(0xE89DC9),
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
