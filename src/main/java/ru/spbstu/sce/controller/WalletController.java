package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.user.wallet.WalletBalanceResponse;
import ru.spbstu.sce.user.wallet.WalletService;

import java.util.List;

@RestController
@RequestMapping("/account")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/wallet-balance")
    public List<WalletBalanceResponse> getWalletBalance(@RequestParam(value = "symbol", required = false) String symbol) {
        return walletService.getWalletBalance(symbol);
    }
}
