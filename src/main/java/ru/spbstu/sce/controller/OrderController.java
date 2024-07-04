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
    public String createOrder(@RequestBody OrderItem orderItem,
                              @RequestParam String category,
                              @RequestParam String symbol,
                              @RequestParam(required = false) Integer isLeverage,
                              @RequestParam String side,
                              @RequestParam String orderType,
                              @RequestParam double qty,
                              @RequestParam(required = false) String marketUnit,
                              @RequestParam(required = false) String price,
                              @RequestParam(required = false) Integer triggerDirection,
                              @RequestParam(required = false) String orderFilter,
                              @RequestParam(required = false) String triggerPrice,
                              @RequestParam(required = false) String triggerBy,
                              @RequestParam(required = false) String orderIv,
                              @RequestParam(required = false) String timeInForce,
                              @RequestParam(required = false) Integer positionIdx,
                              @RequestParam(required = false) String orderLinkId,
                              @RequestParam(required = false) String takeProfit,
                              @RequestParam(required = false) String stopLoss,
                              @RequestParam(required = false) String tpTriggerBy,
                              @RequestParam(required = false) String slTriggerBy,
                              @RequestParam(required = false) Boolean reduceOnly,
                              @RequestParam(required = false) Boolean closeOnTrigger,
                              @RequestParam(required = false) String smpType,
                              @RequestParam(required = false) Boolean mmp,
                              @RequestParam(required = false) String tpslMode,
                              @RequestParam(required = false) String tpLimitPrice,
                              @RequestParam(required = false) String slLimitPrice,
                              @RequestParam(required = false) String tpOrderType,
                              @RequestParam(required = false) String slOrderType) {

        // We determine the type of operation (purchase or sale)
        if (side.equalsIgnoreCase("Buy")) {
            market.bidAdd(orderItem);
        } else if (side.equalsIgnoreCase("Sell")) {
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
