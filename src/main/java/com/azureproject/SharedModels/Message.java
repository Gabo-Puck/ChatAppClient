package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedInterfaces.AppDataMessage;
import com.azureproject.SharedInterfaces.Chat;

public class Message implements AppDataMessage, Serializable {
    private static final long serialVersionUID = 8799656478674716638L;

    Chat chatReciever;
    User from;
    String content;

    public Message() {
    }

    public Message(Chat chatReciever, User from, String content) {
        this.chatReciever = chatReciever;
        this.from = from;
        this.content = content;
    }

    public Chat getChatReciever() {
        return chatReciever;
    }

    public void setChatReciever(Chat chatReciever) {
        this.chatReciever = chatReciever;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
