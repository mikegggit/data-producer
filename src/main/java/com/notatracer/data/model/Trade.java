package com.notatracer.data.model;

import lombok.Data;

public @Data
class Trade {
    private Long id;
    private Integer tradeSequenceNumber;
    private Integer quantity;
    private String tradePrice;

    private Strike strike;
    private TradeSide buySide;
    private TradeSide sellSide;

}
