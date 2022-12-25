package com.azureproject.chatclient;

import java.util.concurrent.CountDownLatch;

import java.io.IOException;

import com.azureproject.SharedModels.AppMessage;

import javafx.concurrent.Task;

public class WriteTask extends Task<Object> {
    private final AppMessage message;
    Boolean isError;
    CountDownLatch latch;

    public WriteTask(AppMessage message, Boolean isError, CountDownLatch latch) {
        this.message = message;
        this.isError = isError;
        this.latch = latch;
    }

    @Override
    protected Void call() {
        try {
            OutputWorker.output.writeObject(message);
            OutputWorker.output.flush();
        } catch (IOException e) {
            isError = true;
            this.latch.countDown();
        }
        return null;
    }
}
