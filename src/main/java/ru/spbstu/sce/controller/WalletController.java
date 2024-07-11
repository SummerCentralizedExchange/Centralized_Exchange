package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.accesscontrol.SecurityConfiguration;
import ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance;
import ru.spbstu.sce.service.WalletService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/account")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet-balance")
    public List<CoinWalletBalance> getWalletBalance(Principal principal, @RequestParam(value = "coin", required = false) String coin) {
        if (principal == null) {
            throw new IllegalStateException("getWalletBalance should be accessed with authentication. Check permits in" + SecurityConfiguration.class.getSimpleName());
        }
        List<String> symbols = (coin != null && !coin.isEmpty()) ? Arrays.asList(coin.split(",")) : Collections.emptyList();
        return walletService.getWalletBalance(principal, symbols);
    }
}
