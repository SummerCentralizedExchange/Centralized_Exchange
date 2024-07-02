package ru.spbstu.sce.user.wallet;

import java.math.BigDecimal;

public class WalletBalanceResponse {
    private String symbol;
    private double walletBalance;

    public WalletBalanceResponse(String symbol, double walletBalance) {
        this.symbol = symbol;
        this.walletBalance = walletBalance;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getWalletBalance() {
        return walletBalance;
    }
}
