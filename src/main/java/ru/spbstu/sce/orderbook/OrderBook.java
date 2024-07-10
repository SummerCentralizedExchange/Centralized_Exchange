package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBook {
    private final Logger logger = LoggerFactory.getLogger(OrderBook.class);

    private final String symbol;

    private final Map<BigDecimal, List<Order>> bidMap;
    private final Map<BigDecimal, List<Order>> offerMap;

    private final Queue<BigDecimal> bidMaxPriceList;
    private final Queue<BigDecimal> offerMinPriceList;

    public OrderBook(String symbol) {
        this.symbol = symbol;

        bidMap = new TreeMap<>(Comparator.reverseOrder()); // Usually need bids in reverse price order
        offerMap = new TreeMap<>();

        bidMaxPriceList = new PriorityQueue<>();
        offerMinPriceList = new PriorityQueue<>();
    }

    public void addBid(BigDecimal price, BigDecimal quantity) {
        Order order = new Order(price, quantity);

        List<Order> orders = getOrders(bidMap, price);

        orders.add(order);
        bidMap.put(order.getPrice(), orders);
        bidMaxPriceList.add(price);
        matchOrders();

        logger.info("addBid: quantity {}", quantity);
        logger.info("addBid: price {}", price);
    }

    public void addOffer(BigDecimal price, BigDecimal quantity) {
        Order order = new Order(price, quantity);

        List<Order> orders = getOrders(offerMap, price);

        logger.info("addOffer: quantity {}", quantity);
        logger.info("addOffer: price {}", price);
        logger.info("offerMap: size {}", offerMap.size());

        orders.add(order);
        offerMap.put(order.getPrice(), orders);
        offerMinPriceList.add(order.getPrice());
        matchOrders();

        logger.info("offerMap after insert size : {}", orders.size());
    }

    public void matchOrders() {
        boolean stop = false;
        while (!stop) {
            BigDecimal highestBid = bidMaxPriceList.peek();
            BigDecimal lowestOffer = offerMinPriceList.peek();

            //Completion condition: there are no purchase or sale orders, or the lowest sale price is higher than the highest purchase price
            if (lowestOffer == null || highestBid == null || lowestOffer.compareTo(highestBid) > 0) {
                stop = true;
                logger.info("OrderBook matchOrders finished = true");
            }
            else {
                List<Order> bidOrders = bidMap.get(highestBid);
                List<Order> offerOrders = offerMap.get(lowestOffer);

                // Getting the first (oldest) application from each list
                Order bidOrder = bidOrders.get(0);
                Order offerOrder = offerOrders.get(0);

                BigDecimal bidQuantity = bidOrder.getQuantity();
                BigDecimal offerQuantity = offerOrder.getQuantity();

                // If the quantity in the purchase order is greater than the quantity in the sale order
                if (bidQuantity.compareTo(offerQuantity) > 0) {
                    logger.info("bidQuantity > offerQuantity");
                    logger.info(makeSuccessfulTradeInfo(offerQuantity, lowestOffer));

                    // Reducing the quantity in the purchase request
                    bidOrder.setQuantity(bidQuantity.subtract(offerQuantity));
                    logger.info("bidQuantity remaining quantity : {}", bidQuantity);

                    // Closing the previous sale request
                    offerOrders.remove(0);
                    if (offerOrders.isEmpty()) {
                        offerMinPriceList.remove();
                    }
                }
                // If the quantity in the sales order is greater than the quantity in the purchase order
                else if (offerQuantity.compareTo(bidQuantity) > 0) {
                    logger.info("bidQuantity < offerQuantity");
                    logger.info(makeSuccessfulTradeInfo(bidQuantity, highestBid));

                    offerOrder.setQuantity(offerQuantity.subtract(bidQuantity));
                    logger.info("offerQuantity remaining quantity : {}", offerQuantity);

                    bidOrders.remove(0);
                    if (bidOrders.isEmpty()) {
                        bidMaxPriceList.remove();
                    }
                }
                // If the quantity in the purchase and sale orders are equal
                else {
                    logger.info(makeSuccessfulTradeInfo(offerQuantity, highestBid));

                    // We delete closed purchase and sale orders
                    bidOrders.remove(0);
                    offerOrders.remove(0);

                    // We remove the price from the queue if all requests for this price are fulfilled
                    if (bidOrders.isEmpty()) {
                        bidMaxPriceList.remove();
                    }
                    if (offerOrders.isEmpty()) {
                        offerMinPriceList.remove();
                    }
                }
            }
        }
    }


    private String makeSuccessfulTradeInfo(BigDecimal offerQuantity, BigDecimal lowestOffer) {
        logger.info("successfulTrade bidQuantity : {}", offerQuantity);
        logger.info("successfulTrade lowestOffer : {}", lowestOffer);

        return offerQuantity + " share traded for " + lowestOffer + " per share.";

    }

    /**
     * Returns the list of orders associated with the given price.
     * If no orders exist for the given price, a new empty list is returned.
     * <p>
     * This method is used to ensure that there is always a list available
     * to add new orders to, regardless of whether there are existing orders
     * at the specified price.
     *
     * @param hasmap the map containing lists of orders grouped by price
     * @param price  the price for which to retrieve the list of orders
     * @return the list of orders associated with the given price,
     * or a new empty list if no orders exist for the given price
     */

    private List<Order> getOrders(Map<BigDecimal, List<Order>> hasmap, BigDecimal price) {
        return hasmap.getOrDefault(price, new ArrayList<>());
    }

    public Map<BigDecimal, List<Order>> getBidMap() {
        return bidMap;
    }

    public Map<BigDecimal, List<Order>> getOfferMap() {
        return offerMap;
    }

    // TODO: Pseudo-method. Now using as marker of starting Market. Mind is it need in future?
    public void openMarket() {
        logger.info("Market opens.");
    }

    // TODO: Pseudo-method. Now using as marker of finising Market. Mind is it need in future?
    public void closeMarket() {
        logger.info("Market close.");
        printFailedTrades(bidMap, "Bid for ");
        printFailedTrades(offerMap, "Offer of ");
    }

    private void printFailedTrades(Map<BigDecimal, List<Order>> hasmap, String coin) {
        for (Map.Entry<BigDecimal, List<Order>> entry : hasmap.entrySet()) {
            for (Order order : entry.getValue()) {
                logger.info(coin + order.getQuantity() + "shares for " + order.getPrice());
            }
        }
    }
}
