package com.binarybrotherhood.slidepuzzle;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        final Point2D[] cursorPosition = new Point2D[1];

        Pane pane = new Pane();

        pane.setStyle(" -fx-background-image: url('background.jfif'); -fx-background-position: center center;");

        Scene scene = new Scene(pane, Utilities.width() * 0.75, Utilities.height() * 0.75);;

        ImageView picture = new ImageView(inputImage);

        picture.setPreserveRatio(false);
        picture.setFitWidth(400);
        picture.setFitHeight(400);

        inputImage = picture.snapshot(null, null);





        ElementProperties[][] ghostArray = Randomizer.getRandomArray(SelectionMenu.getGridSize(), "Picture");

        ImageView [][] imageCell = Utilities.getImageCells(picture, ghostArray);

        for(int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                final int row_temp = row;
                final int col_temp = col;

                imageCell[row][col].setOnMousePressed(event -> {
                    // Store initial mouse cursor position
                    cursorPosition[0] = new Point2D(event.getSceneX(), event.getSceneY());
                });

                imageCell[row][col].setOnMouseDragged(event -> {
                    // Calculate the movement of the mouse cursor

                    double horizontalDistance = event.getSceneX() - cursorPosition[0].getX();
                    double verticalDistancec = event.getSceneY() - cursorPosition[0].getY();

                    // Move the ImageView by the amount the mouse cursor has moved
                    imageCell[row_temp][col_temp].setTranslateX(imageCell[row_temp][col_temp].getTranslateX() + horizontalDistance);
                    imageCell[row_temp][col_temp].setTranslateY(imageCell[row_temp][col_temp].getTranslateY() + verticalDistancec);

                    // Store the current mouse cursor position
                    cursorPosition[0] = new Point2D(event.getSceneX(), event.getSceneY());
                });

                imageCell[row][col].setOnMouseReleased(event -> {
                    // Handle the mouse release event
                    System.out.println("ImageView released: ");
                });

                imageCell[row][col].setOnMouseClicked(event -> {
                    // Handle the mouse click event
                    System.out.println("ImageView clicked: ");
                });

                pane.getChildren().add(imageCell[row][col]);

            }
        }




        stage.heightProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            double dynamicSize = ((stage.getWidth() / 4) / SelectionMenu.getGridSize());



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE POSITION OF RECTANGLES ACCORDING TO VERTICAL WINDOW SIZE

                    imageCell[row][col].setTranslateY(((Utilities.spacingHeight() / Utilities.height()) * (double) newResolution) + (dynamicSize * row));

                }
            }
        });

        //END-------------------------------------------------



        //START-------------------------------------------------
        // EXPERIMENTAL: DYNAMIC RESIZING (FOR WIDTH)

        stage.widthProperty().addListener((observableValue, oldResolution, newResolution) -> {

            // CREATE AND INITIALIZE DYNAMICSIZE VARIABLE (FOR DYNAMIC RECTANGLE SIZE)

            double dynamicSize = (((double) newResolution / 4) / SelectionMenu.getGridSize());



            // APPLY SPECIFIED TASKS TO ALL ARRAY ELEMENTS:

            for (int row = 0; row < SelectionMenu.getGridSize(); row++){
                for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                    // CHANGE THE SIZE OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE

                    imageCell[row][col].setFitHeight(dynamicSize);
                    imageCell[row][col].setFitWidth(dynamicSize);



                    // CHANGE POSITION OF RECTANGLES ACCORDING TO HORIZONTAL WINDOW SIZE AND RECTANGLE SIZE

                    imageCell[row][col].setTranslateX((Utilities.spacingWidth / (Utilities.width() / (double) newResolution)) + (dynamicSize * col));
                    imageCell[row][col].setTranslateY((Utilities.spacingHeight() / (Utilities.height() / stage.getHeight())) + (dynamicSize * row));
                }
            }
        });











        stage.setScene(scene);
        stage.show();


    }
}