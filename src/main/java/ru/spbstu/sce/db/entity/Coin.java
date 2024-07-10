package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coin")
public class Coin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coinId;

    private String coinName;
}
