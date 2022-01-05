module com._305.propertyassessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com._305.propertyassessment to javafx.fxml;
    exports com._305.propertyassessment;
}