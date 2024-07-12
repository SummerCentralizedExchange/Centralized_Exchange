package ru.spbstu.sce.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.sce.db.entity.coin.Coin;
import ru.spbstu.sce.db.repositories.CoinRepository;
import ru.spbstu.sce.db.repositories.OrderHistoryRepository;
import ru.spbstu.sce.db.repositories.UserBalanceRepository;
import ru.spbstu.sce.db.repositories.UserRepository;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CoinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @BeforeEach
    public void setUp() {
        userBalanceRepository.deleteAll();
        orderHistoryRepository.deleteAll();
        userRepository.deleteAll();
        coinRepository.deleteAll();
    }

    @Test
    public void getSymbols_ShouldReturnSymbolPairs() throws Exception {
        coinRepository.saveAll(Arrays.asList(
                new Coin("BTC"),
                new Coin("ETH"),
                new Coin("USDT")
        ));

        mockMvc.perform(get("/symbols"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"symbols\":[\"BTCETH\"," +
                        "\"BTCUSDT\"," +
                        "\"ETHBTC\"," +
                        "\"ETHUSDT\"," +
                        "\"USDTBTC\"," +
                        "\"USDTETH\"]}"));
    }
}