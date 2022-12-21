package com.azureproject.chatclient;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BackButton {
    static String previousScene = "";
    public Button backSceneButton;

    @FXML
    protected void goBackScene() throws IOException {
        App.setRoot(previousScene);
    }

}
