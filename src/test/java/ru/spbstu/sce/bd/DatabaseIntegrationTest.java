package ru.spbstu.sce.bd;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.spbstu.sce.db.entity.*;
import ru.spbstu.sce.db.entity.user.User;
import ru.spbstu.sce.db.repositories.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class DatabaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password");
        user.setApiKey("apikey123");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getUser_id());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getLogin()).isEqualTo("testUser");
    }

    @Test
    public void testCreateCoin() {
        Coin coin = new Coin();
        coin.setCoin_name("BTC");
        coinRepository.save(coin);

        Optional<Coin> foundCoin = coinRepository.findById(coin.getCoin_id());
        assertThat(foundCoin).isPresent();
        assertThat(foundCoin.get().getCoin_name()).isEqualTo("BTC");
    }

    @Test
    public void testCreateUserBalance() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password");
        user.setApiKey("apikey123");
        userRepository.save(user);

        Coin coin = new Coin();
        coin.setCoin_name("BTC");
        coinRepository.save(coin);

        UserBalanceId userBalanceId = new UserBalanceId(user.getUser_id(), coin.getCoin_id());
        UserBalance userBalance = new UserBalance();
        userBalance.setId(userBalanceId);
        userBalance.setUser(user);
        userBalance.setCoin(coin);
        userBalance.setAmount(BigDecimal.valueOf(1.5));
        userBalanceRepository.save(userBalance);

        Optional<UserBalance> foundUserBalance = userBalanceRepository.findById(userBalanceId);
        assertThat(foundUserBalance).isPresent();
        assertThat(foundUserBalance.get().getAmount()).isEqualByComparingTo(BigDecimal.valueOf(1.50));
    }

    @Test
    public void testCreateOrderHistory() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password");
        user.setApiKey("apikey123");
        userRepository.save(user);

        Coin baseCoin = new Coin();
        baseCoin.setCoin_name("BTC");
        coinRepository.save(baseCoin);

        Coin quoteCoin = new Coin();
        quoteCoin.setCoin_name("ETH");
        coinRepository.save(quoteCoin);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setPrice(BigDecimal.valueOf(50000.0));
        orderHistory.setQuantity(BigDecimal.valueOf(0.1));
        orderHistory.setTimestamp(LocalDateTime.now());
        orderHistory.setSide("buy");
        orderHistory.setUser(user);
        orderHistory.setBaseCoin(baseCoin);
        orderHistory.setQuoteCoin(quoteCoin);
        orderHistoryRepository.save(orderHistory);

        Optional<OrderHistory> foundOrderHistory = orderHistoryRepository.findById(orderHistory.getOrderHistory_id());
        assertThat(foundOrderHistory).isPresent();
        assertThat(foundOrderHistory.get().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(50000.00));

    }
}
