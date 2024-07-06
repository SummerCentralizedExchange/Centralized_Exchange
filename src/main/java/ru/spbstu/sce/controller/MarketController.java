package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.sce.orderbook.MarketList;
import ru.spbstu.sce.orderbook.Order;
import ru.spbstu.sce.orderbook.OrderBookResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    MarketList marketList;

    private List<OrderBookResponse.Position> formatOrderBookData(Map<BigDecimal, List<Order>> data, Comparator<BigDecimal> comparator, int limit) {
        return data.entrySet().stream()
                .map(e -> new OrderBookResponse.Position(
                        e.getKey(),
                        BigDecimal.valueOf(
                                e.getValue()
                                        .stream()
                                        .map(Order::getQuantity)
                                        .reduce(0, Integer::sum))))
                .sorted(Comparator.comparing(OrderBookResponse.Position::price, comparator))
                .limit(limit).toList();
    }

    @GetMapping("/orderbook")
    public OrderBookResponse getOrderBook(@RequestParam String symbol, @RequestParam(defaultValue = "25") int limit) {
        if (limit < 1 || limit > 50) {
            throw new IllegalArgumentException("Limit is out of bounds");
        }
        if (!marketList.isValidSymbol(symbol)) {
            throw new IllegalArgumentException("symbol %s does not exists".formatted(symbol));
        }
        return new OrderBookResponse(symbol,
                formatOrderBookData(marketList.getBidMap(symbol), Comparator.reverseOrder(), limit),
                formatOrderBookData(marketList.getOfferMap(symbol), Comparator.naturalOrder(), limit),
                System.currentTimeMillis());
    }

}
