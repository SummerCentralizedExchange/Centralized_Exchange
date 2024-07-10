package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;

public class Order {
    private BigDecimal price;
    public BigDecimal quantity;

    public Order(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.price + " " + this.quantity;
    }
}
