package com.azureproject.chatclient;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public Button controlButton;
    public static Stage window;

    public static void display(String title, String message) {
        window = new Stage();
        Scene scene;
        try {
            scene = new Scene(App.loadFXML("Alertbox"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    public static void OnClickControlButton() {
        window.close();
    }
}
