package com.azureproject.SharedModels;

import java.io.Serializable;

import com.azureproject.SharedEnum.EnumActions;
import com.azureproject.SharedInterfaces.AppDataMessage;

public class AppMessage implements Serializable {
    private static final long serialVersionUID = 8799656478674716638L;
    private EnumActions action;
    private AppDataMessage dataMessage;
    private int id;

    private String status;

    public AppMessage() {
    }

    public AppMessage(EnumActions action, AppDataMessage dataMessage, int id, String status) {
        this.action = action;
        this.dataMessage = dataMessage;
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AppMessage [action=" + action + ", dataMessage=" + dataMessage + ", id=" + id + "]";
    }

    public AppDataMessage getDataMessage() {
        return dataMessage;
    }

    public void setDataMessage(AppDataMessage dataMessage) {
        this.dataMessage = dataMessage;
    }

    public EnumActions getAction() {
        return this.action;
    }

    public void setAction(EnumActions action) {
        this.action = action;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
