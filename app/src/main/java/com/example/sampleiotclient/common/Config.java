package com.example.sampleiotclient.common;
import com.example.sampleiotclient.pojo.auth.LoginMessage;

public class Config {

    private static Config config = new Config();
    private LoginMessage message;

    private Config() {
        this.reset();
    }

    public static Config getConfig() {
        return config;
    }

    public void reset() {
        this.message = new LoginMessage();
    }

    public LoginMessage getMessage() {
        return message;
    }

    public void setMessage(LoginMessage message) {
        this.message = message;
    }

}
