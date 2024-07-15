package ru.spbstu.sce.orderbook;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItem {
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private String side;
    private String type;
    private String marketUnit;

    public OrderItem() {}

    public OrderItem(String symbol, BigDecimal price, BigDecimal quantity, String side, String type, String marketUnit) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.type = type;
        this.marketUnit = marketUnit;
    }

    @Override
    public String toString() {
        return this.symbol + " " + this.price + " " + this.quantity + " " + this.side + " " + this.type + " " + this.marketUnit;
    }
}
