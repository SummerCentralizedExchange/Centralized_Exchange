package ru.spbstu.sce.orderbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.spbstu.sce.db.entity.coin.Coin;
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

    @BeforeEach
    public void setUp() {
        orderHistoryRepository.deleteAll();
        coinRepository.deleteAll();
        userRepository.deleteAll();

        Coin baseCoin = new Coin();
        baseCoin.setCoinName("BTC");
        coinRepository.save(baseCoin);

        Coin quoteCoin = new Coin();
        quoteCoin.setCoinName("USD");
        coinRepository.save(quoteCoin);

        User user = new User("testUser", "password");
        userRepository.save(user);

        orderHistoryRepository.save(new OrderHistory(new BigDecimal("100.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 7, 10, 10, 0), "BUY", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("105.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 7, 10, 11, 0), "BUY", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("95.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 7, 10, 12, 0), "SELL", baseCoin, quoteCoin, user));
        orderHistoryRepository.save(new OrderHistory(new BigDecimal("110.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 7, 10, 13, 0), "SELL", baseCoin, quoteCoin, user));
    }

    @Test
    public void testGetCandlesticks() {
        LocalDate startDate = LocalDate.of(2023, 7, 10);
        LocalDate endDate = LocalDate.of(2023, 7, 10);

        List<Candlestick> candlesticks = service.getCandlesticks(startDate, endDate);

        assertEquals(1, candlesticks.size());

        Candlestick candlestick = candlesticks.get(0);
        assertEquals(LocalDate.of(2023, 7, 10), candlestick.getTime());
        assertEquals(0, candlestick.getOpen().compareTo(new BigDecimal("100.00")));
        assertEquals(0, candlestick.getHigh().compareTo(new BigDecimal("110.00")));
        assertEquals(0, candlestick.getLow().compareTo(new BigDecimal("95.00")));
        assertEquals(0, candlestick.getClose().compareTo(new BigDecimal("110.00")));

    }
}
