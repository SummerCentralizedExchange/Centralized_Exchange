package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBook {
    private final Logger logger = LoggerFactory.getLogger(OrderBook.class);

    private final String symbol;

    private final SortedMap<BigDecimal, List<Order>> bidMap;
    private final SortedMap<BigDecimal, List<Order>> offerMap;

    public OrderBook(String symbol) {
        this.symbol = symbol;

        bidMap = new TreeMap<>(Comparator.reverseOrder()); // Usually need bids in reverse price order
        offerMap = new TreeMap<>();
    }

    public void addBid(BigDecimal price, BigDecimal quantity) {
        Order order = new Order(price, quantity);

        List<Order> orders = getOrders(bidMap, price);

        orders.add(order);
        bidMap.put(order.getPrice(), orders);
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
        matchOrders();

        logger.info("offerMap after insert size : {}", orders.size());
    }

    private static void removeFirstOrder(SortedMap<BigDecimal, List<Order>> map) {
        var entry = map.firstEntry();
        entry.getValue().removeFirst();
        if (entry.getValue().isEmpty()) {
            map.remove(entry.getKey());
        }
    }

    public void matchOrders() {
        while (true) {
            Map.Entry<BigDecimal, List<Order>> highestBidOrders = bidMap.firstEntry();
            var lowestOfferOrders = offerMap.firstEntry();

            // Completion condition: there are no purchase or sale order...
            if (lowestOfferOrders == null || highestBidOrders == null) {
                logger.info("OrderBook matchOrders finished. Orderbook is empty");
                break;
            }
            BigDecimal highestBid = bidMap.firstKey();
            BigDecimal lowestOffer = offerMap.firstKey();
            // ... or the lowest sale price is higher than the highest purchase price
            if (lowestOffer.compareTo(highestBid) > 0) {
                logger.info("OrderBook matchOrders finished. Spread is > 0");
                break;
            }

            List<Order> bidOrders = highestBidOrders.getValue();
            List<Order> offerOrders = lowestOfferOrders.getValue();

            // Getting the first (oldest) application from each list
            Order bidOrder = bidOrders.getFirst();
            Order offerOrder = offerOrders.getFirst();

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
                removeFirstOrder(offerMap);
            }
            // If the quantity in the sales order is greater than the quantity in the purchase order
            else if (offerQuantity.compareTo(bidQuantity) > 0) {
                logger.info("bidQuantity < offerQuantity");
                logger.info(makeSuccessfulTradeInfo(bidQuantity, highestBid));

                offerOrder.setQuantity(offerQuantity.subtract(bidQuantity));
                logger.info("offerQuantity remaining quantity : {}", offerQuantity);

                removeFirstOrder(bidMap);
            }
            // If the quantity in the purchase and sale orders are equal
            else {
                logger.info(makeSuccessfulTradeInfo(offerQuantity, highestBid));

                // We delete closed purchase and sale orders
                // We remove the price from the queue if all requests for this price are fulfilled
                removeFirstOrder(offerMap);
                removeFirstOrder(bidMap);
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

    public String bidMarket(BigDecimal quantityBaseCoin) {
        while (quantityBaseCoin.compareTo(BigDecimal.ZERO) > 0) {
            if (offerMap.isEmpty()) {
                return "Limit order is needed, orderbook is empty. Your quantity = " + quantityBaseCoin;
            }
            var e = offerMap.firstEntry();
            BigDecimal price = e.getKey();
            Order matchedOrder = e.getValue().getFirst();
            BigDecimal quantity = quantityBaseCoin.min(matchedOrder.getQuantity());
            addBid(price, quantity);
            quantityBaseCoin = quantityBaseCoin.subtract(quantityBaseCoin);
        }
        return "OK";
    }

    public String offerMarket(BigDecimal quantityBaseCoin) {
        while (quantityBaseCoin.compareTo(BigDecimal.ZERO) > 0) {
            if (bidMap.isEmpty()) {
                // TODO proper error handling. Need to signal the user that his order partially worked
                return "Limit order is needed, orderbook is empty. Your quantity = " + quantityBaseCoin;
            }
            var e = bidMap.firstEntry();
            BigDecimal price = e.getKey();
            Order matchedOrder = e.getValue().getFirst();
            BigDecimal quantity = quantityBaseCoin.min(matchedOrder.getQuantity());
            addOffer(price, quantity);
            quantityBaseCoin = quantityBaseCoin.subtract(quantityBaseCoin);
        }
        return "OK";
    }
}
