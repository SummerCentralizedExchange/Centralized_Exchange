package ru.spbstu.sce.controller;

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

@SpringBootTest
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MarketList marketList;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateLimitOrder() throws Exception {
        OrderItem orderItem = new OrderItem("Test",
                new BigDecimal("100"),
                new BigDecimal("10"),
                "Buy",
                "Limit",
                "baseCoin",
                "user1");

        when(marketList.isValidSymbol("Test")).thenReturn(true);

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"symbol\":\"Test\",\"price\":100,\"quantity\":10,\"side\":\"Buy\",\"type\":\"Limit\",\"marketUnit\":\"baseCoin\",\"userLogin\":\"user1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created successfully"));
    }

    @Test
    void testCreateMarketOrder() throws Exception {
        OrderItem orderItem = new OrderItem("Test",
                null,
                new BigDecimal("10"),
                "Buy",
                "Market",
                "baseCoin",
                "user1");

        when(marketList.getOfferOrders("Test")).thenReturn(Collections.singletonList(new Order(new BigDecimal("100"), new BigDecimal("10"))));

        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"symbol\":\"Test\",\"price\":null,\"quantity\":10,\"side\":\"Buy\",\"type\":\"Market\",\"marketUnit\":\"baseCoin\",\"userLogin\":\"user1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order created successfully"));
    }

    @Test
    void testCreateInvalidOrderType() throws Exception {
        mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"symbol\":\"Test\",\"price\":100,\"quantity\":10,\"side\":\"Buy\",\"type\":\"Invalid\",\"marketUnit\":\"baseCoin\",\"userLogin\":\"user1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid type parameter. Must be 'Market' or 'Limit'."));
    }

    @Test
    void testGetOrderList() throws Exception {
        when(marketList.getOrders("Test")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/order/list")
                        .param("symbol", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}