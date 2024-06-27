package ru.kevgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderBookTest {
    private OrderBook market;

    @Before
    public void init() {
        market = new OrderBook("BTC");
    }

    @Test
    public void addBid() {
        init();
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(12000, 12);
        assertTrue(market.getBidMap().containsKey(12000.0));

    }

    @Test
    public void addDuplicateKey(){
        init();
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(12000, 12);
        market.addBid(12000, 1);

        assertEquals(12, market.getBidMap().get(12000.0).get(0).getQuantity());
        assertEquals(1, market.getBidMap().get(12000.0).get(1).getQuantity());
    }

    @Test
    public void addOffer() {
        init();
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(12000, 12);
        assertTrue(market.getOfferMap().containsKey(12000.0));

    }

    @Test
    public void addOfferDuplicateKey() {
        init();
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(12000, 12);
        market.addOffer(12000, 1);
        assertEquals(12, market.getOfferMap().get(12000.0).get(0).getQuantity());
        assertEquals(1, market.getOfferMap().get(12000.0).get(1).getQuantity());
    }

    @Test
    public void ReducingNumberOffers(){
        init();
        market.addOffer(12.0, 6);
        market.addBid(12.0, 9);
        market.matchOrders();
        assertEquals(3, market.getBidMap().get(12.0).get(0).getQuantity());
        assertTrue(market.getOfferMap().get(12.0).isEmpty());
    }
}