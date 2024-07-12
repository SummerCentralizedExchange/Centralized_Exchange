package ru.spbstu.sce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.sce.orderbook.Candlestick;
import ru.spbstu.sce.service.CandlestickService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/candlesticks")
public class CandlestickController {
    @Autowired
    private CandlestickService service;

    @GetMapping
    public List<Candlestick> getCandlesticks(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return service.getCandlesticks(start, end);
    }
}
