package com.azureproject.chatclient;

import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedModels.LoginData;

import javafx.concurrent.Task;

public class OutputWorker extends Task<Void> {
    public static BlockingQueue<Task<Void>> queue;
    ObjectOutputStream output;

    public OutputWorker(ObjectOutputStream output, BlockingQueue<Task<Void>> _queue) {
        this.output = output;
        if (queue == null) {
            OutputWorker.queue = _queue;
        }
    }

    @Override
    protected Void call() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Login stuff");
        while (true) {
            try {
                Task<Void> task = queue.take();
                Thread t = new Thread(task);
                System.out.println("writing stuff");
                taskFinished(t);
            } catch (IllegalThreadStateException | InterruptedException e) {
                break;
            }
        }
        return null;
    }

    private void taskFinished(Thread t) throws IllegalThreadStateException {
        Boolean isCompleted = false;
        t.start();
        while (!isCompleted) {
            try {
                t.join();
                isCompleted = true;
            } catch (InterruptedException e) {
                isCompleted = false;
            }
        }

    }

}
