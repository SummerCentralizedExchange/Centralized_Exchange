package ru.spbstu.sce.user;

public class CreateUserRequest {
    private String username;
    private String password;

    public CreateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

