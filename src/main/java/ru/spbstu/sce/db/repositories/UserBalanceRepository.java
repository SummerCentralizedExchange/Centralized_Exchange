package ru.spbstu.sce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spbstu.sce.db.entity.UserBalance;
import ru.spbstu.sce.db.entity.UserBalanceId;
import ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    Optional<UserBalance> findById(UserBalanceId userBalanceId);

    @Query("""
            select new ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance(b.coin.coinName, b.amount)
            from UserBalance b
            where b.user.login = :login and b.amount > 0""")
    List<CoinWalletBalance> findByUserLogin(String login);

    @Query("""
            select new ru.spbstu.sce.db.entity.user.wallet.CoinWalletBalance(c.coinName, coalesce(b.amount, 0) )
            from Coin c
            left join UserBalance b on c.coinId = b.coin.coinId and b.user.login = :login
            where c.coinName in :coinNames""")
    List<CoinWalletBalance> findByUserLoginAndCoinNames(String login, List<String> coinNames);
}
