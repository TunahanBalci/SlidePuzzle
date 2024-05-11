package com.binarybrotherhood.slidepuzzle;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class PicturePuzzleController {

    public PicturePuzzleController() {
        System.out.println("PicturePuzzleController created");
    }

    @FXML
    private ImageView puzzleImage;

    public ImageView getPuzzleImage(){
        return puzzleImage;
    }

    public void initialize(){
        System.out.println("PicturePuzzleController initialized");

        puzzleImage.setImage(PicturePuzzle.getImage());
    }
}