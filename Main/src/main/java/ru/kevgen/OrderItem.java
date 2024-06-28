package ru.kevgen;

public class OrderItem {
    private String symbol;
    private double price;
    private int quantity;
    private String type;
    private String transactionType;

    public String getSymbolName() {
        return symbol;
    }

    public double getPrice() {
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

    public void setCoinName(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(double price) {
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
