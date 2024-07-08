package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.spbstu.sce.db.entity.user.User;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "user_balance")
public class UserBalance {

    @EmbeddedId
    private UserBalanceId id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("coin_id")
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(precision = 38, scale = 10)
    private BigDecimal amount;

}
