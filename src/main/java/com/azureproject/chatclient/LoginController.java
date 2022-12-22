package com.azureproject.chatclient;

import javafx.fxml.Initializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.LoginData;
import com.azureproject.chatclient.Models.Response;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    private final class TaskExtension extends Task<Object> {
        private final AppMessage message;
        Boolean isError;
        CountDownLatch latch;

        private TaskExtension(AppMessage message, Boolean isError, CountDownLatch latch) {
            this.message = message;
            this.isError = isError;
            this.latch = latch;
        }

        @Override
        protected Void call() {
            try {
                OutputWorker.output.writeObject(message);
            } catch (IOException e) {
                isError = true;
                this.latch.countDown();
            }
            return null;
        }
    }

    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;

    public LoginController() {

    }

    @FXML
    private void loginAttempt() throws IOException {
        new Thread(new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                try {
                    AppMessage message;
                    LoginData data;

                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    data = new LoginData(username, password, "");
                    message = new AppMessage("LoginAttempt", data, 0);
                    CountDownLatch latch = new CountDownLatch(1);
                    Boolean isError = false;
                    System.out.println("Adding task to OutputWorker queue");
                    OutputWorker.queue.put(new TaskExtension(message, isError, latch));
                    System.out.println("Creatin response");
                    Response responseResult = new Response(latch, message);
                    System.out.println("Adding responseResult to ResponseList");
                    InputWorker.addResponseWait(responseResult);
                    System.out.println("Waiting for response to finish ...");
                    latch.await();
                    if (isError) {
                        System.out.println("THERE IS AN ERROR");

                    }
                    System.out.println("Getting response data");
                    AppMessage response = responseResult.getResponseData();
                    System.out.println("parsing stuff");
                    LoginData responseData = (LoginData) response.getDataMessage();

                    if ("ok".equals(responseData.getMessage())) {
                        System.out.println("YESS");
                        Platform.runLater(() -> {
                            try {
                                App.setRoot("mainView");
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

        }).run();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

}
