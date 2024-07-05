package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
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

    private BigDecimal amount;

    public UserBalanceId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Coin getCoin() {
        return coin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setId(UserBalanceId id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
