package ru.spbstu.sce.db.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spbstu.sce.db.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
