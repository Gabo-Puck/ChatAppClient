package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedInterfaces.AppDataMessage;

public class LoginData implements AppDataMessage, Serializable {
    String username;
    String password;
    private static final long serialVersionUID = 8799656478674716638L;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginData() {
    }
}
