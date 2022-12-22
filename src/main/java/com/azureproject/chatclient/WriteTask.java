package com.azureproject.chatclient;

import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedModels.AppMessage;

import javafx.concurrent.Task;

public class WriteTask extends Task<Object> {
    private final AppMessage message;

    private CountDownLatch latch;

    public WriteTask(AppMessage message, CountDownLatch latch) {
        this.message = message;
        this.latch = latch;
        this.setOnSucceeded(getOnCancelled());
    }

    @Override
    protected Object call() throws Exception {
        OutputWorker.output.writeObject(message);
        OutputWorker.output.flush();
        latch.countDown();
        // TODO Auto-generated method stub
        return null;
    }
}
