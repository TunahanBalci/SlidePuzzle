package com.binarybrotherhood.slidepuzzle;

import javafx.scene.input.KeyCode;

import java.util.Objects;
import java.util.Set;

public class Checks {



    //START-------------------------------------------------
    // CHECK IF ALL OF THE INDEXES OF SELECTED ARRAY ALIGNS TO THE SPECIFIED ORDER
    // IN OUR CASE: 1 -> 2 -> 3 ... (8 or 15) FOR NUMBER PUZZLE

    public boolean inCorrectOrder(ComparableElements[][] input){

        // CREATE COUNT VARIABLE FOR COUNTING THE NUMBER OF TOTAL ALIGNMENTS

        int count = 0;



        // LOOP TO CHECK EACH ELEMENT OF 2D ARRAY

        for (int row = 0; row < SelectionMenu.gridSize; row++){
            for (int col = 0; col < SelectionMenu.gridSize; col++){

                // IF THE INDEX OF SELECTED ROW AND COLUMN IS 1 LOWER THAN THE INDEX OF THE NEXT COLUMN (OR ROW) ,INCREASE COUNT

                if (input[row][col + 1].index == input[row][col + 1].index + 1) {
                    count++;
                }
                else { // TO OPTIMIZE THE LOOP
                    break;
                }
                }
            }



        // IF COUNT IS INCREMENTED THE AMOUNT OF TOTAL ADJACENT INDEXES, RETURN TRUE
        // THIS MEANS THAT ALL THE NUMBERS ARE IN CORRECT ORDER

        if (count == (SelectionMenu.gridSize * SelectionMenu.gridSize - 1)) {
            return true;
        }



        // OTHERWISE, RETURN FALSE

        return false;
    }

    //END-------------------------------------------------


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
