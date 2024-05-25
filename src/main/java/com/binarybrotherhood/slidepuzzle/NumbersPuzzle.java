package com.binarybrotherhood.slidepuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

public class NumbersPuzzle extends Application {

    private static boolean canPress = true;

    private static int emptySquareIndex = 0;
    private static int emptySquareRow = 0;
    private static int emptySquareCol = 0;

    private Random random = new Random();

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();

        Image squareImage = Utilities.createPlaceholderImage(100).getImage();


        ImageView [] buttons = new ImageView[1];
        Label [] buttonTexts = new Label[1];

        try {

            squareImage = new Image(PicturePuzzle.class.getResourceAsStream("/square.png"));
        } catch (Exception e) {

            System.out.println("ERROR: Couldn't load square image: " + e.getMessage());
        }

        buttons[0] = new ImageView(squareImage);

        buttonTexts[0] = new Label("SHUFFLE");
        buttonTexts[0].setStyle(" -fx-text-fill:#000000; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + 20 + ";");



//        Media soundResource_swipe = null;
//        Media soundResource_swipe2 = null;
//
//        soundResource_swipe = new Media(new File("src/main/resources/swipe.mp3").toURI().toString());
//        soundResource_swipe2 = new Media(new File("src/main/resources/swipe_2.mp3").toURI().toString());
//
//        MediaPlayer sound_swipe = new MediaPlayer(soundResource_swipe);
//        MediaPlayer sound_swipe2 = new MediaPlayer(soundResource_swipe2);
//
//        sound_swipe.setVolume(0.5);
//        sound_swipe2.setVolume(0.5);
//

// Get the URI of the sound file
        String soundFileURI = getClass().getResource("/senVeBen.mp3").toString();

// Create a Media object
        Media sound = new Media(soundFileURI);

// Create a MediaPlayer object
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

// Play the sound
        mediaPlayer.play();



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



        ImageView [][] squares = new ImageView[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];
        Label [][] numbers = new Label[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];
        ElementProperties[][] ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Number");



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


        alignPosition(stage, squares);
        setLabels(stage, numbers, squares);
        cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
        cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);

        buttons[0].setFitWidth(getSize(stage) / 4.0 * 3.0);
        buttons[0].setFitHeight(getSize(stage) / 16.0 * 3.0);
        buttons[0].setTranslateX(stage.getWidth() / 4 - buttons[0].getFitWidth() / 2);
        buttons[0].setTranslateY(stage.getHeight() / 2 - buttons[0].getFitHeight() / 2);

        DropShadow ds = new DropShadow(); //CSS EFFECT (DROPSHADOW)
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#0d2e36"));

        Label header = new Label(SelectionMenu.getGridSize() + "x" + SelectionMenu.getGridSize() + " SLIDING PUZZLE");
        header.setStyle(" -fx-text-fill:#9fd5c1; -fx-font-family: 'Papyrus'; -fx-font-size: " + 60 + ";");

        header.setEffect(ds);

        pane.getChildren().add(header);

        for (int i = 0; i < buttons.length; i++){
            pane.getChildren().add(buttons[i]);
            pane.getChildren().add(buttonTexts[i]);
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

            alignButtonPosition(stage, buttons);
            alignButtonSize(stage, buttons);
            setButtonTexts(stage, buttons, buttonTexts);

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

            alignButtonPosition(stage, buttons);
            alignButtonSize(stage, buttons);
            setButtonTexts(stage, buttons, buttonTexts);

            setHeader(stage, header, squares);

        });



        Timeline reAlignElements = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {

            alignSize(stage, squares);
            alignPosition(stage, squares);
            setLabels(stage, numbers, squares);
            cellBackground.setTranslateX(squares[0][0].getTranslateX() - getSize(stage) * 0.1);
            cellBackground.setTranslateY(squares[0][0].getTranslateY() - getSize(stage) * 0.1);
            cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
            cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);

            alignButtonSize(stage, buttons);
            alignButtonPosition(stage, buttons);
            setButtonTexts(stage, buttons, buttonTexts);
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


//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);


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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);

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

//                        if (SelectionMenu.sounds) randomSwipeSound(sound_swipe, sound_swipe2);


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


    private void alignButtonPosition (Stage stage, ImageView[] buttons) {

        buttons[0].setTranslateX(stage.getWidth() / 4 - buttons[0].getFitWidth() / 2);
        buttons[0].setTranslateY(stage.getHeight() / 2 - buttons[0].getFitHeight() / 2);

    }
    private void alignButtonSize (Stage stage, ImageView[] buttons) {

        buttons[0].setFitWidth(getSize(stage) / 4 * 3);
        buttons[0].setFitHeight(getSize(stage) / 2);

    }

    private void setButtonTexts(Stage stage, ImageView[] buttons, Label[] buttonTexts){

        for (int i = 0; i < buttons.length; i++){

            double size_double = (stage.getWidth() / 96.0);

            int size = (int) (size_double - (size_double % 1.0));
            buttonTexts[i].setStyle(" -fx-text-fill:#47321e; -fx-font-family: 'Tw Cen MT Condensed Extra Bold'; -fx-font-size: " + size + ";");

            buttonTexts[i].setTranslateX(buttons[i].getTranslateX() + buttons[i].getFitWidth() / 2 - buttonTexts[i].getWidth() / 2);
            buttonTexts[i].setTranslateY(buttons[i].getTranslateY() + buttons[i].getFitHeight() / 2 - buttonTexts[i].getHeight() / 2);

        }
    }

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

    private void randomSwipeSound (MediaPlayer swipe, MediaPlayer swipe_2) { //github copilot

        if (random.nextBoolean()) {

            swipe.play();
        } else {

            swipe_2.play();
        }
    }
}