package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;

public class OrderItem {
    private String symbol;
    private BigDecimal price;
    private int quantity;
    private String type;
    private String transactionType;

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
