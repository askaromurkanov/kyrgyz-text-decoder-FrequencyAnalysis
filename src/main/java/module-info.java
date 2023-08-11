module com.example.kyrgyztextdecrypt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kyrgyztextdecrypt to javafx.fxml;
    exports com.example.kyrgyztextdecrypt;
}