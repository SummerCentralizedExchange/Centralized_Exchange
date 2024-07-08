package ru.spbstu.sce.db.entity.user;

public class SignupRequest {
    private final String login;
    private final String password;

    public SignupRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getUserName() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

