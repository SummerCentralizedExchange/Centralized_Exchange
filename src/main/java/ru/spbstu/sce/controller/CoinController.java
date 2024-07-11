package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.sce.db.entity.Coin.SymbolsResponse;
import ru.spbstu.sce.service.CoinService;

@RestController
@RequestMapping("/symbols")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @GetMapping
    public SymbolsResponse getSymbols() {
        return new SymbolsResponse(coinService.getAllSymbols());
    }
}