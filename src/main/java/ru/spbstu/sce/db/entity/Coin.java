package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coin")
public class Coin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coin_id;

    @Column(unique = true, nullable = false)
    private String coin_name;

}
