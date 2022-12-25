package com.azureproject.chatclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedEnum.EnumActions;
import com.azureproject.SharedInterfaces.Chat;
import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.Message;
import com.azureproject.SharedModels.PeerChat;
import com.azureproject.SharedModels.User;
import com.azureproject.SharedModels.UsersList;
import com.azureproject.chatclient.Models.Response;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MainSceneController implements Initializable {
    @FXML
    public ListView<String> activeUsers = new ListView<>();
    @FXML
    public ListView<String> sentMessages = new ListView<>();
    @FXML
    public Label activeChatName;
    @FXML
    public Button sendButton;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public Label userLabel;

    public String activeChat;
    public int activeChatID;
    public Chat activeChatBag;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        InputWorker.worker.setChatListView(activeUsers);
        InputWorker.worker.setMessageListView(sentMessages);
        userLabel.setText("Welcome: ".concat(App.getUserSession().getUsername()).concat("!"));
        activeUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1,
                    String arg2) {
                // TODO Auto-generated method stub
                sentMessages.getItems().clear();
                activeChat = activeUsers.getSelectionModel().getSelectedItem();
                activeChatName.setText(activeChat);

                new Thread(new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        try {
                            AppMessage message;
                            PeerChat data = new PeerChat();
                            User usuario = new User();
                            usuario.setUsername(activeChat);
                            ArrayList<User> users = new ArrayList<>();
                            users.add(usuario);
                            users.add(App.getUserSession());
                            data.setUsers(users);
                            message = new AppMessage(EnumActions.GET_CHAT_DATA, data, 0, "pending");
                            CountDownLatch latch = new CountDownLatch(1);
                            Boolean isError = false;
                            System.out.println("Adding task to OutputWorker queue 1");
                            OutputWorker.queue.put(new WriteTask(message, isError, latch));
                            System.out.println("Creatin response 1");
                            Response responseResult = new Response(latch, message);
                            System.out.println("Adding responseResult to ResponseList 1");
                            InputWorker.addResponseWait(responseResult);
                            System.out.println("Waiting for response to finish ... 1");
                            latch.await();
                            if (isError) {
                                System.out.println("THERE IS AN ERROR 1");
                            }
                            System.out.println("Getting response data 1");
                            AppMessage response = responseResult.getResponseData();
                            System.out.println("parsing stuff 1");
                            activeChatBag = (PeerChat) response.getDataMessage();
                            System.out.println("YESS 1");
                            System.out.println(activeChatBag.getUsers().toString());
                            activeChatBag.setName(activeChat);
                            InputWorker.worker.setActiveChat(activeChat);
                            if ("ok".equals(response.getStatus())) {
                                Platform.runLater(() -> {
                                    try {
                                        // Aqui añadimos los mensajes previos también
                                        // sentMessages.getItems().addAll(responseData.getUsers());
                                        // sentMessages.getItems().addAll(responseData.getUsers());

                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                });
                            } else {
                                AlertBox.display("Error", "Algo ha salido mal");
                            }

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }

                }).run();
            }

        });
        new Thread(new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                try {
                    AppMessage message;
                    UsersList data;

                    data = new UsersList();
                    message = new AppMessage(EnumActions.GET_MAIN_SCREEN_DATA, data, 0, "pending");
                    CountDownLatch latch = new CountDownLatch(1);
                    Boolean isError = false;
                    System.out.println("Adding task to OutputWorker queue 1");
                    OutputWorker.queue.put(new WriteTask(message, isError, latch));
                    System.out.println("Creatin response 1");
                    Response responseResult = new Response(latch, message);
                    System.out.println("Adding responseResult to ResponseList 1");
                    InputWorker.addResponseWait(responseResult);
                    System.out.println("Waiting for response to finish ... 1");
                    latch.await();
                    if (isError) {
                        System.out.println("THERE IS AN ERROR 1");
                    }
                    System.out.println("Getting response data 1");
                    AppMessage response = responseResult.getResponseData();
                    System.out.println("parsing stuff 1");
                    UsersList responseData = (UsersList) response.getDataMessage();

                    System.out.println("YESS 1");
                    if ("ok".equals(response.getStatus())) {
                        Platform.runLater(() -> {
                            try {
                                activeUsers.getItems().addAll(responseData.getUsers());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        });
                    } else {
                        AlertBox.display("Error", "Algo ha salido mal");
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

        }).run();

    }

    @FXML
    public void sendMessageServer() {
        String messageText = messageTextArea.getText();
        if (messageText.isBlank()) {
            return;
        }
        Message message = new Message(activeChatBag, App.getUserSession(), messageText);
        AppMessage appPackage = new AppMessage(EnumActions.NEW_MESSAGE_PEER, message, 0, messageText);
        CountDownLatch latch = new CountDownLatch(1);
        Boolean isError = false;
        System.out.println("Adding task to OutputWorker queue");
        try {
            OutputWorker.queue.put(new WriteTask(appPackage, isError, latch));
            messageTextArea.setText("");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    public void selectChat() {

    }
}
