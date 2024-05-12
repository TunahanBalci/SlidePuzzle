package com.binarybrotherhood.slidepuzzle;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Objects;

public class Checks {



    //START-------------------------------------------------
    // CHECK IF ALL OF THE INDEXES OF SELECTED ARRAY ALIGNS TO THE SPECIFIED ORDER
    // IN OUR CASE: 1 -> 2 -> 3 ... (8 or 15) FOR NUMBER PUZZLE

    public static boolean inCorrectOrder(ElementProperties[][] input) {

        ArrayList<ElementProperties> linearArray = new ArrayList<>();

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {

                linearArray.add(input[row][col]);
            }
        }

        // CREATE COUNT VARIABLE FOR COUNTING THE NUMBER OF TOTAL ALIGNMENTS

        int count = 0;

        for (int i = 0; i < linearArray.size(); i++) {

            if (i == linearArray.size() - 1) {
                break;
            }

            if (linearArray.get(i + 1).index == linearArray.get(i).index + 1){
                count++;
            }

        }

        return (count == linearArray.size() - 2);
    }
    //END-------------------------------------------------

    public static boolean isSolvable(ElementProperties[][] candidate){

        int [] linearArray = new int[SelectionMenu.getGridSize() * SelectionMenu.getGridSize() - 1];

        int inverseCount = 0;

        for (int row = 0; row < SelectionMenu.getGridSize(); row++){
            for (int col = 0; col < SelectionMenu.getGridSize(); col++){

                if (row == SelectionMenu.getGridSize() - 1 && col == SelectionMenu.getGridSize() - 1){
                    break;
                }

                linearArray[row * SelectionMenu.getGridSize() + col] = candidate[row][col].index;
            }
        }

        for (int i = 0; i < linearArray.length - 1; i++){
            if (linearArray[i + 1] < linearArray[i]){
                inverseCount++;
            }
        }

        return inverseCount % 2 == 0;
    }

    public static boolean checkKeys(KeyCode candidate, String session){

        if (
                Objects.requireNonNull(candidate) == Settings.getKey_UP()
                || Objects.requireNonNull(candidate) == Settings.getKey_DOWN()
                || Objects.requireNonNull(candidate) == Settings.getKey_RIGHT()
                || Objects.requireNonNull(candidate) == Settings.getKey_LEFT()
                || Objects.requireNonNull(candidate) == KeyCode.ESCAPE
                || Objects.requireNonNull(candidate) == KeyCode.WINDOWS
        ){
            return false;
        }
            return true;
    }
}
