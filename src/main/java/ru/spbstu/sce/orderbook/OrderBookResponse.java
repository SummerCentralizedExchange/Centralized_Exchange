package ru.spbstu.sce.orderbook;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.List;

public record OrderBookResponse(String symbol, List<Position> bids, List<Position> asks, long timestampMs) {
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public record Position(
            /*[0]*/ BigDecimal price,
            /*[1]*/ BigDecimal size) {}
}
