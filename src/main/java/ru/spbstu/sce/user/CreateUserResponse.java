package ru.spbstu.sce.user;

import java.util.UUID;

public class CreateUserResponse {
    private final UUID uid;
    private final String apiKey;

    public CreateUserResponse(UUID uid, String apiKey) {
        this.uid = uid;
        this.apiKey = apiKey;
    }

    public UUID getUid() {
        return uid;
    }

    public String getApiKey() {
        return apiKey;
    }
}
