package com.binarybrotherhood.slidepuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class NumbersPuzzle extends Application {

    @Override
    public void start(Stage mainStage) {;

        //START-------------------------------------------------
        // CREATE PANE AND SCENE + ADJUST BACKGROUND

        Pane pane = new Pane();

        pane.setStyle(" -fx-background-image: url('background.jfif'); -fx-background-position: center center;");

        Scene scene = new Scene(pane, Resolution.width(), Resolution.height());;

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE ARRAYS

        Rectangle [][] backgroundCell = new Rectangle[SelectionMenu.gridSize][SelectionMenu.gridSize];

        Label [][] numbers = new Label[SelectionMenu.gridSize][SelectionMenu.gridSize];

        ComparableElements [][] twinArray = Randomizer.getRandomArray(SelectionMenu.gridSize, "Number");

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE RECTANGLESIZE VARIABLE (FOR CALCULATIONS)

        double rectangleSize = (mainStage.getWidth() / 4.0) / SelectionMenu.gridSize;

        //END-------------------------------------------------



        //START-------------------------------------------------
        // ADJUST, ADD AND ALIGN RECTANGLES AND ADD NUMBERS

        for (int row = 0; row < SelectionMenu.gridSize; row++) {
            for (int col = 0; col < SelectionMenu.gridSize; col++) {

                // CREATE RECTANGLES

                backgroundCell[row][col] = new Rectangle(rectangleSize, rectangleSize, Color.web("#d1edd8"));



                // ADJUST RECTANGLE PROPERTIES

                backgroundCell[row][col].setStroke(Color.BLACK);
                backgroundCell[row][col].strokeProperty();



                // ADJUST RECTANGLE POSITIONS

                backgroundCell[0][0].setTranslateX(Resolution.spacingWidth);
                backgroundCell[0][0].setTranslateY(Resolution.spacingHeight());

                backgroundCell[row][col].setTranslateX(Resolution.spacingWidth + (rectangleSize * col));
                backgroundCell[row][col].setTranslateY(Resolution.spacingHeight() + (rectangleSize * row));



                // ASSIGN NUMBER LABELS

                if (row == SelectionMenu.gridSize - 1 && col == SelectionMenu.gridSize - 1){
                    numbers[row][col] = new Label(" ");
                } else {
                    numbers[row][col] = new Label(String.valueOf(twinArray[row][col].index));
                }



                // ADJUST NUMBER LABEL POSITIONS

                numbers[row][col].setAlignment(Pos.CENTER);
                numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);



                // ASSIGN INITIAL FONT TO NUMBER LABELS

                int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.gridSize);

                numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");



                // ADD RECTANGLES AND LABELS TO THE PANE

                pane.getChildren().add(backgroundCell[row][col]);
                pane.getChildren().add(numbers[row][col]);
            }
        }

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE, ADD AND ADJUST HEADER

        DropShadow ds = new DropShadow(); //CSS EFFECT (DROPSHADOW)
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#0d2e36"));

        Label header = new Label(SelectionMenu.gridSize + "x" + SelectionMenu.gridSize + " SLIDING PUZZLE");
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MightySouly.TTF"), 60);
        header.setFont(customFont);
        header.setTextFill(Color.web("#3ebfde"));

        header.setEffect(ds);

        pane.getChildren().add(header);

        header.layoutXProperty().bind(pane.widthProperty().subtract(header.widthProperty()).divide(2));
        header.layoutYProperty().bind(pane.heightProperty().subtract(header.widthProperty()).divide(5));

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE, ADD AND ADJUST NUMBERS

        for (int row = 0; row < SelectionMenu.gridSize; row++){
            for (int col = 0; col < SelectionMenu.gridSize; col++) {

                numbers[row][col].setAlignment(Pos.CENTER);
                numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);

                int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.gridSize);

                numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");
            }}

        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR HEIGT)

        mainStage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            double dynamicSize = ((mainStage.getWidth() / 4) / SelectionMenu.gridSize);



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.gridSize; row++){
                for (int col = 0; col < SelectionMenu.gridSize; col++){

                    // CHANGE POSITION OF RECTANGLES ACCORDING TO VERTICAL WINDOW SIZE

                    backgroundCell[row][col].setTranslateY(((Resolution.spacingHeight() / Resolution.height()) * (double) newResolution) + (dynamicSize * row));



                    // CHANGE FONT SIZE OF NUMBER LABELS ACCORDING TO HORIZONTAL WINDOW SIZE

                    int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.gridSize);

                    numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");



                    // ALIGN NUMBER LABELS WITH THE POSITION OF RECTANGLES

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
    }
        }
    });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR WIDTH)

        mainStage.widthProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            double dynamicSize = (((double) newResolution / 4) / SelectionMenu.gridSize);



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.gridSize; row++){
                for (int col = 0; col < SelectionMenu.gridSize; col++){

                    // CHANGE THE SIZE OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE

                    backgroundCell[row][col].setHeight(dynamicSize);
                    backgroundCell[row][col].setWidth(dynamicSize);



                    // CHANGE POSITION OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE AND RECTANGLE SIZE

                    backgroundCell[row][col].setTranslateX((Resolution.spacingWidth / (Resolution.width() / (double) newResolution)) + (dynamicSize * col));
                    backgroundCell[row][col].setTranslateY((Resolution.spacingHeight() / (Resolution.height() / mainStage.getHeight())) + (dynamicSize * row));



                    // CHANGE FONT SIZE OF NUMBER LABELS ACCORDING TO HORIZONTAL WINDOW SIZE

                    int temp = (int) Math.round(newResolution.doubleValue() / 8.0 / (double) SelectionMenu.gridSize);

                    numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");



                    // ALIGN NUMBER LABELS WITH THE POSITION OF RECTANGLES

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }
            }
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // TIMED EVENTS (FOR FIXING INITIAL LABEL POSITIONS, FOR NOW)

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            double dynamicSize = (mainStage.getWidth() / 4) / SelectionMenu.gridSize;

            for (int row = 0; row < SelectionMenu.gridSize; row++){
                for (int col = 0; col < SelectionMenu.gridSize; col++){

                    backgroundCell[row][col].setHeight(dynamicSize);
                    backgroundCell[row][col].setWidth(dynamicSize);

                    backgroundCell[row][col].setTranslateX((Resolution.spacingWidth / (Resolution.width() / mainStage.getWidth())) + (dynamicSize * col));
                    backgroundCell[row][col].setTranslateY((Resolution.spacingHeight() / (Resolution.height() / mainStage.getHeight())) + (dynamicSize * row));


                    int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.gridSize);

                    numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");


                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }
            }
        }));

        timeline.setCycleCount(20);
        timeline.play();

        //END-------------------------------------------------



        //START-------------------------------------------------
        // LISTENS FOR MAXIMIZED STAGE (TO ALIGN SCENE ELEMENTS)
        mainStage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                timeline.play();
            }
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // LISTENS FOR KEYSTROKES

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            boolean fullscreen = false;

            System.out.println("Key pressed"); //DEBUG

            if (event.getCode() == KeyCode.F11) {

                fullscreen = !fullscreen;
                System.out.println("F11 pressed"); //DEBUG

                mainStage.setFullScreen(fullscreen);
            }

            if (event.getCode() == KeyCode.UP){
                System.out.println("UP ARROW");
            }
            if (event.getCode() == KeyCode.DOWN){
                System.out.println("DOWN ARROW");
            }
            if (event.getCode() == KeyCode.LEFT){
                System.out.println("LEFT ARROW");
            }
            if (event.getCode() == KeyCode.RIGHT){
                System.out.println("RIGHT ARROW");
            }

            event.consume();
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // ADJUST AND SET THE STAGE

        mainStage.getIcons().add(new Image("/icon.png")); //APP ICON
        mainStage.setTitle("Slide Puzzle"); // APP TITLE
        mainStage.setScene(scene);
        mainStage.show();

        mainStage.setMinHeight((double) Resolution.height() / 2);
        mainStage.setMinWidth((double) Resolution.width() / 2 );

        mainStage.setMaximized(false); // BREAKS THE SCREEN SOMEHOW

        //END-------------------------------------------------
    }



    public static void main(String[] args) {

        //END-------------------------------------------------
        // LAUNCH THE PROGRAM

        launch(args);

        //END-------------------------------------------------
    }
}