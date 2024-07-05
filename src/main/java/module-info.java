module me.tunahanbalci.slidepuzzle {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.tunahanbalci.slidepuzzle to javafx.fxml;
    exports me.tunahanbalci.slidepuzzle;
}