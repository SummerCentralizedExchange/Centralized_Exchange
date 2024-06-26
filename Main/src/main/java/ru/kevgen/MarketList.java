package ru.kevgen;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketList {
    private OrderBook market;
    private Map<String, OrderBook> orderBooks;

    public MarketList() {
        market = new OrderBook("Test");
        orderBooks = new HashMap<>();
        orderBooks.put("Test", market);
    }

    public void add(String coinName){
        if(!orderBooks.containsKey(coinName)){
            market = new OrderBook(coinName);
            orderBooks.put(coinName, market);
        }
    }

    public void bidAdd(OrderItem bid){
        if(orderBooks.containsKey(bid.getCoinName())){
            OrderBook orderBook = orderBooks.get(bid.getCoinName());
            orderBook.addBid(bid.getPrice(), bid.getQuantity());
        }
    }

    public void offerAdd(OrderItem offer){
        if(orderBooks.containsKey(offer.getCoinName())){
            OrderBook orderBook = orderBooks.get(offer.getCoinName());
            orderBook.addOffer(offer.getPrice(), offer.getQuantity());
        }
    }

    public Map<Double, List<Order>> getBidMap(OrderItem bid){
        if(orderBooks.containsKey(bid.getCoinName())){
            OrderBook orderBook = orderBooks.get(bid.getCoinName());
            Map<Double, List<Order>> bidMap = orderBook.getBidMap();
            return bidMap;
        }
        return null;
    }

    public Map<Double, List<Order>> getOfferMap(OrderItem offer){
        if(orderBooks.containsKey(offer.getCoinName())){
            OrderBook orderBook = orderBooks.get(offer.getCoinName());
            Map<Double, List<Order>> offerMap = orderBook.getOfferMap();
            return offerMap;
        }
        return null;
    }

    public List<Order> getOrders(String coinName){
        if(orderBooks.containsKey(coinName)){
            OrderBook orderBook = orderBooks.get(coinName);
            List<Order> allOrders = new ArrayList<>();
            for(List<Order> orders : orderBook.getBidMap().values()){
                allOrders.addAll(orders);
            }
            for(List<Order> orders : orderBook.getOfferMap().values()){
                allOrders.addAll(orders);
            }
            return allOrders;
        }
        return new ArrayList<>();
    }
}
