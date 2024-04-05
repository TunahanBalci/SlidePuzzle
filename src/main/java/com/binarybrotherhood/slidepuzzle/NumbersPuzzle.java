package com.binarybrotherhood.slidepuzzle;

import java.awt.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class NumbersPuzzle extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();

        pane.setStyle(" -fx-background-image: url('background.jfif')");

        Rectangle [][] cell = new Rectangle[SelectionMenu.gridSize] [SelectionMenu.gridSize];

        int rectangleSize = (Resolution.width() / 4) / SelectionMenu.gridSize;


        for (int row = 0; row < SelectionMenu.gridSize; row++) {

            for (int col = 0; col < SelectionMenu.gridSize; col++) {

                cell[row][col] = new Rectangle(rectangleSize, rectangleSize, Color.web("#d1edd8"));

                cell[row][col].setStroke(Color.BLACK);
                cell[row][col].strokeProperty();

                cell[row][col].setArcWidth(15.0);
                cell[row][col].setArcHeight(10.0);


                pane.getChildren().add(cell[row][col]);
            }
        }

        for (int row = 0; row < SelectionMenu.gridSize; row++){
            for (int col = 0; col < SelectionMenu.gridSize; col++){

                cell[0][0].setTranslateX(Resolution.spacingWidth);
                cell[0][0].setTranslateY(Resolution.spacingHeight());

                cell[row][col].setTranslateX(Resolution.spacingWidth + (rectangleSize * col));
                cell[row][col].setTranslateY(Resolution.spacingHeight() + (rectangleSize * row));

            }
        }

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.web("#0d2e36"));

        Label myLabel = new Label(SelectionMenu.gridSize + "x" + SelectionMenu.gridSize + " SLIDING PUZZLE");
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MightySouly.TTF"), 60);
        myLabel.setFont(customFont);
        myLabel.setTextFill(Color.web("#3ebfde"));
        myLabel.setEffect(ds);

        pane.getChildren().add(myLabel);

        myLabel.layoutXProperty().bind(pane.widthProperty().subtract(myLabel.widthProperty()).divide(2));
        myLabel.layoutYProperty().bind(pane.heightProperty().subtract(myLabel.widthProperty()).divide(5));

        Scene scene = new Scene(pane, Resolution.width(), Resolution.height());
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Numbers Sliding Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}