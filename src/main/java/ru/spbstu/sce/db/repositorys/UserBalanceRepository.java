package ru.spbstu.sce.db.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.sce.db.entity.UserBalance;
import ru.spbstu.sce.db.entity.UserBalanceId;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    Optional<UserBalance> findById(UserBalanceId userBalanceId);
}
