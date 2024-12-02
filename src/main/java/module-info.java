module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.junit.jupiter.api;


    exports com.example.demo.Controller;
    opens com.example.demo.Actors to javafx.fxml;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Views to javafx.fxml;
}