package com.notatracer.data.model;


import lombok.Data;

public @Data class Strike {
    private String symbol;
    private String strikePrice;
    private String expirationDate;
    private CallPut callPut;

    public Strike() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CallPut getCallPut() {
        return callPut;
    }

    public void setCallPut(CallPut callPut) {
        this.callPut = callPut;
    }
}
