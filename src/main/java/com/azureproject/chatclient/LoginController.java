package com.azureproject.chatclient;

import javafx.fxml.Initializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ResourceBundle;

import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.LoginData;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;

    public LoginController() {

    }

    @FXML
    private void loginAttempt() throws IOException {
        new Thread(() -> {
            try {
                Socket s = new Socket("localhost", 76);
                ObjectOutputStream is = new ObjectOutputStream(s.getOutputStream());
                is.flush();
                ObjectInputStream es = new ObjectInputStream(s.getInputStream());
                InputWorker w = new InputWorker(es);
                OutputWorker w2 = new OutputWorker(is);
                Thread t = new Thread(w);
                Thread t2 = new Thread(w2);
                t.start();
                t2.start();
                AppMessage message;
                LoginData data;

                String username = usernameField.getText();
                String password = passwordField.getText();

            } catch (IOException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

}
