package ru.spbstu.sce.db.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserBalanceId implements Serializable {
    private Long user_id;
    private Long coin_id;

    public UserBalanceId() {}

    public UserBalanceId(Long user_id, Long coin_id) {
        this.user_id = user_id;
        this.coin_id = coin_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCoin_id() {
        return coin_id;
    }

    public void setCoin_id(Long coin_id) {
        this.coin_id = coin_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalanceId that = (UserBalanceId) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(coin_id, that.coin_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, coin_id);
    }
}
