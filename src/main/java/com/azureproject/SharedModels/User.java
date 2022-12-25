package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedInterfaces.AppDataMessage;

public class User implements AppDataMessage, Serializable {
    int ID;
    String username;
    private static final long serialVersionUID = 8799656478674716638L;

    public User() {
    }

    public User(int iD, String username) {
        ID = iD;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
