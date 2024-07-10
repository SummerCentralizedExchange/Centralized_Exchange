package ru.spbstu.sce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance;
import ru.spbstu.sce.db.repositories.UserBalanceRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    public List<CoinWalletBalance> getWalletBalance(Principal principal, @NonNull List<String> symbols) {
        if (symbols.isEmpty()) {
            return userBalanceRepository.findByUserLogin(principal.getName());
        }

        var map = userBalanceRepository.findByUserLoginAndCoinNames(principal.getName(), symbols)
                .stream()
                .collect(Collectors.toMap(CoinWalletBalance::getCoin, CoinWalletBalance::getWalletBalance));
        return symbols.stream().map(s -> new CoinWalletBalance(s, map.getOrDefault(s, BigDecimal.ZERO))).toList();
    }
}
