package ru.spbstu.sce.db.entity.user.wallet;

import java.math.BigDecimal;

public class CoinWalletBalance {
    private final String coin;
    private final BigDecimal walletBalance;

    public CoinWalletBalance(String coin, BigDecimal walletBalance) {
        this.coin = coin;
        this.walletBalance = walletBalance;
    }

    public String getCoin() {
        return coin;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }
}
