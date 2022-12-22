package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedInterfaces.AppDataMessage;

public class LoginData implements AppDataMessage, Serializable {
    private static final long serialVersionUID = 8799656478674716638L;
    String username;
    String password;
    String message;

    public LoginData(String username, String password, String message) {
        this.username = username;
        this.password = password;
        this.message = message;
    }

    public LoginData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
