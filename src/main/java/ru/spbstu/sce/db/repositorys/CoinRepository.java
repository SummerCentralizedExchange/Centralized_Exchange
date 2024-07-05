package ru.spbstu.sce.db.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.sce.db.entity.Coin;

public interface CoinRepository extends JpaRepository<Coin, Long> {
}
