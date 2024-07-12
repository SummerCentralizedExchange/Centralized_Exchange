package ru.spbstu.sce.orderbook;

import java.time.LocalDate;
import java.math.BigDecimal;

public class Candlestick {
    private LocalDate time;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;

    public Candlestick(LocalDate time, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }
}
