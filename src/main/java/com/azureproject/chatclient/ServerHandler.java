package com.azureproject.chatclient;

import java.net.Socket;

import javafx.concurrent.Task;

public class ServerHandler extends Task<Void> {
    private static final ServerHandler resources = new ServerHandler();

    public static ServerHandler getServerHandler() {
        return resources;
    }

    @Override
    protected Void call() throws Exception {
        try (Socket server = new Socket("localhost", 76)) {
            
        } catch (Exception e) {

        }
        return null;
    }

}
