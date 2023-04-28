module com.example.clientlite {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.client to javafx.fxml;
    exports com.example.client;
}