package com.example.sampleiotclient.pojo.auth;
import android.support.annotation.NonNull;

public class LoginMessage {

    private String address;
    private String gatewayPort;
    private String cloudPort;
    private String account;
    private String password;

    public LoginMessage(){

    }

    public LoginMessage(@NonNull String address, @NonNull String gatewayPort,@NonNull String cloudPort, @NonNull String account, String password) {
        this.address = address;
        this.gatewayPort = gatewayPort;
        this.cloudPort = cloudPort;
        this.account = account;
        this.password = password;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(String gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getCloudPort() {
        return cloudPort;
    }

    public void setCloudPort(String cloudPort) {
        this.cloudPort = cloudPort;
    }

    public String getGatewayServer(){
        return address+":"+gatewayPort;
    }

    public String getCloudServer(){
        return address+":"+cloudPort;
    }
}
