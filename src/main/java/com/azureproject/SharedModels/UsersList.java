package com.azureproject.SharedModels;

import java.io.Serializable;
import java.util.List;

import com.azureproject.SharedInterfaces.AppDataMessage;

public class UsersList implements AppDataMessage, Serializable {
    private static final long serialVersionUID = 8799656478674716638L;
    List<String> users;

    public UsersList() {
    }

    public UsersList(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return this.users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
