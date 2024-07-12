package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.orderbook.MarketList;
import ru.spbstu.sce.orderbook.Order;
import ru.spbstu.sce.orderbook.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MarketList market;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderItem orderItem) {

        if (orderItem.getType().equalsIgnoreCase("Market")) {
            return createMarketOrder(orderItem);
        } else if (orderItem.getType().equalsIgnoreCase("Limit")) {
            return createLimitOrder(orderItem);
        } else {
            return "Invalid type parameter. Must be 'Market' or 'Limit'.";
        }
    }

    private String createLimitOrder(OrderItem orderItem) {
        if (orderItem.getPrice() == null) {
            return "Price must be specified for limit orders.";
        }
        return processOrder(orderItem);
    }

    private String createMarketOrder(OrderItem orderItem) {
        List<Order> matchingOrders = getMatchingOrders(orderItem);

        if (matchingOrders.isEmpty()) {
            return "No matching orders available.";
        }

        BigDecimal totalQuantity = orderItem.getQuantity();
        BigDecimal filledQuantity = BigDecimal.ZERO;
        BigDecimal averagePrice = BigDecimal.ZERO;

        for (Order matchingOrder : matchingOrders) {
            BigDecimal tradeQuantity = matchingOrder.getQuantity().min(totalQuantity.subtract(filledQuantity));
            filledQuantity = filledQuantity.add(tradeQuantity);
            averagePrice = averagePrice.add(matchingOrder.getPrice().multiply(tradeQuantity));

            if (filledQuantity.compareTo(totalQuantity) >= 0) {
                break;
            }
        }

        averagePrice = averagePrice.divide(filledQuantity, BigDecimal.ROUND_HALF_UP);
        orderItem.setPrice(averagePrice);
        orderItem.setQuantity(filledQuantity);

        return processOrder(orderItem);
    }

    private List<Order> getMatchingOrders(OrderItem orderItem) {
        if (orderItem.getSide().equalsIgnoreCase("Buy")) {
            return market.getOfferOrders(orderItem.getSymbol());
        } else if (orderItem.getSide().equalsIgnoreCase("Sell")) {
            return market.getBidOrders(orderItem.getSymbol());
        } else {
            throw new IllegalArgumentException("Invalid side parameter. Must be 'Buy' or 'Sell'.");
        }
    }

    private String processOrder(OrderItem orderItem) {
        if (orderItem.getSide().equalsIgnoreCase("Buy")) {
            market.bidAdd(orderItem);
        } else if (orderItem.getSide().equalsIgnoreCase("Sell")) {
            market.offerAdd(orderItem);
        } else {
            return "Invalid side parameter. Must be 'Buy' or 'Sell'.";
        }

        return "Order created successfully";
    }

    @GetMapping("/list")
    public List<Order> getOrderList(@RequestParam(required = false) String symbol,
                                    @RequestParam(required = false) String orderId,
                                    @RequestParam(required = false) String orderLinkId,
                                    @RequestParam(required = false) String copyTradeOrderType) {

        List<Order> orders = market.getOrders(symbol);

        return orders;
    }

}
