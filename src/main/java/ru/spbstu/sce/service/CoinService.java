package ru.spbstu.sce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.sce.db.entity.Coin.Coin;
import ru.spbstu.sce.db.repositories.CoinRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public List<String> getAllSymbols() {
        List<Coin> coins = coinRepository.findAll();
        List<String> symbols = new ArrayList<>();

        for (int i = 0; i < coins.size(); i++) {
            for (int j = 0; j < coins.size(); j++) {
                if (i != j) {
                    symbols.add(coins.get(i).getCoinName() + coins.get(j).getCoinName());
                }
            }
        }

        return symbols;
    }
}