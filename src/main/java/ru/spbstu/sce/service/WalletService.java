package ru.spbstu.sce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance;
import ru.spbstu.sce.db.repositories.UserBalanceRepository;

import java.security.Principal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    public List<CoinWalletBalance> getWalletBalance(@NonNull Principal principal, @NonNull List<String> symbols) {
        if (symbols.isEmpty()) {
            return userBalanceRepository.findByUserLogin(principal.getName());
        }

        return userBalanceRepository.findByUserLoginAndCoinNames(principal.getName(), symbols);
    }
}
