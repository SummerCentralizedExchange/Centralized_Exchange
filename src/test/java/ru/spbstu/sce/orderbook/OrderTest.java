package ru.spbstu.sce.orderbook;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderTest {
    public static final BigDecimal A_QUANTITY = BigDecimal.ONE;
    public static final BigDecimal NEW_QUANTITY = BigDecimal.valueOf(2);
    private Order order;

    @org.junit.Test
    public void getPrice() {
        BigDecimal price = BigDecimal.valueOf(69);
        order = new Order(price, A_QUANTITY);
        assertEquals(price, order.getPrice());
    }

    @org.junit.Test
    public void getQuantity() {
        order = new Order(BigDecimal.valueOf(69), A_QUANTITY);
        assertEquals(A_QUANTITY, order.getQuantity());
    }

    @org.junit.Test
    public void setPrice() {
        order = new Order(BigDecimal.valueOf(69), A_QUANTITY);
        BigDecimal newValue = BigDecimal.valueOf(10);
        order.setPrice(newValue);
        assertEquals(newValue, order.getPrice());
    }

    @org.junit.Test
    public void setQuantity() {
        order = new Order(BigDecimal.valueOf(69), A_QUANTITY);
        order.setQuantity(NEW_QUANTITY);
        assertEquals(NEW_QUANTITY, order.getQuantity());
    }

    @org.junit.Test
    public void testToString() {
        order = new Order(BigDecimal.valueOf(69.0), A_QUANTITY);
        assertEquals("69.0 1", order.toString());
    }
}