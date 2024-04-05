package com.binarybrotherhood.slidepuzzle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

public class Controller implements Initializable {
    @FXML
    private VBox rootVBox;  // Assuming your VBox has the fx:id "rootVBox"


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/resources/background.jpg"));

        BackgroundImage image = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Background background = new Background(image);
        rootVBox.setBackground(background);
    }
}