package ru.spbstu.sce.db.entity.coin;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coin")
public class Coin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coinId;

    @Column(unique = true, nullable = false)
    private String coinName;

    public Coin(String coinName) {
        this.coinName = coinName;
    }

    public Coin() {}
}
