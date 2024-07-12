package ru.spbstu.sce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spbstu.sce.db.entity.OrderHistory;
import ru.spbstu.sce.db.repositories.OrderHistoryRepository;
import ru.spbstu.sce.orderbook.Candlestick;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandlestickService {
    @Autowired
    private OrderHistoryRepository repository;

    public List<Candlestick> getCandlesticks(LocalDate startDate, LocalDate endDate) {
        List<OrderHistory> orders = repository.findAllByTimestampBetween(
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay()
        );

        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getTimestamp().toLocalDate()))
                .entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<OrderHistory> dailyOrders = entry.getValue();

                    BigDecimal open = dailyOrders.get(0).getPrice();
                    BigDecimal close = dailyOrders.get(dailyOrders.size() - 1).getPrice();
                    BigDecimal high = dailyOrders.stream().map(OrderHistory::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                    BigDecimal low = dailyOrders.stream().map(OrderHistory::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

                    return new Candlestick(date, open, high, low, close);
                })
                .collect(Collectors.toList());
    }
}

