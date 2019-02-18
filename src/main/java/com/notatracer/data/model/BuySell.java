package com.notatracer.data.model;

public enum BuySell {
    Buy('B'), Sell('S');

    private final char code;

    BuySell(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
