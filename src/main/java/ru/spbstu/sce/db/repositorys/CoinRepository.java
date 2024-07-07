package ru.spbstu.sce.db.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbstu.sce.db.entity.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {
}
