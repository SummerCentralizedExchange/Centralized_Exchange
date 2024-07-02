package ru.spbstu.sce.user.wallet;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    public List<WalletBalanceResponse> getWalletBalance(String symbol) {
        /** TO DO
         * There should be a logic for getting the wallet balance from a database or other source **/
        List<WalletBalanceResponse> balances = new ArrayList<>();

        //simple example
        if(symbol == null || symbol.isEmpty()) {
            balances.add(new WalletBalanceResponse("BTCUSDT", 0.5));
            balances.add(new WalletBalanceResponse("ETHUSDT", 2.5));
            balances.add(new WalletBalanceResponse("USDC", 1000.0));
        } else {
            String[] symbols = symbol.split(",");
            for(String coin : symbols) {
                if("BTCUSDT".equals(coin)) {
                    balances.add(new WalletBalanceResponse("BTCUSDT", 0.5));
                } else if ("ETHUSDT".equals(coin)) {
                    balances.add(new WalletBalanceResponse("ETHUSDT", 2.5));
                } else if ("USDC".equals(coin)) {
                    balances.add(new WalletBalanceResponse("USDC", 1000.0));
                }
            }
        }
        return balances;
    }
}
