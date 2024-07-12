package ru.spbstu.sce.db.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import ru.spbstu.sce.db.entity.coin.Coin;

import java.math.BigDecimal;

@Entity
@Data
public class UserBalance {

    @EmbeddedId
    private UserBalanceId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("coinId")
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Column(precision = 38, scale = 10)
    private BigDecimal amount;

}
