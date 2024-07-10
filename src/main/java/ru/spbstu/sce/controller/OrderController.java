package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.sce.orderbook.MarketList;
import ru.spbstu.sce.orderbook.Order;
import ru.spbstu.sce.orderbook.OrderItem;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MarketList market;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderItem orderItem) {

        // We determine the type of operation (purchase or sale)
        if (orderItem.getSide().equalsIgnoreCase("Buy")) {
            market.bidAdd(orderItem);
        } else if (orderItem.getSide().equalsIgnoreCase("Sell")) {
            market.offerAdd(orderItem);
        } else {
            return "Invalid side parameter. Must be 'Buy' or 'Sell'.";
        }

        // Other parameters can be used for additional processing logic.

        return "Order created successfully";
    }

    @GetMapping("/list")
    public List<Order> getOrderList(@RequestParam(required = false) String symbol,
                                    @RequestParam(required = false) String orderId,
                                    @RequestParam(required = false) String orderLinkId,
                                    @RequestParam(required = false) String copyTradeOrderType) {

        // Get the orders based on the symbol
        List<Order> orders = market.getOrders(symbol);

        return orders;
    }

}
