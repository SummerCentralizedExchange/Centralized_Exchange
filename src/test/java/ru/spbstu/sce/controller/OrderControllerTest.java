package ru.spbstu.sce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spbstu.sce.orderbook.MarketList;
import ru.spbstu.sce.orderbook.Order;
import ru.spbstu.sce.orderbook.OrderItem;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MarketList market;

    @InjectMocks
    private OrderController orderController;

    final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(new BigDecimal(10000));
        orderItem.setQuantity(new BigDecimal(1));
        orderItem.setSymbol("BTCUSDT");
        orderItem.setType("Limit");
        orderItem.setSide("Buy");

        String jsonData = objectMapper.writeValueAsString(orderItem);

        mockMvc.perform(post("/order/create")
                        .contentType("application/json")
                        .content(jsonData))
                .andExpect(status().isOk());

        verify(market, times(1)).bidAdd(any(OrderItem.class));
    }

    @Test
    public void testGetOrderList() throws Exception {
        when(market.getOrders(anyString())).thenReturn(Collections.singletonList(new Order(BigDecimal.valueOf(10000), BigDecimal.valueOf(1))));

        mockMvc.perform(get("/order/list")
                        .param("symbol", "BTCUSDT"))
                .andExpect(status().isOk());

        verify(market, times(1)).getOrders("BTCUSDT");
    }


    @Test
    void testCreateLimitOrder() throws Exception {
        OrderItem orderItem = new OrderItem("Test",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(1),
                "Buy",
                "Limit",
                "baseCoin");

        when(market.isValidSymbol("Test")).thenReturn(true);

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created successfully"));
    }

    @Test
    void testCreateMarketOrder() throws Exception {
        OrderItem orderItem = new OrderItem("Test",
                null,
                BigDecimal.valueOf(10),
                "Buy",
                "Market",
                "baseCoin");

        when(market.getOfferOrders("Test")).thenReturn(Collections.singletonList(new Order(BigDecimal.valueOf(100), BigDecimal.valueOf(10))));

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created successfully"));
    }

    @Test
    void testCreateInvalidOrderType() throws Exception {
        OrderItem orderItem = new OrderItem("Test",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10),
                "Buy",
                "Invalid",
                "baseCoin");
        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid type parameter. Must be 'Market' or 'Limit'."));
    }
}