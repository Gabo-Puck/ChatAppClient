package com.azureproject.SharedInterfaces;

import java.io.Serializable;
import java.util.List;

import com.azureproject.SharedModels.Message;
import com.azureproject.SharedModels.User;

public abstract class Chat implements AppDataMessage, Serializable {
    private static final long serialVersionUID = 8799656478674716638L;

    String name;
    List<Message> messages;
    List<User> users;

    public Chat() {
    }

    public Chat(String name, List<Message> messages, List<User> users) {
        this.name = name;
        this.messages = messages;
        this.users = users;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", messages='" + getMessages() + "'" +
                ", users='" + getUsers().toString() + "'" +
                "}";
    }

}
