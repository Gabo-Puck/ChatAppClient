package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedInterfaces.AppDataMessage;

public class AppMessage implements Serializable {
    private String action;
    private AppDataMessage dataMessage;
    private int id;

    private static final long serialVersionUID = 8799656478674716638L;

    @Override
    public String toString() {
        return "AppMessage [action=" + action + ", dataMessage=" + dataMessage + ", id=" + id + "]";
    }

    public AppMessage(String action, AppDataMessage dataMessage, int id) {
        this.action = action;
        this.dataMessage = dataMessage;
        this.id = id;
    }

    public AppMessage() {
    }

    public AppDataMessage getDataMessage() {
        return dataMessage;
    }

    public void setDataMessage(AppDataMessage dataMessage) {
        this.dataMessage = dataMessage;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
