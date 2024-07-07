package ru.spbstu.sce.db.entity.user;

public class SinginRequest {
    private final String login;
    private final String password;
    //private final String apiKey;

    public SinginRequest(String login, String password) {
        this.login = login;
        this.password = password;
        //this.apiKey = apiKey;
    }

    public String getUserName() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    //public String getApiKey() {
    //    return apiKey;
    //}
}
