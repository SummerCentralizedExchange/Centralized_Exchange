package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.user.wallet.CoinWalletBalance;
import ru.spbstu.sce.user.wallet.WalletService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/account")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet-balance")
    public List<CoinWalletBalance> getWalletBalance(@RequestParam(value = "coin", required = false) String coin) {
        List<String> symbols = (coin != null && !coin.isEmpty()) ? Arrays.asList(coin.split(",")) : null;
        return walletService.getWalletBalance(symbols);

    }
}
