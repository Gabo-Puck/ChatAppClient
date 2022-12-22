package com.azureproject.chatclient;

import java.io.ObjectInputStream;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedModels.AppMessage;
import com.azureproject.chatclient.Models.Response;

import javafx.concurrent.Task;

public class InputWorker extends Task<Void> {

    ObjectInputStream input;
    BlockingQueue<Task<Void>> queue;
    public static ConcurrentHashMap<Integer, Response> responseList = new ConcurrentHashMap<>();

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
                System.out.println("server has answered");
                System.out.println("creating task for answer from server");
                new Thread(new Task<Void>() {
                    @Override
                    protected Void call() {
                        String action = a.getAction();
                        switch (action) {
                            case "LoginAttempt":
                                fetchResponse(a);
                                break;

                            default:
                                break;
                        }
                        return null;

                    }
                }).run();
                System.out.println(a.toString());

            } catch (Exception er) {
                er.printStackTrace();
                break;
            }
        }
        System.out.println("Leaving input worker");
        return null;
    }

    public static void addResponseWait(Response res) {
        int max = Integer.MAX_VALUE;
        int min = 1;
        int range = max - min;
        Response inserted;
        inserted = res;
        while (true) {
            int number = (int) (Math.random() * range) - min;
            Integer idTransaction = Integer.valueOf(number);

            Response resReplaced = responseList.put(idTransaction, inserted);
            if (!Optional.ofNullable(resReplaced).isPresent()) {
                res.getResponseData().setId(idTransaction);
                System.out.println("ID:" + idTransaction);
                break;
            }
            inserted = resReplaced;
        }
    }

    private void fetchResponse(AppMessage message) {
        System.out.println("Fetching stuff");
        Response res = responseList.get(message.getId());
        res.setResponseData(message);
        res.getLatch().countDown();
        responseList.remove(message.getId());
    }

}
