package ru.spbstu.sce.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SecurityTestConfiguration.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("testuser")
    public void testGetWalletBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/wallet-balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Ожидаем пустой список
    }

    @Test
    @WithUserDetails("testuser")
    public void testGetWalletBalanceWithCoin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/wallet-balance")
                        .param("coin", "BTC,ETH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].coin").value("BTC"))
                .andExpect(jsonPath("$[1].coin").value("ETH"));
    }
}
