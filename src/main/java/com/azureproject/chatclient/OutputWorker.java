package com.azureproject.chatclient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.concurrent.Task;

public class OutputWorker extends Task<Void> {
    public static BlockingQueue<Task<Object>> queue = new LinkedBlockingQueue<>();
    public static ObjectOutputStream output;
    public static Task<Void> th;

    @Override
    protected Void call() throws Exception {
        // TODO Auto-generated method stub

        System.out.println("Login stuff");
        while (true) {
            try {
                System.out.println("Waiting for a task...");
                Task<Object> task = queue.take();
                if (!Optional.ofNullable(task).isPresent()) {
                    break;
                }
                System.out.println("Task taken");
                Thread t = new Thread(task);
                System.out.println("writing stuff");
                taskFinished(t);
                System.out.println("end of loop");
            } catch (IllegalThreadStateException | InterruptedException e) {
                break;
            }
        }
        System.out.println("Leaving output worker");

        return null;
    }

    private void taskFinished(Thread t) throws IllegalThreadStateException {
        Boolean isCompleted = false;
        t.start();
        while (!isCompleted) {
            try {
                System.out.println("Waiting for task to finish");
                t.join();
                isCompleted = true;
                System.out.println("Task finished");
            } catch (InterruptedException e) {
                System.out.println("Task interrupted, not finished yet");

                isCompleted = false;
            }
        }

    }

}
