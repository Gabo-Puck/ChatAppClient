package com.azureproject.chatclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    }
}
