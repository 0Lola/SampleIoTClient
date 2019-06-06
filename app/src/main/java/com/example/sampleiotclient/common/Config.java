package com.example.sampleiotclient.common;

import com.example.sampleiotclient.pojo.Setting;
import com.example.sampleiotclient.pojo.auth.LoginMessage;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    public static String USER = "使用者";
    public static String ADDRESS = "伺服器位置";
    public static String LOGOUT = "登出";
    public static String LOGOUT_MESSAGE = "登出本帳號";
    private static Config config = new Config();
    private List<Setting> settings;
    private LoginMessage message;

    private Config() {
        this.reset();
    }

    public static Config getConfig() {
        return config;
    }

    public void reset() {
        this.settings = new LinkedList<>();
        this.message = new LoginMessage();
    }


    public LoginMessage getMessage() {
        return message;
    }

    public void setMessage(LoginMessage message) {
        this.message = message;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void addSetting(Setting setting) {
        this.settings = this.settings.stream()
                .filter((item) -> !item.getKey().equals(setting.getKey()))
                .collect(Collectors.toList());
        this.settings.add(setting);
    }

}
