 package ru.spbstu.sce.orderbook;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderBookTest {
    public static final BigDecimal A_PRICE = BigDecimal.valueOf(12000);
    private OrderBook market;

    @Before
    public void init() {
        market = new OrderBook("BTCUSDT");
    }

    @Test
    public void addBid() {
        init();
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(A_PRICE, 12);
        assertTrue(market.getBidMap().containsKey(A_PRICE));

    }

    @Test
    public void addDuplicateKey(){
        init();
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(A_PRICE, 12);
        market.addBid(A_PRICE, 1);

        assertEquals(12, market.getBidMap().get(A_PRICE).get(0).getQuantity());
        assertEquals(1, market.getBidMap().get(A_PRICE).get(1).getQuantity());
    }

    @Test
    public void addOffer() {
        init();
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(A_PRICE, 12);
        assertTrue(market.getOfferMap().containsKey(A_PRICE));

    }

    @Test
    public void addOfferDuplicateKey() {
        init();
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(A_PRICE, 12);
        market.addOffer(A_PRICE, 1);
        assertEquals(12, market.getOfferMap().get(A_PRICE).get(0).getQuantity());
        assertEquals(1, market.getOfferMap().get(A_PRICE).get(1).getQuantity());
    }

    @Test
    public void ReducingNumberOffers(){
        init();
        market.addOffer(A_PRICE, 6);
        market.addBid(A_PRICE, 9);
        market.matchOrders();
        assertEquals(3, market.getBidMap().get(A_PRICE).get(0).getQuantity());
        assertTrue(market.getOfferMap().get(A_PRICE).isEmpty());
    }
}