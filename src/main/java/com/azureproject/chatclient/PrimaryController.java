package com.azureproject.chatclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.LoginData;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PrimaryController implements Initializable {
    public Button loginButton;
    public Button siginButton;

    @FXML
    private void switchToLoginView() throws IOException {
        App.setRoot("loginView");
    }

    @FXML
    private void switchToRegisterView() throws IOException {
        App.setRoot("siginView");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        new Thread(() -> {
            try {
                Socket s = new Socket("localhost", 76);
                ObjectOutputStream is = new ObjectOutputStream(s.getOutputStream());
                is.flush();
                ObjectInputStream es = new ObjectInputStream(s.getInputStream());
                InputWorker w = new InputWorker(es);
                OutputWorker w2 = new OutputWorker();

                OutputWorker.output = is;
                OutputWorker.th = w2;
                Thread t = new Thread(w);
                t.setDaemon(false);
                Thread t2 = new Thread(w2);
                t2.setDaemon(false);
                t.start();
                t2.start();

            } catch (IOException e) {

                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }
}
