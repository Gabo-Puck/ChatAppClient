module com.azureproject.chatclient {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.azureproject.chatclient to javafx.fxml;
    exports com.azureproject.chatclient;
}
