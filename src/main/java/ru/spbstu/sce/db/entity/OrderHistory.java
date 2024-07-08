package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.spbstu.sce.db.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistory_id;

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
}
