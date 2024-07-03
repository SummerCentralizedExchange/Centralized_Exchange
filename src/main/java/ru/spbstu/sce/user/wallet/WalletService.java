package ru.spbstu.sce.user.wallet;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    public List<CoinWalletBalance> getWalletBalance(List<String> symbols) {
        /** TO DO
         * There should be a logic for getting the wallet balance from a database or other source **/
        List<CoinWalletBalance> balances = new ArrayList<>();

        //simple example
        if (symbols == null || symbols.isEmpty()) {
            balances.add(new CoinWalletBalance("BTCUSDT", new BigDecimal("0.5")));
            balances.add(new CoinWalletBalance("ETHUSDT", new BigDecimal("2.5")));
            balances.add(new CoinWalletBalance("USDC", new BigDecimal("100.0")));
        } else {
            for (String symbol : symbols) {
                if ("BTCUSDT".equals(symbol)) {
                    balances.add(new CoinWalletBalance("BTCUSDT", new BigDecimal("0.5")));
                } else if ("ETHUSDT".equals(symbol)) {
                    balances.add(new CoinWalletBalance("ETHUSDT", new BigDecimal("2.5")));
                } else if ("USDC".equals(symbol)) {
                    balances.add(new CoinWalletBalance("USDC", new BigDecimal("100.0")));
                }
            }
        }
        return balances;
    }
}
