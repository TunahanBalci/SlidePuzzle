package com.binarybrotherhood.slidepuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.*;

public class PicturePuzzle extends Application {

    private static boolean canPress = true;

    private static int emptySquareIndex = 0;
    private static int emptySquareRow = 0;
    private static int emptySquareCol = 0;

    private static Image inputImage = new Image(PicturePuzzle.class.getResourceAsStream("/latest_image.png"));

    public static void assignImage(Image input){

        inputImage = input;

        String format = "png";
        File file = new File("src/main/resources/latest_image." + format);


        try {
            ImageIO.write(SwingFXUtils.fromFXImage(input, null), format, file);

            System.out.println("Image saved successfully");
        } catch (IOException e) {

            System.out.println("Couldn't save image: " + e.getMessage());
        }


    }

    public static Image getImage(){

        return inputImage;
    }

    @Override
    public void start(Stage stage) {

        //START-------------------------------------------------
        // CREATE PANE AND SCENE + ADJUST BACKGROUND


        Pane pane = new Pane();

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

        Scene scene = new Scene(pane, Utilities.width() * 0.75, Utilities.height() * 0.75);;


        ImageView mainImage = new ImageView(inputImage);


        mainImage.setFitWidth(smallestDimension(getImage()));
        mainImage.setFitHeight(smallestDimension(getImage()));

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

        ImageView cellBackground = Utilities.createCellBackground(400);
        cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
        cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
        pane.getChildren().add(cellBackground);

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

                imageCell[0][0].setTranslateX(Utilities.spacingWidth);
                imageCell[0][0].setTranslateY(Utilities.spacingHeight());

                imageCell[row][col].setTranslateX(Utilities.spacingWidth + (spacing * col));
                imageCell[row][col].setTranslateY(Utilities.spacingHeight() + (spacing * row));

                if(ghostArray[row][col].index == 0){
                    imageCell[row][col].setImage(null);
                }

                pane.getChildren().add(imageCell[row][col]);
            }
        }


        Label header = new Label(SelectionMenu.getGridSize() + "x" + SelectionMenu.getGridSize() + " SLIDING PUZZLE");
        header.setStyle(" -fx-text-fill:#9fd5c1; -fx-font-family: 'Papyrus'; -fx-font-size: " + 60 + ";");


        DropShadow ds = new DropShadow(); //CSS EFFECT (DROPSHADOW)
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#000000"));
        header.setEffect(ds);

        pane.getChildren().add(header);

        header.layoutXProperty().bind(pane.widthProperty().subtract(header.widthProperty()).divide(2));
        header.layoutYProperty().bind(pane.heightProperty().subtract(header.widthProperty()).divide(5));



        stage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE POSITION OF RECTANGLES ACCORDING TO VERTICAL WINDOW SIZE

                    imageCell[row][col].setTranslateY(((Utilities.spacingHeight() / Utilities.height()) * (double) newResolution) + (getSize(stage) * row));

                    cellBackground.setTranslateX(imageCell[0][0].getTranslateX() - getSize(stage) * 0.1);
                    cellBackground.setTranslateY(imageCell[0][0].getTranslateY() - getSize(stage) * 0.1);

                }
            }
        });



        stage.widthProperty().addListener((observableValue, oldResolution, newResolution) -> {



            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){



                    imageCell[row][col].setFitHeight(getSize(stage));
                    imageCell[row][col].setFitWidth(getSize(stage));

                    cellBackground.setFitWidth(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);
                    cellBackground.setFitHeight(getSize(stage) * SelectionMenu.getGridSize() + getSize(stage) * 0.2);

                    cellBackground.setTranslateX(imageCell[0][0].getTranslateX() - getSize(stage) * 0.1);
                    cellBackground.setTranslateY(imageCell[0][0].getTranslateY() - getSize(stage) * 0.1);




                    imageCell[row][col].setTranslateX((Utilities.spacingWidth / (Utilities.width() / (double) newResolution)) + (getSize(stage) * col));
                    imageCell[row][col].setTranslateY((Utilities.spacingHeight() / (Utilities.height() / stage.getHeight())) + (getSize(stage) * row));

                }
            }
        });

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


        stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                timeline.play();
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


        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {


            boolean fullscreen = false;


            if (event.getCode() == KeyCode.F11) { // FULLSCREEN

                fullscreen = !fullscreen;

                stage.setFullScreen(fullscreen);
            }

            else if (event.getCode() == KeyCode.DOWN && canPress){ // SWIPE UP

                if (SelectionMenu.animations){

                    Timeline switchAnimation_DOWN = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                        canPress = false;

                        imageCell[emptySquareRow - 1][emptySquareCol].setTranslateY(imageCell[emptySquareRow - 1][emptySquareCol].getTranslateY() + (getSize(stage) / 20.0));
                    }));

                    switchAnimation_DOWN.setCycleCount(20);
                    switchAnimation_DOWN.setOnFinished(e -> {

                        canPress = true;

                        imageCell[emptySquareRow - 1][emptySquareCol].setTranslateY(imageCell[emptySquareRow - 1][emptySquareCol].getTranslateY() - (getSize(stage)));

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow - 1][emptySquareCol].getImage());
                        imageCell[emptySquareRow - 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow - 1][emptySquareCol].index;
                        ghostArray[emptySquareRow - 1][emptySquareCol].index = 0;

                        setRow(emptySquareRow - 1);

                    });

                    switchAnimation_DOWN.play();
                } else {

                    if (emptySquareRow > 0){

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow - 1][emptySquareCol].getImage());
                        imageCell[emptySquareRow - 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow - 1][emptySquareCol].index;
                        ghostArray[emptySquareRow - 1][emptySquareCol].index = 0;

                        setRow(emptySquareRow - 1);
                    }
                }




                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }


            }

            else if (event.getCode() == KeyCode.UP && canPress){ // SWIPE DOWN

                if (SelectionMenu.animations){

                    Timeline switchAnimation_UP = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                        canPress = false;

                        imageCell[emptySquareRow + 1][emptySquareCol].setTranslateY(imageCell[emptySquareRow + 1][emptySquareCol].getTranslateY() - (getSize(stage) / 20.0));
                    }));

                    switchAnimation_UP.setCycleCount(20);
                    switchAnimation_UP.setOnFinished(e -> {

                        canPress = true;

                        imageCell[emptySquareRow + 1][emptySquareCol].setTranslateY(imageCell[emptySquareRow + 1][emptySquareCol].getTranslateY() + (getSize(stage)));

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow + 1][emptySquareCol].getImage());
                        imageCell[emptySquareRow + 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow + 1][emptySquareCol].index;
                        ghostArray[emptySquareRow + 1][emptySquareCol].index = 0;

                        setRow(emptySquareRow + 1);
                    });

                    switchAnimation_UP.play();


                } else {

                    if (emptySquareRow < SelectionMenu.getGridSize() - 1){

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow + 1][emptySquareCol].getImage());
                        imageCell[emptySquareRow + 1][emptySquareCol].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow + 1][emptySquareCol].index;
                        ghostArray[emptySquareRow + 1][emptySquareCol].index = 0;

                        setRow(emptySquareRow + 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }

            }

            else if (event.getCode() == KeyCode.RIGHT && canPress){ // SWIPE RIGHT

                if (SelectionMenu.animations){

                    Timeline switchAnimation_RIGHT= new Timeline(new KeyFrame(Duration.millis(10), e -> {

                        canPress = false;

                        imageCell[emptySquareRow][emptySquareCol - 1].setTranslateX(imageCell[emptySquareRow][emptySquareCol - 1].getTranslateX() + (getSize(stage) / 20.0));
                    }));

                    switchAnimation_RIGHT.setCycleCount(20);
                    switchAnimation_RIGHT.setOnFinished(e -> {

                        canPress = true;

                        imageCell[emptySquareRow][emptySquareCol - 1].setTranslateX(imageCell[emptySquareRow][emptySquareCol - 1].getTranslateX() - (getSize(stage)));

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow][emptySquareCol - 1].getImage());
                        imageCell[emptySquareRow][emptySquareCol - 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol - 1].index;
                        ghostArray[emptySquareRow][emptySquareCol - 1].index = 0;

                        setCol(emptySquareCol - 1);
                    });

                    switchAnimation_RIGHT.play();

                } else {

                    if (emptySquareCol > 0){

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow][emptySquareCol - 1].getImage());
                        imageCell[emptySquareRow][emptySquareCol - 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol - 1].index;
                        ghostArray[emptySquareRow][emptySquareCol - 1].index = 0;

                        setCol(emptySquareCol - 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            else if (event.getCode() == KeyCode.LEFT && canPress){ // SWIPE LEFT

                if (SelectionMenu.animations){

                    Timeline switchAnimation_LEFT = new Timeline(new KeyFrame(Duration.millis(10), e -> {

                        canPress = false;

                        imageCell[emptySquareRow][emptySquareCol + 1].setTranslateX(imageCell[emptySquareRow][emptySquareCol + 1].getTranslateX() - (getSize(stage) / 20.0));
                    }));

                    switchAnimation_LEFT.setCycleCount(20);
                    switchAnimation_LEFT.setOnFinished(e -> {

                        canPress = true;

                        imageCell[emptySquareRow][emptySquareCol + 1].setTranslateX(imageCell[emptySquareRow][emptySquareCol + 1].getTranslateX() + (getSize(stage)));

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow][emptySquareCol + 1].getImage());
                        imageCell[emptySquareRow][emptySquareCol + 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol + 1].index;
                        ghostArray[emptySquareRow][emptySquareCol + 1].index = 0;

                        setCol(emptySquareCol + 1);
                    });

                    switchAnimation_LEFT.play();

                } else {

                    if (emptySquareCol < SelectionMenu.getGridSize() - 1){

                        imageCell[emptySquareRow][emptySquareCol].setImage(imageCell[emptySquareRow][emptySquareCol + 1].getImage());
                        imageCell[emptySquareRow][emptySquareCol + 1].setImage(null);

                        ghostArray[emptySquareRow][emptySquareCol].index = ghostArray[emptySquareRow][emptySquareCol + 1].index;
                        ghostArray[emptySquareRow][emptySquareCol + 1].index = 0;

                        setCol(emptySquareCol + 1);
                    }
                }

                if (Checks.inCorrectOrder(ghostArray)){

                    System.out.println("CONGRATULATIONS!!!!");
                }
            }

            event.consume();
        });


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

    private int smallestDimension(Image input){

        if (input.getWidth() < input.getHeight()){
            return (int) input.getWidth();
        } else {
            return (int) input.getHeight();
        }
    }

    private static void setRow(int row){
        emptySquareRow = row;
        emptySquareIndex = row * SelectionMenu.getGridSize() + emptySquareCol;
    }
    private static void setCol(int col){
        emptySquareCol = col;
        emptySquareIndex = emptySquareRow * SelectionMenu.getGridSize() + col;
    }

}