package ru.spbstu.sce.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(unique = true, nullable = false)
    private String login;
    private String password;
    @Column(unique = true, nullable = false)
    private String apiKey;

    public Long getUser_id() {
        return user_id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
