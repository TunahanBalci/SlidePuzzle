package com.binarybrotherhood.slidepuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class SelectionMenu extends Application {

    private static int gridSize = 3;
    public static boolean animations = true;
    public static int gameModeIndex;
    private static boolean isSelectingImage = false;
    public static boolean sounds = true;
    private static Scene sc;

    public static void setGridSize(int input) {
        gridSize = input;
    }

    public static int getGridSize() {
        return gridSize;
    }

    public static void setIsSelectingImage(boolean input) {
        isSelectingImage = input;
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setX(Utilities.windowPos_X);
        stage.setY(Utilities.windowPos_Y);

        Settings settings = new Settings();
        settings.initializeSettings();

        stage.setMinHeight(456);
        stage.setMinWidth(672);
        stage.setMaxHeight(456);
        stage.setMaxWidth(672);

        Scene scene = new Scene(new FXMLLoader(SelectionMenuController.class.getResource("SelectionMenu.fxml")).load(), 400, 640);
        sc = scene;

        Timeline selectFileListener = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            if (isSelectingImage) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
                );

                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    Image inputImage = new Image(selectedFile.toURI().toString());

                    File file = new File("src/main/resources/latest_image.png");
                    if (!file.exists()) {
                        System.out.println("ERROR: Latest Image could not be found. Creating new image file");
                        Utilities.createPlaceholderImage(400);
                        PicturePuzzle.assignImage(inputImage);
                    } else {
                        PicturePuzzle.assignImage(inputImage);
                    }
                }

                isSelectingImage = false;
            }
        }));

        selectFileListener.setCycleCount(Timeline.INDEFINITE);
        selectFileListener.play();

        stage.setScene(scene);
        stage.setTitle("Background Image");
        stage.show();
        stage.getIcons().add(new Image("/icon.png")); //APP ICON

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        stage.setTitle("Slide Puzzle");
        stage.setScene(scene);
        stage.show();

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            if (SelectionMenuController.getIsSelectingKey() && !(event.getCode() == KeyCode.UNDEFINED)) {
                System.out.println("Key pressed"); //DEBUG

                if (Checks.checkKeys(event.getCode(), SelectionMenuController.getKeySession())) {
                    settings.assignKey(SelectionMenuController.getKeySession(), event.getCode());
                }
            }
            event.consume();
        });
    }

    public static void setScene_LeaderBoard(Stage stage) {
        try {
            stage.setScene(new Scene((new FXMLLoader(LeaderBoardController.class.getResource("LeaderBoard.fxml"))).load(), 400, 640));
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't load LeaderBoard.fxml: " + e.getMessage());
        }
    }

    public static void setScene_Help(Stage stage) {
        try {
            stage.setScene(new Scene((new FXMLLoader(LeaderBoardController.class.getResource("Help.fxml"))).load(), 400, 640));
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't load Help.fxml: " + e.getMessage());
        }
    }

    public static void comeBack(Stage stage) {
        Scene scene = sc;
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
