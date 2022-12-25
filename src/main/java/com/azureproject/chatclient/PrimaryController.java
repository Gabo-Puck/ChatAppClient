package com.azureproject.chatclient;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import com.azureproject.SharedModels.AppMessage;
import com.azureproject.SharedModels.LoginData;
// import com.google.gson;

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
                File config = new File("./config.json");
                FileReader reader = new FileReader(config);
                char[] chars = new char[(int) config.length()];
                reader.read(chars);
                String json = new String(chars);
                reader.close();
                // Gson mapper = new Gson();
                // Data data = mapper.readValue(json, Data.class);
                Socket s = new Socket("localhost", 76);
                ObjectOutputStream is = new ObjectOutputStream(s.getOutputStream());
                is.flush();
                ObjectInputStream es = new ObjectInputStream(s.getInputStream());
                InputWorker.worker = new InputWorker(es);
                OutputWorker w2 = new OutputWorker();

                OutputWorker.output = is;
                OutputWorker.th = w2;
                Thread t = new Thread(InputWorker.worker);
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
