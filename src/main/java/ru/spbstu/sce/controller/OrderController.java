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

    private String createMarketOrder(OrderItem orderItem) {
        if (orderItem.getSide().equalsIgnoreCase("Buy")) {
            if (!"baseCoin".equalsIgnoreCase(orderItem.getMarketUnit())) {
                //default buy quoteCoin
                throw new UnsupportedOperationException();
            } else {
                //buy baseCoin
                market.bidMarket(orderItem);
            }
        } else if (orderItem.getSide().equalsIgnoreCase("Sell")) {
            if (!"quoteCoin".equalsIgnoreCase(orderItem.getMarketUnit())) {
                //default sell baseCoin
                market.offerMarket(orderItem);
            } else {
                //sell quoteCoin
                throw new UnsupportedOperationException();
            }
        } else {
            return "Invalid side parameter. Must be 'Buy' or 'Sell'.";
        }
        return "Order created successfully";
    }

    private String createLimitOrder(OrderItem orderItem) {
        if (orderItem.getPrice() == null) {
            return "Price must be specified for limit orders.";
        }
        return processOrder(orderItem);
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
