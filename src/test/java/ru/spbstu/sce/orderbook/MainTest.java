package ru.spbstu.sce.orderbook;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
@SpringBootTest
public class MainTest {
    private OrderBook market;

    @Test
    public void addNewBid(){
        market = new OrderBook("BTCUSDT");
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(69.0, 1);
        assertTrue(market.getBidMap().containsKey(69.0));
    }

}