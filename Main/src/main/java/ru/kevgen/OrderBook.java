package ru.kevgen;

import java.util.*;

public class OrderBook {
    private String coinName;

    private Map<Double, List<Order>> bidMap;
    private Map<Double, List<Order>> offerMap;

    private Queue<Double> bidMaxPriceList;
    private Queue<Double> offerMinPriceList;

    public OrderBook(String coinName) {
        this.coinName = coinName;

        bidMap = new HashMap<>();
        offerMap = new HashMap<>();

        bidMaxPriceList = new PriorityQueue<>(30 , Collections.reverseOrder());
        offerMinPriceList = new PriorityQueue<>();
    }

    public void addBid(double price, int quantity) {
        List<Order> orders = getOrders(bidMap, price);
        Order order = new Order(price, quantity);
        orders.add(order);
        bidMap.put(order.getPrice(), orders);
        bidMaxPriceList.add(price);
        matchOrders();
    }

    public void addOffer(double price, int quantity) {
        List<Order> orders = getOrders(offerMap, price);
        Order order = new Order(price, quantity);
        orders.add(order);
        offerMap.put(order.getPrice(), orders);
        offerMinPriceList.add(order.getPrice());
        matchOrders();
    }

    private void matchOrders() {
        boolean stop = false;
        while (!stop){
            Double highestBid = bidMaxPriceList.peek();
            Double lowestOffer = offerMinPriceList.peek();

            if(lowestOffer == null || highestBid == null || lowestOffer > highestBid){
                stop = true;
            }else {
                List<Order> bidOrders = bidMap.get(highestBid);
                List<Order> offerOrders = offerMap.get(lowestOffer);

                Order bidOrder = bidOrders.get(0);
                Order offerOrder = offerOrders.get(0);

                int bidQuantity = bidOrder.getQuantity();
                int offerQuantity = offerOrder.getQuantity();

                if(bidQuantity > offerQuantity){
                    System.out.println(successfulTrade(offerQuantity, lowestOffer));

                    // Decrement quantity in bid
                    bidOrder.setQuantity(bidQuantity - offerQuantity);

                    // Close previous offer
                    offerOrders.remove(0);
                    if(offerOrders.isEmpty()){
                        offerMinPriceList.remove();
                    }
                }else if(offerQuantity > bidQuantity){
                    System.out.println(successfulTrade(bidQuantity, highestBid));

                    // Decrement quantity in bid
                    offerOrder.setQuantity(offerQuantity - bidQuantity);

                    // Close previous bid
                    bidOrders.remove(0);
                    if(bidOrders.isEmpty()){
                        bidMaxPriceList.remove();
                    }
                }else{
                    System.out.println(successfulTrade(offerQuantity, highestBid));
                    bidOrders.remove(0);
                    offerOrders.remove(0);

                    if(bidOrders.isEmpty()){
                        bidMaxPriceList.remove();
                    }

                    if(offerOrders.isEmpty()){
                        offerMinPriceList.remove();
                    }
                }
            }
        }
    }

    private String successfulTrade(int offerQuantity, Double lowestOffer) {
        return offerQuantity + " share traded for " + lowestOffer + " per share.";
    }

    private List<Order> getOrders(Map<Double, List<Order>> hasmap, Double price){
        return hasmap.getOrDefault(price, new ArrayList<>());
    }

    public Map<Double, List<Order>> getBidMap() {
        return bidMap;
    }

    public Map<Double, List<Order>> getOfferMap() {
        return offerMap;
    }

    public void openMarket(){
        System.out.println("Market opens.");
    }

    public void closeMarket(){
        System.out.println("Market close.");
        printFailedTrades(bidMap, "Bid for ");
        printFailedTrades(offerMap, "Offer of ");
    }

    private void printFailedTrades(Map<Double, List<Order>> hasmap, String coin){
        for (Map.Entry<Double, List<Order>> entry : hasmap.entrySet()){
            for (Order order : entry.getValue()){
                System.out.println(coin + order.getQuantity() + "shares for " + order.getPrice());
            }
        }
    }
}
