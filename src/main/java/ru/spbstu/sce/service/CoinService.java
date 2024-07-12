package ru.spbstu.sce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.sce.db.entity.coin.Coin;
import ru.spbstu.sce.db.repositories.CoinRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public List<String> getAllSymbols() {
        List<Coin> coins = coinRepository.findAll();

        return coins.stream()
                .flatMap(coin1 -> coins.stream()
                        .filter(coin2 -> !coin1.equals(coin2))
                        .map(coin2 -> coin1.getCoinName() + coin2.getCoinName()))
                .collect(Collectors.toList());
    }

}