package ru.spbstu.sce.orderbook;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@SpringBootTest
public class MainTest {
    private OrderBook market;

    @Test
    public void addNewBid(){
        market = new OrderBook("BTCUSDT");
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(BigDecimal.valueOf(69.0), BigDecimal.valueOf(1));
        assertTrue(market.getBidMap().containsKey(BigDecimal.valueOf(69.0)));
    }

}