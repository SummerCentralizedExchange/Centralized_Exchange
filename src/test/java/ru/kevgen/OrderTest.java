package ru.kevgen;

import static org.junit.Assert.*;

public class OrderTest {
    private Order order;

    @org.junit.Test
    public void getPrice() {
        order = new Order(69, 1);
        assertEquals(69, order.getPrice(), .000000000001);
    }

    @org.junit.Test
    public void getQuantity() {
        order = new Order(69, 1);
        assertEquals(1, order.getQuantity());
    }

    @org.junit.Test
    public void setPrice() {
        order = new Order(69, 1);
        order.setPrice(10);
        assertEquals(10, order.getPrice(), .000000000001);
    }

    @org.junit.Test
    public void setQuantity() {
        order = new Order(69, 1);
        order.setQuantity(2);
        assertEquals(2, order.getQuantity());
    }

    @org.junit.Test
    public void testToString() {
        order = new Order(69, 1);
        assertEquals("69.0 1", order.toString());
    }
}