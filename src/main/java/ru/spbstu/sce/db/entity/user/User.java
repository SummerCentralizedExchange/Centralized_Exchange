package ru.spbstu.sce.db.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "\"user\"") /*use additional \"\" to differ from postgre USER */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Login cannot be empty")
    @Column(unique = true, nullable = false)
    private String login;
    
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
    @Transient
    private String passwordConfirm;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
