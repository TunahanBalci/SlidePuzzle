package com.binarybrotherhood.slidepuzzle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {

    @FXML
    private Button returnButton;

    @FXML
    protected void mainMenu() {
        Stage stage = (Stage) returnButton.getScene().getWindow(); //GITHUB COPILOT

        Platform.runLater(() -> {
            try {
                Utilities.windowPos_X = stage.getX();
                Utilities.windowPos_Y = stage.getY();

                Application application = SelectionMenu.class.newInstance();
                Stage newStage = new Stage();
                application.start(newStage);
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}