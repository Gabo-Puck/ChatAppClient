package com.azureproject.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.IOException;

import com.azureproject.SharedModels.User;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static User userSession;

    public static User getUserSession() {
        return userSession;
    }

    public static void setUserSession(User userSession) {
        App.userSession = userSession;
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setOnCloseRequest(arg0 -> {
            System.out.println("Closinnnn");
            try {
                OutputWorker.output.close();
                OutputWorker.th.cancel();
                InputWorker.worker.chatListPrinter.cancel();
                InputWorker.worker.chatListRemove.cancel();
                InputWorker.worker.chatMessagePrinter.cancel();
            } catch (IOException | NullPointerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}