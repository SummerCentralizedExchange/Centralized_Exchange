package ru.spbstu.sce.orderbook;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MarketList {
    private final Logger logger = LoggerFactory.getLogger(MarketList.class);

    private Map<String, OrderBook> orderBooks;

    public MarketList() {
        orderBooks = new HashMap<>();
        orderBooks.put("Test", new OrderBook("Test"));
    }

    public void add(String symbol) {
        logger.info("MarketList Add coin: " + symbol);

        orderBooks.putIfAbsent(symbol, new OrderBook(symbol));
    }

    public void bidAdd(OrderItem bid) {
        logger.info("MarketList AddBid name : {}", bid.getSymbol());
        logger.info("MarketList AddBid price : {}", bid.getPrice());
        logger.info("MarketList AddBid quantity : {}", bid.getQuantity());

        if (orderBooks.containsKey(bid.getSymbol())) {
            OrderBook orderBook = orderBooks.get(bid.getSymbol());
            orderBook.addBid(bid.getPrice(), bid.getQuantity());
        }
    }

    public void offerAdd(OrderItem offer) {
        logger.info("MarketList offerAdd name : {}", offer.getSymbol());
        logger.info("MarketList offerAdd price : {}", offer.getPrice());
        logger.info("MarketList offerAdd quantity : {}", offer.getQuantity());

        if (orderBooks.containsKey(offer.getSymbol())) {
            OrderBook orderBook = orderBooks.get(offer.getSymbol());
            orderBook.addOffer(offer.getPrice(), offer.getQuantity());
        }
    }

    public Map<BigDecimal, List<Order>> getBidMap(String symbol) {
        logger.info("MarketList GetBidMap name : {}", symbol);

        if (orderBooks.containsKey(symbol)) {
            OrderBook orderBook = orderBooks.get(symbol);
            Map<BigDecimal, List<Order>> bidMap = orderBook.getBidMap();
            return bidMap;
        }

        return null;
    }

    public Map<BigDecimal, List<Order>> getOfferMap(String symbol) {
        logger.info("MarketList GetOfferMap name : {}", symbol);

        if (orderBooks.containsKey(symbol)) {

            OrderBook orderBook = orderBooks.get(symbol);
            Map<BigDecimal, List<Order>> offerMap = orderBook.getOfferMap();
            return offerMap;
        }

        return null;
    }

    public List<Order> getOrders(String coinName) {
        if (orderBooks.containsKey(coinName)) {
            OrderBook orderBook = orderBooks.get(coinName);
            List<Order> allOrders = new ArrayList<>();

            for (List<Order> orders : orderBook.getBidMap().values()) {
                allOrders.addAll(orders);
            }

            for (List<Order> orders : orderBook.getOfferMap().values()) {
                allOrders.addAll(orders);
            }

            return allOrders;
        }

        return new ArrayList<>();
    }

    public boolean isValidSymbol(String symbol) {
        return orderBooks.containsKey(symbol);
    }
}
