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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;

public class PicturePuzzle extends Application {

    public static int emptySquareIndex = 0;

    private static Image inputImage = new Image("file:path/to/your/image.jpg");

    public static void assignImage(Image input){
        inputImage = input;
    }

    public static Image getImage(){
        return inputImage;
    }

    @Override
    public void start(Stage stage) {

        //START-------------------------------------------------
        // CREATE PANE AND SCENE + ADJUST BACKGROUND


        Pane pane = new Pane();

        pane.setStyle(" -fx-background-image: url('background.jfif'); -fx-background-position: center center;");

        Scene scene = new Scene(pane, Utilities.width() * 0.75, Utilities.height() * 0.75);;

        ImageView mainImage = new ImageView(inputImage);

        mainImage.setPreserveRatio(false);
        mainImage.setFitWidth(400);
        mainImage.setFitHeight(400);

        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE ARRAYS


        ElementProperties[][] ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Picture");
        ImageView[][] imageCell = Utilities.getImageCells(mainImage, ghostArray);


        //END-------------------------------------------------



        //START-------------------------------------------------
        // CREATE AND INITIALIZE RECTANGLE SIZE VARIABLE (FOR CALCULATIONS)

        //END-------------------------------------------------



        //START-------------------------------------------------
        // ADJUST, ADD AND ALIGN RECTANGLES AND ADD NUMBERS

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                double spacing;

                if (col > 0){

                    spacing = imageCell[row][col - 1].getFitWidth();
                } else if (row > 0){

                    spacing = imageCell[row - 1][SelectionMenu.getGridSize() - 1].getFitHeight();
                } else {

                    spacing = 0;
                }

                imageCell[row][col].setFitWidth(getSize(stage));
                imageCell[row][col].setFitHeight(getSize(stage));

                // CREATE RECTANGLES



                // ADJUST RECTANGLE PROPERTIES



                // ADJUST RECTANGLE POSITIONS

                imageCell[0][0].setTranslateX(Utilities.spacingWidth);
                imageCell[0][0].setTranslateY(Utilities.spacingHeight());

                imageCell[row][col].setTranslateX(Utilities.spacingWidth + (spacing * col));
                imageCell[row][col].setTranslateY(Utilities.spacingHeight() + (spacing * row));


                pane.getChildren().add(imageCell[row][col]);
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


        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR HEIGT)

        stage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE POSITION OF RECTANGLES ACCORDING TO VERTICAL WINDOW SIZE

                    imageCell[row][col].setTranslateY(((Utilities.spacingHeight() / Utilities.height()) * (double) newResolution) + (getSize(stage) * row));


                }
            }
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR WIDTH)

        stage.widthProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE THE SIZE OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE

                    imageCell[row][col].setFitHeight(getSize(stage));
                    imageCell[row][col].setFitWidth(getSize(stage));



                    // CHANGE POSITION OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE AND RECTANGLE SIZE

                    imageCell[row][col].setTranslateX((Utilities.spacingWidth / (Utilities.width() / (double) newResolution)) + (getSize(stage) * col));
                    imageCell[row][col].setTranslateY((Utilities.spacingHeight() / (Utilities.height() / stage.getHeight())) + (getSize(stage) * row));

                }
            }
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // TIMED EVENTS (FOR FIXING INITIAL LABEL POSITIONS, FOR NOW)

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    imageCell[row][col].setFitHeight(getSize(stage));
                    imageCell[row][col].setFitWidth(getSize(stage));

                    imageCell[row][col].setTranslateX((Utilities.spacingWidth / (Utilities.width() / stage.getWidth())) + (getSize(stage) * col));
                    imageCell[row][col].setTranslateY((Utilities.spacingHeight() / (Utilities.height() / stage.getHeight())) + (getSize(stage) * row));

                }
            }
        }));

        timeline.setCycleCount(20);
        timeline.play();

        //END-------------------------------------------------



        //START-------------------------------------------------
        // LISTENS FOR MAXIMIZED STAGE (TO ALIGN SCENE ELEMENTS)
        stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                timeline.play();
            }
        });

        //END-------------------------------------------------


        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                if (ghostArray[row][col].index == 0){
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

                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                }
            }

            if (event.getCode() == KeyCode.F11) { // FULLSCREEN

                fullscreen = !fullscreen;

                stage.setFullScreen(fullscreen);
            }

            else if (event.getCode() == KeyCode.DOWN){ // SWIPE UP

                System.out.println(emptySquareIndex);

                if(row_OLD > 0){
;
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }


            }

            else if (event.getCode() == KeyCode.UP){ // SWIPE DOWN

                if(row_OLD < SelectionMenu.getGridSize() - 1){

                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }

            }

            else if (event.getCode() == KeyCode.RIGHT){ // SWIPE RIGHT

                System.out.println(emptySquareIndex);
                if (col_OLD > 0){

                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            else if (event.getCode() == KeyCode.LEFT){ // SWIPE LEFT

                if (col_OLD < SelectionMenu.getGridSize() - 1){

                }


                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            event.consume();
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // ADJUST AND SET THE STAGE

        stage.getIcons().add(new Image("/icon.png")); //APP ICON
        stage.setTitle("Slide Puzzle"); // APP TITLE
        stage.setScene(scene);
        stage.show();

        stage.setMinHeight((double) Utilities.height() / 2);
        stage.setMinWidth((double) Utilities.width() / 2 );

        stage.setMaximized(false); // BREAKS THE SCREEN SOMEHOW

        //END-------------------------------------------------
    }

    private double getSize(Stage stage){

        return (stage.getWidth() / 4.0) / (double) SelectionMenu.getGridSize();
    }
}