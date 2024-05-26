package com.binarybrotherhood.slidepuzzle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.Duration;

import javafx.scene.media.*;

import java.io.File;
import java.util.Random;

public class NumbersPuzzle extends Application {

    private static boolean canPress = true;

    private static int emptySquareIndex = 0;
    private static int emptySquareRow = 0;
    private static int emptySquareCol = 0;

    private Media soundResource_swipe = null;
    private Media backgroundMusic = null;
    private Image squareImage = Utilities.createPlaceholderImage(100).getImage();
    private ElementProperties[][] ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Number");
    private ImageView [][] squares = new ImageView[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];
    private Label [][] numbers = new Label[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];
    private int timeCounter = 0;
    private ImageView [] buttons = new ImageView[4];


    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();

        try {
            backgroundMusic = new Media(new File("src/main/resources/background_music.mp3").toURI().toString());

        } catch (Exception exception) {

            System.out.println("ERROR: Couldn't load background music: " + exception.getMessage());
        }

        MediaPlayer backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setVolume(0.15);
        backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                backgroundMusicPlayer.seek(Duration.ZERO);
            }
        });
        backgroundMusicPlayer.play();

        Label [] timeLabel = new Label[1];


        try {

            squareImage = new Image(PicturePuzzle.class.getResourceAsStream("/square.png"));
        } catch (Exception e) {

            System.out.println("ERROR: Couldn't load square image: " + e.getMessage());
        }

        Image button_shuffle;
        try {

            button_shuffle = new Image(PicturePuzzle.class.getResourceAsStream("/button_shuffle.png"));
        } catch (Exception e) {

            button_shuffle = Utilities.createPlaceholderImage(100).getImage();
            System.out.println("ERROR: Couldn't load shuffle button image: " + e.getMessage());
        }

        Image button_home;
        try {

            button_home = new Image(PicturePuzzle.class.getResourceAsStream("/button_home.png"));
        } catch (Exception e) {

            button_home = Utilities.createPlaceholderImage(100).getImage();
            System.out.println("ERROR: Couldn't load home button image: " + e.getMessage());
        }

        Image button_pause;
        try {

            button_pause = new Image(PicturePuzzle.class.getResourceAsStream("/button_pause.png"));
        } catch (Exception e) {

            button_pause = Utilities.createPlaceholderImage(100).getImage();
            System.out.println("ERROR: Couldn't load pause button image: " + e.getMessage());
        }

        Image button_sound;
        try {

            button_sound = new Image(PicturePuzzle.class.getResourceAsStream("/button_sound.png"));
        } catch (Exception e) {

            button_sound = Utilities.createPlaceholderImage(100).getImage();
            System.out.println("ERROR: Couldn't load sound button image: " + e.getMessage());
        }


        buttons[0] = new ImageView(button_shuffle);
        buttons[1] = new ImageView(button_home);
        buttons[2] = new ImageView(button_pause);
        buttons[3] = new ImageView(button_sound);



//        buttonTexts[0] = new Label("SHUFFLE");
//        buttonTexts[0].setStyle(" -fx-text-fill:#47321e; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + 20 + ";");




        try {
            soundResource_swipe = new Media(new File("src/main/resources/swipe.mp3").toURI().toString());
        } catch (Exception e) {

            System.out.println("ERROR: Couldn't load sound effects: " + e.getMessage());
        }





        ImageView background;

        try {

            background = new ImageView(new Image(PicturePuzzle.class.getResourceAsStream("/background.png")));

        } catch (Exception e) {

            background = Utilities.createPlaceholderImage((int) stage.getWidth());
        }



        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(pane.widthProperty());
        background.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(background);



        ImageView cellBackground = Utilities.createCellBackground(400);
        cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
        cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
        pane.getChildren().add(cellBackground);

        Scene scene = new Scene(pane, Utilities.width() * 0.75, Utilities.height() * 0.75);;






        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                squares[row][col] = new ImageView(squareImage);

                if (row == SelectionMenu.getGridSize() - 1 && col == SelectionMenu.getGridSize() - 1){
                    squares[row][col].setImage(null);
                }

                squares[row][col].setFitWidth(getSize(stage));
                squares[row][col].setFitHeight(getSize(stage));


                if (row == SelectionMenu.getGridSize() - 1 && col == SelectionMenu.getGridSize() - 1){

                    numbers[row][col] = new Label(" ");

                } else {

                    numbers[row][col] = new Label(String.valueOf(ghostArray[row][col].index));
                }


                pane.getChildren().add(squares[row][col]);
                pane.getChildren().add(numbers[row][col]);
            }
        }

        timeLabel[0] = new Label("TIME: 0.000 seconds");
        timeLabel[0].setStyle(" -fx-text-fill:#9fd5c1; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + 48 + ";");
        timeLabel[0].setTranslateX(stage.getWidth() / 2 - timeLabel[0].getWidth() / 2);
        timeLabel[0].setTranslateY(squares[SelectionMenu.getGridSize() - 1][0].getTranslateY() + getSize(stage) * 1.5);
        pane.getChildren().add(timeLabel[0]);

        alignPosition(stage, squares);
        setLabels(stage, numbers, squares);
        cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
        cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);

        DropShadow ds = new DropShadow(); //CSS EFFECT (DROPSHADOW)
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#0d2e36"));

        Label header = new Label(SelectionMenu.getGridSize() + "x" + SelectionMenu.getGridSize() + " SLIDING PUZZLE");
        header.setStyle(" -fx-text-fill:#9fd5c1; -fx-font-family: 'Papyrus'; -fx-font-size: " + 60 + ";");

        header.setEffect(ds);

        pane.getChildren().add(header);

        for (int i = 0; i < buttons.length; i++){
            pane.getChildren().add(buttons[i]);
//            pane.getChildren().add(buttonTexts[i]);
        }

        header.scaleXProperty().bind(pane.widthProperty().divide(Utilities.width()));
        header.scaleYProperty().bind(pane.heightProperty().divide(Utilities.height()));



        stage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            alignPosition(stage, squares);
            setLabels(stage, numbers, squares);

            cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
            cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);
            cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
            cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);

            alignButtonPosition(stage, buttons, squares);
            alignButtonSize(stage, buttons);
//            setButtonTexts(stage, buttons, buttonTexts);

            setHeader(stage, header, squares);
        });


        stage.widthProperty().addListener((observableValue, oldResolution, newResolution) -> {

            alignSize(stage, squares);
            alignPosition(stage, squares);
            setLabels(stage, numbers, squares);

            cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
            cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);
            cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
            cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);

            alignButtonPosition(stage, buttons, squares);
            alignButtonSize(stage, buttons);
//            setButtonTexts(stage, buttons, buttonTexts);

            setHeader(stage, header, squares);

        });

//        buttonTexts[0].addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//
//
//                buttons[0].setEffect(new Glow(0.9));
//                event.consume();
//            }
//        });

//        buttonTexts[0].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//
//                ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Number");
//                updateLabels(numbers, ghostArray);
//                buttonPressedAnimation(buttons[0]);
//                event.consume();
//            }
//
//
//        });



        buttons[0].toFront();

        Timeline updateTime = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            timeCounter++;
            updateTimeLabel(stage, timeLabel[0]);
        }));
        updateTime.setCycleCount(Animation.INDEFINITE);
        updateTime.play();
        timeLabel[0].toFront();

        Timeline reAlignElements = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {

            alignSize(stage, squares);
            alignPosition(stage, squares);
            setLabels(stage, numbers, squares);
            cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
            cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);
            cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
            cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);

            alignButtonSize(stage, buttons);
            alignButtonPosition(stage, buttons, squares);
//            setButtonTexts(stage, buttons, buttonTexts);
            setHeader(stage, header, squares);
        }));

        reAlignElements.setCycleCount(10);
        reAlignElements.play();

        stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                reAlignElements.play();
            }
        });


        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                if (ghostArray[row][col].index == 0){
                    emptySquareIndex = row * SelectionMenu.getGridSize() + col;
                }
            }
        }

        for (int i = 0; i < buttons.length; i++){
            addPressedFunction(stage, buttons[i], backgroundMusicPlayer);
        }

        emptySquareCol = emptySquareIndex % SelectionMenu.getGridSize();
        emptySquareRow = (emptySquareIndex - emptySquareCol) / SelectionMenu.getGridSize();


        //END-------------------------------------------------

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            boolean fullscreen = false;

            if (event.getCode() == Settings.getKey_FULLSCREEN()) { // FULLSCREEN

                fullscreen = !fullscreen;

                stage.setFullScreen(fullscreen);
            }

            if (!canPress) return;

            else if (event.getCode() == Settings.getKey_DOWN()){ // SWIPE DOWN

                if (SelectionMenu.animations){

                    if (emptySquareRow > 0) {


                        if (SelectionMenu.sounds) randomSwipeSound();

                        canPress = false;

                        Timeline switchAnimation_DOWN = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                            canPress = false;

                            squares[emptySquareRow - 1][emptySquareCol].setTranslateY(squares[emptySquareRow - 1][emptySquareCol].getTranslateY() + (getSize(stage) / 20.0));
                            setLabels_SINGULAR(stage, numbers[emptySquareRow - 1][emptySquareCol], squares[emptySquareRow - 1][emptySquareCol]);
                        }));

                        switchAnimation_DOWN.setCycleCount(20);
                        switchAnimation_DOWN.setOnFinished(e -> {

                            canPress = true;

                            squares[emptySquareRow - 1][emptySquareCol].setTranslateY(squares[emptySquareRow - 1][emptySquareCol].getTranslateY() - (getSize(stage)));

                            squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow - 1][emptySquareCol].getImage());
                            squares[emptySquareRow - 1][emptySquareCol].setImage(null);

                            ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow - 1][emptySquareCol].index;
                            ghostArray[emptySquareRow - 1][emptySquareCol].index = 0;

                            Label temp = numbers[emptySquareRow][emptySquareCol];
                            numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow - 1][emptySquareCol];
                            numbers[emptySquareRow - 1][emptySquareCol] = temp;
                            numbers[emptySquareRow][emptySquareCol].toFront();

                            setRow(emptySquareRow - 1);

                        });

                        switchAnimation_DOWN.play();
                    }
                } else {

                    if (emptySquareRow > 0){

                        if (SelectionMenu.sounds) randomSwipeSound();

                        squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow - 1][emptySquareCol].getImage());
                        squares[emptySquareRow - 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow - 1][emptySquareCol].index;
                        ghostArray[emptySquareRow - 1][emptySquareCol].index = 0;

                        Label temp = numbers[emptySquareRow][emptySquareCol];
                        numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow - 1][emptySquareCol];
                        numbers[emptySquareRow - 1][emptySquareCol] = temp;
                        numbers[emptySquareRow][emptySquareCol].toFront();

                        setRow(emptySquareRow - 1);
                    }
                }




                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }


            }

            else if (event.getCode() == Settings.getKey_UP()){ // SWIPE UP

                if (SelectionMenu.animations){

                    if (emptySquareRow < SelectionMenu.getGridSize() - 1) {

                        if (SelectionMenu.sounds) randomSwipeSound();

                        canPress = false;

                        Timeline switchAnimation_UP = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                            canPress = false;

                            squares[emptySquareRow + 1][emptySquareCol].setTranslateY(squares[emptySquareRow + 1][emptySquareCol].getTranslateY() - (getSize(stage) / 20.0));
                            setLabels_SINGULAR(stage, numbers[emptySquareRow + 1][emptySquareCol], squares[emptySquareRow + 1][emptySquareCol]);
                        }));

                        switchAnimation_UP.setCycleCount(20);
                        switchAnimation_UP.setOnFinished(e -> {

                            canPress = true;

                            squares[emptySquareRow + 1][emptySquareCol].setTranslateY(squares[emptySquareRow + 1][emptySquareCol].getTranslateY() + (getSize(stage)));

                            squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow + 1][emptySquareCol].getImage());
                            squares[emptySquareRow + 1][emptySquareCol].setImage(null);

                            ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow + 1][emptySquareCol].index;
                            ghostArray[emptySquareRow + 1][emptySquareCol].index = 0;

                            Label temp = numbers[emptySquareRow][emptySquareCol];
                            numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow + 1][emptySquareCol];
                            numbers[emptySquareRow + 1][emptySquareCol] = temp;
                            numbers[emptySquareRow][emptySquareCol].toFront();

                            setRow(emptySquareRow + 1);
                        });

                        switchAnimation_UP.play();

                    }
                } else {

                    if (emptySquareRow < SelectionMenu.getGridSize() - 1){

                        if (SelectionMenu.sounds) randomSwipeSound();

                        squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow + 1][emptySquareCol].getImage());
                        squares[emptySquareRow + 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow + 1][emptySquareCol].index;
                        ghostArray[emptySquareRow + 1][emptySquareCol].index = 0;

                        Label temp = numbers[emptySquareRow][emptySquareCol];
                        numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow + 1][emptySquareCol];
                        numbers[emptySquareRow + 1][emptySquareCol] = temp;
                        numbers[emptySquareRow][emptySquareCol].toFront();

                        setRow(emptySquareRow + 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }

            }

            else if (event.getCode() == Settings.getKey_RIGHT()){ // SWIPE RIGHT

                if (SelectionMenu.animations) {

                    if (emptySquareCol > 0) {

                        if (SelectionMenu.sounds) randomSwipeSound();

                        canPress = false;

                        Timeline switchAnimation_RIGHT = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                            canPress = false;

                            squares[emptySquareRow][emptySquareCol - 1].setTranslateX(squares[emptySquareRow][emptySquareCol - 1].getTranslateX() + (getSize(stage) / 20.0));
                            setLabels_SINGULAR(stage, numbers[emptySquareRow][emptySquareCol - 1], squares[emptySquareRow][emptySquareCol - 1]);
                        }));

                        switchAnimation_RIGHT.setCycleCount(20);
                        switchAnimation_RIGHT.setOnFinished(e -> {

                            canPress = true;

                            squares[emptySquareRow][emptySquareCol - 1].setTranslateX(squares[emptySquareRow][emptySquareCol - 1].getTranslateX() - (getSize(stage)));

                            squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow][emptySquareCol - 1].getImage());
                            squares[emptySquareRow][emptySquareCol - 1].setImage(null);

                            ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol - 1].index;
                            ghostArray[emptySquareRow][emptySquareCol - 1].index = 0;

                            Label temp = numbers[emptySquareRow][emptySquareCol];
                            numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow][emptySquareCol - 1];
                            numbers[emptySquareRow][emptySquareCol - 1] = temp;
                            numbers[emptySquareRow][emptySquareCol].toFront();

                            setCol(emptySquareCol - 1);
                        });

                        switchAnimation_RIGHT.play();
                    }
                } else {

                    if (emptySquareCol > 0){

                        if (SelectionMenu.sounds) randomSwipeSound();


                        squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow][emptySquareCol - 1].getImage());
                        squares[emptySquareRow][emptySquareCol - 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol - 1].index;
                        ghostArray[emptySquareRow][emptySquareCol - 1].index = 0;

                        Label temp = numbers[emptySquareRow][emptySquareCol];
                        numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow][emptySquareCol - 1];
                        numbers[emptySquareRow][emptySquareCol - 1] = temp;
                        numbers[emptySquareRow][emptySquareCol].toFront();

                        setCol(emptySquareCol - 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            else if (event.getCode() == Settings.getKey_LEFT()){ // SWIPE LEFT

                if (SelectionMenu.animations){

                    if (emptySquareCol < SelectionMenu.getGridSize() - 1) {

                        if (SelectionMenu.sounds) randomSwipeSound();

                        canPress = false;

                        Timeline switchAnimation_LEFT = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                            canPress = false;

                            squares[emptySquareRow][emptySquareCol + 1].setTranslateX(squares[emptySquareRow][emptySquareCol + 1].getTranslateX() - (getSize(stage) / 20.0));
                            setLabels_SINGULAR(stage, numbers[emptySquareRow][emptySquareCol + 1], squares[emptySquareRow][emptySquareCol + 1]);
                        }));

                        switchAnimation_LEFT.setCycleCount(20);
                        switchAnimation_LEFT.setOnFinished(e -> {

                            canPress = true;

                            squares[emptySquareRow][emptySquareCol + 1].setTranslateX(squares[emptySquareRow][emptySquareCol + 1].getTranslateX() + (getSize(stage)));

                            squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow][emptySquareCol + 1].getImage());
                            squares[emptySquareRow][emptySquareCol + 1].setImage(null);

                            ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol + 1].index;
                            ghostArray[emptySquareRow][emptySquareCol + 1].index = 0;

                            Label temp = numbers[emptySquareRow][emptySquareCol];
                            numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow][emptySquareCol + 1];
                            numbers[emptySquareRow][emptySquareCol + 1] = temp;
                            numbers[emptySquareRow][emptySquareCol].toFront();

                            setCol(emptySquareCol + 1);
                        });

                        switchAnimation_LEFT.play();
                    }
                } else {

                    if (emptySquareCol < SelectionMenu.getGridSize() - 1){

                        if (SelectionMenu.sounds) randomSwipeSound();


                        squares[emptySquareRow][emptySquareCol].setImage(squares[emptySquareRow][emptySquareCol + 1].getImage());
                        squares[emptySquareRow][emptySquareCol + 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol + 1].index;
                        ghostArray[emptySquareRow][emptySquareCol + 1].index = 0;

                        Label temp = numbers[emptySquareRow][emptySquareCol];
                        numbers[emptySquareRow][emptySquareCol] = numbers[emptySquareRow][emptySquareCol + 1];
                        numbers[emptySquareRow][emptySquareCol + 1] = temp;
                        numbers[emptySquareRow][emptySquareCol].toFront();

                        setCol(emptySquareCol + 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            event.consume();
        });





        //START-------------------------------------------------
        // ADJUST AND SET THE STAGE


        try {

            stage.getIcons().add(new Image("/icon.png")); //APP ICON
        } catch (Exception e) {

            System.out.println("ERROR: Couldn't load icon: " + e.getMessage());

            stage.getIcons().add(Utilities.createPlaceholderImage(128).getImage());
        }



        stage.setTitle("Slide Puzzle"); // APP TITLE
        stage.setScene(scene);
        stage.show();

        stage.setMinHeight((double) Utilities.height() / 2);
        stage.setMinWidth((double) Utilities.width() / 2 );

        stage.setMaximized(false); // BREAKS THE SCREEN SOMEHOW

    }
    private double getSize(Stage stage){

        return (stage.getWidth() / 4.0) / (double) SelectionMenu.getGridSize();
    }

    private int getFontSize(Stage stage){

        return (int) Math.round(stage.getWidth() / 8.0 / (double) SelectionMenu.getGridSize());
    }
    private static void setRow(int row){
        emptySquareRow = row;
        emptySquareIndex = row * SelectionMenu.getGridSize() + emptySquareCol;
    }
    private static void setCol(int col){
        emptySquareCol = col;
        emptySquareIndex = emptySquareRow * SelectionMenu.getGridSize() + col;
    }

    private void alignPosition(Stage stage, ImageView[][] imageViews){

        if (SelectionMenu.getGridSize() % 2 == 0){
            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){


                    imageViews[row][col].setTranslateX(stage.getWidth() / 2 - getSize(stage) * SelectionMenu.getGridSize() / 2 + (getSize(stage) * col));
                    imageViews[row][col].setTranslateY(stage.getHeight() / 2 - getSize(stage) * SelectionMenu.getGridSize() / 2 + (getSize(stage) * row));
                }
            }
            return;
        }

        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){


                imageViews[row][col].setTranslateX(stage.getWidth() / 2 - getSize(stage) * ((SelectionMenu.getGridSize() - 1) / 2) + (getSize(stage) * col) - getSize(stage) / 2);
                imageViews[row][col].setTranslateY(stage.getHeight() / 2 - getSize(stage) * ((SelectionMenu.getGridSize() - 1) / 2) + (getSize(stage) * row) - getSize(stage) / 2);
            }
        }
    }

    private void alignSize(Stage stage, ImageView[][] imageViews){

        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                imageViews[row][col].setFitWidth(getSize(stage));
                imageViews[row][col].setFitHeight(getSize(stage));
            }
        }
    }

    private void setLabels(Stage stage, Label[][] labels, ImageView[][] imageViews){

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                labels[row][col].setStyle(" -fx-text-fill:#47321e; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + getFontSize(stage) + ";");

                labels[row][col].setTranslateX(imageViews[row][col].getTranslateX() + imageViews[row][col].getFitWidth() / 2 - labels[row][col].getWidth() / 2);
                labels[row][col].setTranslateY(imageViews[row][col].getTranslateY() + imageViews[row][col].getFitHeight() / 2 - labels[row][col].getHeight() / 2);
            }
        }

    }
    private void setLabels_SINGULAR(Stage stage, Label label, ImageView imageView){

        label.setStyle(" -fx-text-fill:#47321e; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + getFontSize(stage) + ";");

        label.setTranslateX(imageView.getTranslateX() + imageView.getFitWidth() / 2 - label.getWidth() / 2);
        label.setTranslateY(imageView.getTranslateY() + imageView.getFitHeight() / 2 - label.getHeight() / 2);
    }


    private void alignButtonPosition (Stage stage, ImageView[] buttons, ImageView[][] squares) {

        if (SelectionMenu.getGridSize() % 2 == 0){
            buttons[0].setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 1.75);
            buttons[0].setTranslateY(squares[0][0].getTranslateY() + getSize(stage) / 4);

            buttons[1].setTranslateX(squares[SelectionMenu.getGridSize() - 1][0].getTranslateX() - getSize(stage) * 1.75);
            buttons[1].setTranslateY(squares[SelectionMenu.getGridSize() - 1][0].getTranslateY());


        } else {
            buttons[0].setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 1.75);
            buttons[0].setTranslateY(squares[0][0].getTranslateY() + getSize(stage) / 4);

            buttons[1].setTranslateX(squares[SelectionMenu.getGridSize() - 1][0].getTranslateX() - getSize(stage) * 1.75);
            buttons[1].setTranslateY(squares[SelectionMenu.getGridSize() - 1][0].getTranslateY());

            buttons[2].setTranslateX(squares[0][SelectionMenu.getGridSize() - 1].getTranslateX() + getSize(stage) * 1.75);
            buttons[2].setTranslateY(squares[0][SelectionMenu.getGridSize() - 1].getTranslateY() + getSize(stage) / 4);

            buttons[3].setTranslateX(squares[SelectionMenu.getGridSize() - 1][SelectionMenu.getGridSize() - 1].getTranslateX() + getSize(stage) * 1.75);
            buttons[3].setTranslateY(squares[SelectionMenu.getGridSize() - 1][SelectionMenu.getGridSize() - 1].getTranslateY());
        }

    }
    private void alignButtonSize (Stage stage, ImageView[] buttons) {

        for (int i = 0; i < buttons.length; i++){
            buttons[i].setFitWidth(getSize(stage) / 4 * 3);
            buttons[i].setFitHeight(getSize(stage) / 4 * 3);
        }
    }

//    private void setButtonTexts(Stage stage, ImageView[] buttons, Label[] buttonTexts){
//
//        for (int i = 0; i < buttons.length; i++){
//
//            double size_double = (stage.getWidth() / 96.0);
//
//            int size = (int) (size_double - (size_double % 1.0));
//            buttonTexts[i].setStyle(" -fx-text-fill:#47321e; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + size + ";");
//
//            buttonTexts[i].setTranslateX(buttons[i].getTranslateX() + buttons[i].getFitWidth() / 2 - buttonTexts[i].getWidth() / 2);
//            buttonTexts[i].setTranslateY(buttons[i].getTranslateY() + buttons[i].getFitHeight() / 2 - buttonTexts[i].getHeight() / 2);
//
//        }
//    }

    private void setHeader(Stage stage, Label header, ImageView[][] squares){

        double size_double = (stage.getWidth() / 32.0);

        int size = (int) (size_double - (size_double % 1.0));

        header.setStyle(" -fx-text-fill:#9fd5c1; -fx-font-family: 'Papyrus'; -fx-font-size: " + size + ";");

        if (header.getHeight() * (3.0 / 2.0) > squares[0][0].getTranslateY()){
            header.setVisible(false);
        } else {

            header.setVisible(true);
            header.setTranslateY(squares[0][0].getTranslateY() / 2 - header.getHeight() / 2);
            header.setTranslateX(stage.getWidth() / 2 - header.getWidth() / 2);

            header.setTranslateY(squares[0][0].getTranslateY() / 2 - header.getHeight() / 2);
            header.setTranslateX(stage.getWidth() / 2 - header.getWidth() / 2);
        }
    }

    private void randomSwipeSound () {

            MediaPlayer player1 = new MediaPlayer(soundResource_swipe);
            player1.setVolume(0.15);
            player1.play();
    }

    private void buttonPressedAnimation(ImageView button){

        button.setEffect(new Glow(0.9));

        Timeline buttonPressedAnimation = new Timeline(new KeyFrame(Duration.millis(100), e -> {

            button.setEffect(null);
        }));

        buttonPressedAnimation.setCycleCount(1);
        buttonPressedAnimation.play();
    }

    private void updateLabels(Label[][] numbers, ElementProperties[][] ghostArray){

        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                if (ghostArray[row][col].index == 0){
                    numbers[row][col].setText(" ");
                } else {
                    numbers[row][col].setText(String.valueOf(ghostArray[row][col].index));
                }
            }
        }
    }
    private void updateTimeLabel(Stage stage, Label timeLabel){

        if (timeCounter > 3600000){

            int hour = (timeCounter - (timeCounter % 3600000)) / 3600000;
            int minute = (timeCounter - (timeCounter % 60000)) / 60000 - hour * 60;
            int second = (timeCounter - (timeCounter % 1000)) / 1000 - minute * 60;
            timeLabel.setText("TIME: " + hour + "h "
                    + minute + "m "
                    + second + "." + timeCounter % 1000 + "s");

        } else if (timeCounter > 60000){

            int minute = (timeCounter - (timeCounter % 60000)) / 60000;
            int second = (timeCounter - (timeCounter % 1000)) / 1000 - minute * 60;
            timeLabel.setText("TIME: " + minute + "m "
                    + second + "." + timeCounter % 1000 + "s");

        } else {

            int second = (timeCounter - (timeCounter % 1000)) / 1000;
            timeLabel.setText("TIME: " + second + "." + timeCounter % 1000 + "s");
        }

        timeLabel.setTranslateX(stage.getWidth() / 2 - timeLabel.getWidth() / 2);
        timeLabel.setTranslateY(squares[SelectionMenu.getGridSize() - 1][0].getTranslateY() + getSize(stage) + getSize(stage) / 4);
    }

    private void addPressedFunction(Stage stage, ImageView button, MediaPlayer mediaPlayer){

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                button.setEffect(new Glow(0.9));
                event.consume();
            }
        });

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                buttonFunction(stage, button, mediaPlayer);
                event.consume();

            }
        });
    }

    private void buttonFunction(Stage stage, ImageView button, MediaPlayer mediaPlayer){


        if (button == buttons[0]){
            ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Number");
            updateLabels(numbers, ghostArray);
            buttonPressedAnimation(button);
        } else if (button == buttons[1]){
            mainMenu(stage, mediaPlayer);
        } else if (button == buttons[2]){
            //PAUSE
        } else if (button == buttons[3]){
            //SOUND
        }
    }
    private void mainMenu(Stage stage, MediaPlayer mediaPlayer) {

        try {
            Application application = SelectionMenu.class.newInstance();

            Stage newStage = new Stage();
            application.start(newStage);

        } catch (Exception e) {

            e.printStackTrace();
        }
        mediaPlayer.stop();
        stage.close();
    }
}