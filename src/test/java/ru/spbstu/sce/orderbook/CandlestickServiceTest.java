package ru.spbstu.sce.orderbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spbstu.sce.db.entity.Coin;
import ru.spbstu.sce.db.entity.OrderHistory;
import ru.spbstu.sce.db.entity.user.User;
import ru.spbstu.sce.db.repositories.CoinRepository;
import ru.spbstu.sce.db.repositories.OrderHistoryRepository;
import ru.spbstu.sce.db.repositories.UserRepository;
import ru.spbstu.sce.service.CandlestickService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CandlestickServiceTest {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandlestickService service;

    private Coin baseCoin;
    private Coin quoteCoin;
    private User user;

    @BeforeEach
    public void setUp() {
        orderHistoryRepository.deleteAll();
        coinRepository.deleteAll();
        userRepository.deleteAll();

        baseCoin = new Coin();
        baseCoin.setCoinName("BTC");
        coinRepository.save(baseCoin);

        quoteCoin = new Coin();
        quoteCoin.setCoinName("USD");
        coinRepository.save(quoteCoin);

        user = new User("testUser", "password");
        userRepository.save(user);

        orderHistoryRepository.save(new OrderHistory(new BigDecimal("100.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 10, 0), "BUY", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("105.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 11, 0), "BUY", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("95.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 12, 0), "SELL", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("110.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 13, 0), "SELL", baseCoin, quoteCoin, user));
    }



    @Test
    public void testGetCandlesticks() {
        LocalDate startDate = LocalDate.of(2024, 7, 10);
        LocalDate endDate = LocalDate.of(2024, 7, 10);

        List<Candlestick> candlesticks = service.getCandlesticks(startDate, endDate);

        assertEquals(0, candlesticks.size());

        Candlestick candlestick = candlesticks.get(0);
        assertEquals(LocalDate.of(2024, 7, 10), candlestick.getTime());
        assertEquals(new BigDecimal("100.00"), candlestick.getOpen());
        assertEquals(new BigDecimal("110.00"), candlestick.getHigh());
        assertEquals(new BigDecimal("95.00"), candlestick.getLow());
        assertEquals(new BigDecimal("110.00"), candlestick.getClose());
    }
}