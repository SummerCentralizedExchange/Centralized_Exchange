package ru.spbstu.sce.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@Embeddable
public class UserBalanceId implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "coin_id")
    private long coinId;

    public UserBalanceId() {
    }

    public UserBalanceId(long userId, long coinId) {
        this.userId = userId;
        this.coinId = coinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalanceId that = (UserBalanceId) o;
        return userId == that.userId && coinId == that.coinId;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new long[]{userId, coinId});
    }
}
