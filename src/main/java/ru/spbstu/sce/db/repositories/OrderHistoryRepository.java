package ru.spbstu.sce.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spbstu.sce.db.entity.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    @Query("SELECT o FROM OrderHistory o WHERE o.timestamp BETWEEN :start AND :end ORDER BY o.timestamp")
    List<OrderHistory> findAllByTimestampBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
