package com.binarybrotherhood.slidepuzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionMenu extends Application {

    private static int gridSize = 3;
    public static boolean animations = true;
    public static int gameModeIndex;


    public static void setGridSize(int input){
        gridSize = input;
    }

    public static int getGridSize(){
        return gridSize;
    }


    @Override
    public void start(Stage stage) throws IOException {



        Settings.initializeSettings();


        FXMLLoader fxmlLoader = new FXMLLoader(SelectionMenuController.class.getResource("SelectionMenu.fxml"));

        stage.setMinHeight(456);
        stage.setMinWidth(672);
        stage.setMaxHeight(456);
        stage.setMaxWidth(672);

        StackPane root = new StackPane();
        Scene scene = new Scene(fxmlLoader.load(), 400, 640);

        scene.getStylesheets().addAll(getClass().getResource(
                "stroke.css"
        ).toExternalForm());


        Image image = new Image("file:path/to/your/image.jpg"); // Replace with your image path

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);

        root.setBackground(background);

        stage.setScene(scene);
        stage.setTitle("Background Image");
        stage.show();
        stage.getIcons().add(new Image("/icon.png")); //APP ICON

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setTitle("Slide Puzzle");
        stage.setScene(scene);
        stage.show();



        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            if (SelectionMenuController.getIsSelectingKey()){

                System.out.println("Key pressed"); //DEBUG

                if (Checks.checkKeys(event.getCode(), SelectionMenuController.getKeySession())){

                    Settings.assignKey(SelectionMenuController.getKeySession(), event.getCode());
                }

            }
            event.consume();
        });
    }



    public static void main(String[] args) {

        //END-------------------------------------------------
        // LAUNCH THE PROGRAM

        launch(args);

        //END-------------------------------------------------
    }
}
