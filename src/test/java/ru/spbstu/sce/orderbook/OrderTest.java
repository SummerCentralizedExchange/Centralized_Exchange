package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderTest {
    private Order order;

    @org.junit.Test
    public void getPrice() {
        BigDecimal price = BigDecimal.valueOf(69);
        order = new Order(price, 1);
        assertEquals(price, order.getPrice());
    }

    @org.junit.Test
    public void getQuantity() {
        order = new Order(BigDecimal.valueOf(69), 1);
        assertEquals(1, order.getQuantity());
    }

    @org.junit.Test
    public void setPrice() {
        order = new Order(BigDecimal.valueOf(69), 1);
        BigDecimal newValue = BigDecimal.valueOf(10);
        order.setPrice(newValue);
        assertEquals(newValue, order.getPrice());
    }

    @org.junit.Test
    public void setQuantity() {
        order = new Order(BigDecimal.valueOf(69), 1);
        order.setQuantity(2);
        assertEquals(2, order.getQuantity());
    }

    @org.junit.Test
    public void testToString() {
        order = new Order(BigDecimal.valueOf(69.0), 1);
        assertEquals("69.0 1", order.toString());
    }
}