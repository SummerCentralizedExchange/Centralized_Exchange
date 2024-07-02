package ru.spbstu.sce.user;

import java.util.UUID;

public class User {
    private String username;
    private String password;
    private UUID uid;
    private String apiKey;

    public User(String username, String password, UUID uid, String apiKey) {
        this.username = username;
        this.password = password;
        this.uid = uid;
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public UUID getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
