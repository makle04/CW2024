module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.demo.controller;
    opens com.example.demo.Actors to javafx.fxml;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Views to javafx.fxml;
}