package ru.spbstu.sce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spbstu.sce.orderbook.Candlestick;
import ru.spbstu.sce.service.CandlestickService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private ObjectMapper objectMapper;

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

        Candlestick candlestick2 = new Candlestick(
                LocalDate.of(2023, 1, 2),
                new BigDecimal("120.0"),
                new BigDecimal("160.0"),
                new BigDecimal("110.0"),
                new BigDecimal("130.0")
        );

        List<Candlestick> candlesticks = Arrays.asList(candlestick1, candlestick2);

        given(candlestickService.getCandlesticks(startDate, endDate)).willReturn(candlesticks);

        mockMvc.perform(get("/candlesticks")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-31"))
                .andExpect(status().isOk())

                .andExpect(content().json(objectMapper.writeValueAsString(candlesticks)));
    }
}