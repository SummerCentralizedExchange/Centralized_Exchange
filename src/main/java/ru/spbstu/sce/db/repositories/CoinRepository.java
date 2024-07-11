package ru.spbstu.sce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.sce.db.entity.Coin.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
}
