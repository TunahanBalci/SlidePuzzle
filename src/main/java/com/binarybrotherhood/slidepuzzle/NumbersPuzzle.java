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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;

public class NumbersPuzzle extends Application {

    public static int emptySquareIndex = 0;

    @Override
    public void start(Stage mainStage) {

        //START-------------------------------------------------
        // CREATE PANE AND SCENE + ADJUST BACKGROUND

        Pane pane = new Pane();

        pane.setStyle(" -fx-background-image: url('background.jfif'); -fx-background-position: center center;");

        Scene scene = new Scene(pane, Resolution.width() * 0.75, Resolution.height() * 0.75);;

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE ARRAYS

        Rectangle [][] backgroundCell = new Rectangle[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];

        Label [][] numbers = new Label[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];

        ComparableElements [][] twinArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Number");

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE RECTANGLESIZE VARIABLE (FOR CALCULATIONS)

        double rectangleSize = (mainStage.getWidth() / 4.0) / SelectionMenu.getGridSize();

        //END-------------------------------------------------



        //START-------------------------------------------------
        // ADJUST, ADD AND ALIGN RECTANGLES AND ADD NUMBERS

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

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

                if (row == SelectionMenu.getGridSize() - 1 && col == SelectionMenu.getGridSize() - 1){
                    numbers[row][col] = new Label(" ");
                } else {
                    numbers[row][col] = new Label(String.valueOf(twinArray[row][col].index));
                }



                // ADJUST NUMBER LABEL POSITIONS

                numbers[row][col].setAlignment(Pos.CENTER);
                numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);



                // ASSIGN INITIAL FONT TO NUMBER LABELS

                int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.getGridSize());

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

        Label header = new Label(SelectionMenu.getGridSize() + "x" + SelectionMenu.getGridSize() + " SLIDING PUZZLE");
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

        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                numbers[row][col].setAlignment(Pos.CENTER);
                numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);

                int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.getGridSize());

                numbers[row][col].setStyle(" -fx-text-fill:#2c2180; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + temp + ";");
            }}

        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR HEIGT)

        mainStage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            double dynamicSize = ((mainStage.getWidth() / 4) / SelectionMenu.getGridSize());



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE POSITION OF RECTANGLES ACCORDING TO VERTICAL WINDOW SIZE

                    backgroundCell[row][col].setTranslateY(((Resolution.spacingHeight() / Resolution.height()) * (double) newResolution) + (dynamicSize * row));



                    // CHANGE FONT SIZE OF NUMBER LABELS ACCORDING TO HORIZONTAL WINDOW SIZE

                    int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.getGridSize());

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

            double dynamicSize = (((double) newResolution / 4) / SelectionMenu.getGridSize());



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE THE SIZE OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE

                    backgroundCell[row][col].setHeight(dynamicSize);
                    backgroundCell[row][col].setWidth(dynamicSize);



                    // CHANGE POSITION OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE AND RECTANGLE SIZE

                    backgroundCell[row][col].setTranslateX((Resolution.spacingWidth / (Resolution.width() / (double) newResolution)) + (dynamicSize * col));
                    backgroundCell[row][col].setTranslateY((Resolution.spacingHeight() / (Resolution.height() / mainStage.getHeight())) + (dynamicSize * row));



                    // CHANGE FONT SIZE OF NUMBER LABELS ACCORDING TO HORIZONTAL WINDOW SIZE

                    int temp = (int) Math.round(newResolution.doubleValue() / 8.0 / (double) SelectionMenu.getGridSize());

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

            double dynamicSize = (mainStage.getWidth() / 4) / SelectionMenu.getGridSize();

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    backgroundCell[row][col].setHeight(dynamicSize);
                    backgroundCell[row][col].setWidth(dynamicSize);

                    backgroundCell[row][col].setTranslateX((Resolution.spacingWidth / (Resolution.width() / mainStage.getWidth())) + (dynamicSize * col));
                    backgroundCell[row][col].setTranslateY((Resolution.spacingHeight() / (Resolution.height() / mainStage.getHeight())) + (dynamicSize * row));


                    int temp = (int) Math.round(mainStage.getWidth() / 8.0 / (double) SelectionMenu.getGridSize());

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


        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                if (twinArray[row][col].index == -1){
                    emptySquareIndex = row * SelectionMenu.getGridSize() + col;
                }
            }
        }

        //START-------------------------------------------------
        // LISTENS FOR KEYSTROKES

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            int row_OLD = (emptySquareIndex - (emptySquareIndex % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
            int col_OLD = emptySquareIndex % SelectionMenu.getGridSize();

            boolean fullscreen = false;

            if (event.getCode() == KeyCode.H){
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
                );

                File selectedFile = fileChooser.showOpenDialog(mainStage);

                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                }
            }

            if (event.getCode() == KeyCode.F11) { // FULLSCREEN

                fullscreen = !fullscreen;

                mainStage.setFullScreen(fullscreen);
            }

            else if (event.getCode() == KeyCode.DOWN){ // SWIPE UP

                System.out.println(emptySquareIndex);

                if(row_OLD > 0){

                    emptySquareIndex -= SelectionMenu.getGridSize();

                    int row = (emptySquareIndex - (emptySquareIndex % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
                    int col = emptySquareIndex % SelectionMenu.getGridSize();

                    numbers[row_OLD][col_OLD].setText(String.valueOf(twinArray[row][col].index));

                    twinArray[row_OLD][col_OLD].index = twinArray[row][col].index;
                    twinArray[row][col].index = -1;

                    numbers[row][col].setText(" ");

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }

                if (Checks.inCorrectOrder(twinArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }


            }

            else if (event.getCode() == KeyCode.UP){ // SWIPE DOWN

                if(row_OLD < SelectionMenu.getGridSize() - 1){

                    emptySquareIndex += SelectionMenu.getGridSize();

                    int row = (emptySquareIndex - (emptySquareIndex % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
                    int col = emptySquareIndex % SelectionMenu.getGridSize();

                    numbers[row_OLD][col_OLD].setText(String.valueOf(twinArray[row][col].index));

                    twinArray[row_OLD][col_OLD].index = twinArray[row][col].index;
                    twinArray[row][col].index = -1;

                    numbers[row][col].setText(" ");

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }

                if (Checks.inCorrectOrder(twinArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }

            }

            else if (event.getCode() == KeyCode.RIGHT){ // SWIPE RIGHT

                System.out.println(emptySquareIndex);
                if (col_OLD > 0){

                    emptySquareIndex--;
                    int row = (emptySquareIndex - (emptySquareIndex % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
                    int col = emptySquareIndex % SelectionMenu.getGridSize();

                    numbers[row_OLD][col_OLD].setText(String.valueOf(twinArray[row][col].index));

                    twinArray[row_OLD][col_OLD].index = twinArray[row][col].index;
                    twinArray[row][col].index = -1;

                    numbers[row][col].setText(" ");

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }

                if (Checks.inCorrectOrder(twinArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            else if (event.getCode() == KeyCode.LEFT){ // SWIPE LEFT

                if (col_OLD < SelectionMenu.getGridSize() - 1){

                    emptySquareIndex++;
                    int row = (emptySquareIndex - (emptySquareIndex % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
                    int col = emptySquareIndex % SelectionMenu.getGridSize();

                    numbers[row_OLD][col_OLD].setText(String.valueOf(twinArray[row][col].index));

                    twinArray[row_OLD][col_OLD].index = twinArray[row][col].index;
                    twinArray[row][col].index = -1;

                    numbers[row][col].setText(" ");

                    numbers[row][col].setAlignment(Pos.CENTER);
                    numbers[row][col].setTranslateX(backgroundCell[row][col].getTranslateX() + backgroundCell[row][col].getWidth() / 2 - numbers[row][col].getWidth() / 2);
                    numbers[row][col].setTranslateY(backgroundCell[row][col].getTranslateY() + backgroundCell[row][col].getHeight() / 2 - numbers[row][col].getHeight() / 2);
                }


                if (Checks.inCorrectOrder(twinArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
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


}