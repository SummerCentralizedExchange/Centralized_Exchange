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
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(A_PRICE, BigDecimal.valueOf(12));
        assertTrue(market.getBidMap().containsKey(A_PRICE));

    }

    @Test
    public void addDuplicateKey() {
        assertTrue(market.getBidMap().isEmpty());
        market.addBid(A_PRICE, BigDecimal.valueOf(12));
        market.addBid(A_PRICE, BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(12), market.getBidMap().get(A_PRICE).get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(1), market.getBidMap().get(A_PRICE).get(1).getQuantity());
    }

    @Test
    public void addOffer() {
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(A_PRICE, BigDecimal.valueOf(12));
        assertTrue(market.getOfferMap().containsKey(A_PRICE));

    }

    @Test
    public void addOfferDuplicateKey() {
        assertTrue(market.getOfferMap().isEmpty());
        market.addOffer(A_PRICE, BigDecimal.valueOf(12));
        market.addOffer(A_PRICE, BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(12), market.getOfferMap().get(A_PRICE).get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(1), market.getOfferMap().get(A_PRICE).get(1).getQuantity());
    }

    @Test
    public void ReducingNumberOffers() {
        market.addOffer(A_PRICE, BigDecimal.valueOf(6));
        market.addBid(A_PRICE, BigDecimal.valueOf(9));
        market.matchOrders();
        assertEquals(BigDecimal.valueOf(3), market.getBidMap().get(A_PRICE).get(0).getQuantity());
        assertTrue(market.getOfferMap().get(A_PRICE).isEmpty());
    }
}