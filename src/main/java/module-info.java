module com.binarybrotherhood.slidepuzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;
    requires jdk.jdi;
    requires javafx.swing;
    requires javafx.base;
    requires jdk.jshell;


    opens com.binarybrotherhood.slidepuzzle to javafx.fxml;
    exports com.binarybrotherhood.slidepuzzle;
}