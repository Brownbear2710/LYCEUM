module com.example.lyceum {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;


    opens mainPackage to javafx.fxml;
    exports mainPackage;
}