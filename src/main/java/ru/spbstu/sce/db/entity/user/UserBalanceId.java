package ru.spbstu.sce.db.entity.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@Embeddable
public class UserBalanceId implements Serializable {

    private long userId;

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
