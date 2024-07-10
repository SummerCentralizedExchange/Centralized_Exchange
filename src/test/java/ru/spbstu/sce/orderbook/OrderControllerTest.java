package ru.spbstu.sce.orderbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spbstu.sce.controller.OrderController;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MarketList market;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(new BigDecimal(10000));
        orderItem.setQuantity(new BigDecimal(1));
        orderItem.setSymbol("BTCUSDT");
        orderItem.setType("Limit");
        orderItem.setSide("Buy");

        ObjectMapper objectMapper = new ObjectMapper();
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
}