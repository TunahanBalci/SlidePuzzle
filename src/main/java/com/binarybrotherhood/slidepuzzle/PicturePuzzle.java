package com.binarybrotherhood.slidepuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PicturePuzzle extends Application {


    private static Image inputImage = new Image("file:path/to/your/image.jpg");

    public static void assignImage(Image input){
        inputImage = input;
    }

    public static Image getImage(){
        return inputImage;
    }



    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(PicturePuzzleController.class.getResource("PicturePuzzle.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Resolution.width() * 0.75, Resolution.height() * 0.75);

        PicturePuzzleController controller = fxmlLoader.getController();

        controller.getPuzzleImage().setTranslateX(Resolution.width() * 0.5);

        stage.setTitle("Picture Puzzle");
        stage.setScene(scene);
        stage.show();
    }
}