package ru.spbstu.sce.db.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.sce.db.entity.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
