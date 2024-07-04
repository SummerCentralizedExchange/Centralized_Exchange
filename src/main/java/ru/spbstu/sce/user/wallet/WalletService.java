package ru.spbstu.sce.user.wallet;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WalletService {

    public List<CoinWalletBalance> getWalletBalance(@NonNull List<String> symbols) {
        /** TO DO
         * There should be a logic for getting the wallet balance from a database or other source **/

        List<CoinWalletBalance> balances = new ArrayList<>();

        if (symbols.isEmpty()) {
            return new ArrayList<>();
        }

        for (String symbol : symbols) {
            if ("BTC".equals(symbol)) {
                balances.add(new CoinWalletBalance("BTC", new BigDecimal("0.5")));
            } else if ("ETH".equals(symbol)) {
                balances.add(new CoinWalletBalance("ETH", new BigDecimal("2.5")));
            } else if ("USDC".equals(symbol)) {
                balances.add(new CoinWalletBalance("USDC", new BigDecimal("100.0")));
            } else {
                balances.add(new CoinWalletBalance(symbol, BigDecimal.ZERO));
            }
        }

        return balances;
    }
}
