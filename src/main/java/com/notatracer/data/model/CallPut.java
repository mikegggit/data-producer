package com.notatracer.data.model;

public enum CallPut {
    Call('C'),
    Put('P');

    private final char code;

    CallPut(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
