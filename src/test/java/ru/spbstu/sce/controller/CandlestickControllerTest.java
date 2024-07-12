package ru.spbstu.sce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.sce.db.entity.coin.Coin;
import ru.spbstu.sce.db.entity.OrderHistory;
import ru.spbstu.sce.db.entity.user.User;
import ru.spbstu.sce.db.repositories.CoinRepository;
import ru.spbstu.sce.db.repositories.OrderHistoryRepository;
import ru.spbstu.sce.db.repositories.UserRepository;
import ru.spbstu.sce.orderbook.Candlestick;
import ru.spbstu.sce.service.CandlestickService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CandlestickControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandlestickService candlestickService;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Coin baseCoin;
    private Coin quoteCoin;

    @BeforeEach
    public void setUp() {
        orderHistoryRepository.deleteAll();
        coinRepository.deleteAll();
        userRepository.deleteAll();

        baseCoin = new Coin("BTC");
        quoteCoin = new Coin("USD");
        coinRepository.saveAll(Arrays.asList(baseCoin, quoteCoin));

        user = new User("testUser", "password");
        userRepository.save(user);

        orderHistoryRepository.saveAll(Arrays.asList(
                new OrderHistory(new BigDecimal("100.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 10, 0), "BUY", baseCoin, quoteCoin, user),
                new OrderHistory(new BigDecimal("150.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 11, 0), "BUY", baseCoin, quoteCoin, user),
                new OrderHistory(new BigDecimal("90.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 12, 0), "SELL", baseCoin, quoteCoin, user),
                new OrderHistory(new BigDecimal("120.00"), new BigDecimal("1.00"), LocalDateTime.of(2023, 1, 1, 13, 0), "SELL", baseCoin, quoteCoin, user)
        ));
    }

    @Test
    public void testGetCandlesticks() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        Candlestick candlestick1 = new Candlestick(
                LocalDate.of(2023, 1, 1),
                new BigDecimal("100.0"),
                new BigDecimal("150.0"),
                new BigDecimal("90.0"),
                new BigDecimal("120.0")
        );

        List<Candlestick> candlesticks = Arrays.asList(candlestick1);

        given(candlestickService.getCandlesticks(startDate, endDate)).willReturn(candlesticks);

        mockMvc.perform(get("/candlesticks")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-31"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(candlesticks)));
    }
}
