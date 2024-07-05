package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coin")
public class Coin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coin_id;

    @Column(unique = false, nullable = false)
    private String coin_name;

    public Long getCoinId() {
        return coin_id;
    }

    public void setCoinId(Long coin_id) {
        this.coin_id = coin_id;
    }

    public String getCoinName() {
        return coin_name;
    }

    public void setCoinName(String coin_name) {
        this.coin_name = coin_name;
    }
}
