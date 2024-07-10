package ru.spbstu.sce.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.spbstu.sce.orderbook.MarketList;
import ru.spbstu.sce.orderbook.OrderItem;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MarketControllerTest {

    public static final String THE_SYMBOL = "BTCUSDT";
    private MockMvc mockMvc;

    @Mock
    private MarketList market;

    @InjectMocks
    private MarketController marketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marketController).build();
    }

    @Test
    void getOrderBookTest() throws Exception {

        var myMarket = getTestMarket();

        when(market.isValidSymbol(THE_SYMBOL)).thenReturn(true);
        when(market.getBidMap(THE_SYMBOL)).thenReturn(myMarket.getBidMap(THE_SYMBOL));
        when(market.getOfferMap(THE_SYMBOL)).thenReturn(myMarket.getOfferMap(THE_SYMBOL));

        mockMvc.perform(get("/market/orderbook")
                        .param("symbol", THE_SYMBOL)
                        .param("limit", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"symbol\":\"BTCUSDT\",\"asks\":[[500001,1],[500000,1]],\"bids\":[[499999,1],[499998,1]]}"))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    private static MarketList getTestMarket() {
        var myMarket = new MarketList();

        myMarket.add(THE_SYMBOL);

        for (int i = 0; i < 10; i++) {
            var item = new OrderItem();
            item.setSymbol(THE_SYMBOL);
            item.setPrice(BigDecimal.valueOf(500000 - 1 - i));
            item.setQuantity(BigDecimal.valueOf(1));
            myMarket.bidAdd(item);
            var item2 = new OrderItem();
            item2.setSymbol(THE_SYMBOL);
            item2.setPrice(BigDecimal.valueOf(500000 + i));
            item2.setQuantity(BigDecimal.valueOf(1));
            myMarket.offerAdd(item2);
        }
        return myMarket;
    }
}