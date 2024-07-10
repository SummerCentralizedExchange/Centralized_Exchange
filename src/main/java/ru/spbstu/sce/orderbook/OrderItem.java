package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;

public class OrderItem {
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
    private String type;
    private String side;

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
