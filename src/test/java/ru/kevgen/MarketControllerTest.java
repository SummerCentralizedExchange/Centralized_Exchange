package ru.kevgen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MarketControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MarketList market;

    @InjectMocks
    private MarketController marketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(marketController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderItem orderItem = new OrderItem("BTCUSDT", 10000, 1, "Limit", "Buy");

        mockMvc.perform(post("/v5/order/create")
                        .param("category", "crypto")
                        .param("symbol", "BTC")
                        .param("side", "Buy")
                        .param("orderType", "Limit")
                        .param("qty", "1")
                        .contentType("application/json")
                        .content("{\"coinName\":\"BTC\",\"price\":10000,\"quantity\":1,\"type\":\"Limit\",\"transactionType\":\"Buy\"}"))
                .andExpect(status().isOk());

        verify(market, times(1)).bidAdd(any(OrderItem.class));
    }

    @Test
    public void testGetOrderList() throws Exception {
        when(market.getOrders(anyString())).thenReturn(Collections.singletonList(new Order(10000, 1)));

        mockMvc.perform(get("/v5/order/list")
                        .param("symbol", "BTC"))
                .andExpect(status().isOk());

        verify(market, times(1)).getOrders("BTC");
    }
}