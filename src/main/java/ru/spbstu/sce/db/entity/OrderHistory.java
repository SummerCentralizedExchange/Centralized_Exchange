package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.spbstu.sce.db.entity.coin.Coin;
import ru.spbstu.sce.db.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderHistoryId;

    @Column(precision = 38, scale = 10)
    private BigDecimal price;

    @Column(precision = 38, scale = 10)
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

    public OrderHistory() {}

    public OrderHistory(BigDecimal price, BigDecimal quantity, LocalDateTime timestamp, String side, Coin baseCoin, Coin quoteCoin, User user) {
        this.price = price;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.side = side;
        this.baseCoin = baseCoin;
        this.quoteCoin = quoteCoin;
        this.user = user;
    }
}
