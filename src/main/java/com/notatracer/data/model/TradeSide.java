package com.notatracer.data.model;

import lombok.Data;

public @Data
class TradeSide {
    private String account;
    private BuySell side;
}
