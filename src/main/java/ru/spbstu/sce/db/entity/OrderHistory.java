package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistory_id;

    private BigDecimal price;
    private BigDecimal quantity;
    private LocalDateTime timestamp;
    private String side;

    @ManyToOne
    @JoinColumn(name = "base_coin")
    private Coin baseCoin;

    @ManyToOne
    @JoinColumn(name = "quote_coin")
    private Coin quoteCoin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getOrderHistory_id() {
        return orderHistory_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSide() {
        return side;
    }

    public Coin getBaseCoin() {
        return baseCoin;
    }

    public Coin getQuoteCoin() {
        return quoteCoin;
    }

    public User getUser() {
        return user;
    }

    public void setOrderHistory_id(Long orderHistory_id) {
        this.orderHistory_id = orderHistory_id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public void setBaseCoin(Coin baseCoin) {
        this.baseCoin = baseCoin;
    }

    public void setQuoteCoin(Coin quoteCoin) {
        this.quoteCoin = quoteCoin;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
