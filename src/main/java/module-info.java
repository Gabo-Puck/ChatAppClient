module com.azureproject.chatclient {

    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.azureproject.chatclient to javafx.fxml;

    exports com.azureproject.chatclient;
    exports com.azureproject.SharedInterfaces;
    exports com.azureproject.SharedModels;
    exports com.azureproject.chatclient.Models;

}
