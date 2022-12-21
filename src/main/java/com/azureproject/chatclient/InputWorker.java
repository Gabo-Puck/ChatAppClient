package com.azureproject.chatclient;

import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

import com.azureproject.SharedModels.AppMessage;

import javafx.concurrent.Task;

public class InputWorker extends Task<Void> {

    ObjectInputStream input;
    BlockingQueue<Task<Void>> queue;

    public InputWorker(ObjectInputStream input) {
        this.input = input;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("Login stuff");
        while (true) {
            try {
                System.out.println("reading stuff");
                AppMessage a = (AppMessage) input.readObject();
                System.out.println(a.toString());

            } catch (Exception er) {
                er.printStackTrace();
                break;
            }
        }
        return null;
    }

}
