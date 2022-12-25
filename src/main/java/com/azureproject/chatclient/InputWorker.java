package com.azureproject.chatclient;

import java.io.ObjectInputStream;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import com.azureproject.SharedEnum.EnumActions;
import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.Message;
import com.azureproject.SharedModels.User;
import com.azureproject.chatclient.Models.Response;
import com.azureproject.servicesgui.ListPrinter;
import com.azureproject.servicesgui.ListRemover;

import javafx.concurrent.Task;
import javafx.scene.control.ListView;

public class InputWorker extends Task<Void> {

    ObjectInputStream input;
    BlockingQueue<Task<Void>> queue;
    public static ConcurrentHashMap<Integer, Response> responseList = new ConcurrentHashMap<>();
    // Queue for modifying gui
    public BlockingQueue<String> queuePrintUserList = new LinkedBlockingQueue<>();
    public BlockingQueue<String> queueRemoveUserList = new LinkedBlockingQueue<>();

    public BlockingQueue<String> queuePrintMessagesSent = new LinkedBlockingQueue<>();
    public BlockingQueue<String> queueRemoveMessagesSent = new LinkedBlockingQueue<>();
    // References to elements in interface
    public ListView<String> chatListView;
    public ListView<String> messageListView;
    // Printer element that handles changes
    public ListPrinter<String> chatListPrinter;
    public ListPrinter<String> chatMessagePrinter;

    public ListRemover<String, String> chatMessageRemover;
    public ListRemover<String, String> chatListRemove;

    public String activeChat;

    public String getActiveChat() {
        return activeChat;
    }

    public void setActiveChat(String activeChat) {
        this.activeChat = activeChat;
    }

    public static InputWorker worker;
    // public static BlockingQueue<String> queueRemoveUserList = new
    // LinkedBlockingQueue<>();
    // public static BlockingQueue<String> queuePrintServerLogs = new
    // LinkedBlockingQueue<>();
    // public static BlockingQueue<String> queuePrintCountUsers = new
    // LinkedBlockingQueue<>();

    public ListView<String> getChatListView() {
        return chatListView;
    }

    public void setChatListView(ListView<String> chatListView) {
        chatListPrinter = new ListPrinter<>(chatListView, queuePrintUserList);
        chatListRemove = new ListRemover<>(chatListView, queueRemoveUserList);
        new Thread(chatListPrinter).start();
        new Thread(chatListRemove).start();
        this.chatListView = chatListView;
    }

    public ListView<String> getMessageListView() {
        return messageListView;
    }

    public void setMessageListView(ListView<String> messageListView) {
        chatMessagePrinter = new ListPrinter<>(messageListView, queuePrintMessagesSent);
        new Thread(chatMessagePrinter).start();
        this.messageListView = messageListView;
    }

    public InputWorker(ObjectInputStream input) {
        this.input = input;
    }

    public InputWorker() {
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("Login stuff");

        new Thread(chatListPrinter).start();
        while (true) {
            try {
                System.out.println("reading stuff");
                AppMessage a = (AppMessage) input.readObject();
                System.out.println("server has answered");
                System.out.println("creating task for answer from server");
                new Thread(new Task<Void>() {
                    @Override
                    protected Void call() {
                        EnumActions action = a.getAction();
                        switch (EnumActions.toAction(action)) {
                            case LOGIN_ATTEMPT:
                            case GET_MAIN_SCREEN_DATA:
                            case GET_CHAT_DATA:
                                fetchResponse(a);
                                break;
                            case NEW_MESSAGE_PEER:
                                Message newMessage = (Message) a.getDataMessage();
                                String messagePrint = newMessage.getFrom().getUsername().concat(": ")
                                        .concat(newMessage.getContent());
                                System.out.println("chat reciever 1:" + newMessage.getChatReciever().getName());
                                System.out.println("chat reciever 2:"
                                        + newMessage.getFrom().getUsername());
                                if (activeChat.equals(newMessage.getChatReciever().getName())
                                        || activeChat.equals(newMessage.getFrom().getUsername())) {
                                    chatMessagePrinter.queue.add(messagePrint);
                                }
                                break;
                            case NEW_USER:
                                User newUser = (User) a.getDataMessage();
                                chatListPrinter.queue.add(newUser.getUsername());
                                break;
                            case LOG_OUT:
                                User userDisconnected = (User) a.getDataMessage();
                                chatListRemove.queue.add(userDisconnected.getUsername());
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
