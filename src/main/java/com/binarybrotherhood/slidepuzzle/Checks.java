package com.binarybrotherhood.slidepuzzle;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Objects;

public class Checks {

    public static boolean inCorrectOrder(ElementProperties[][] input) {
        ArrayList<ElementProperties> linearArray = new ArrayList<>();

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {
                linearArray.add(input[row][col]);
            }
        }

        int count = 0;

        for (int i = 0; i < linearArray.size(); i++) {
            if (i == linearArray.size() - 1) {
                break;
            }
            if (linearArray.get(i + 1).index == linearArray.get(i).index + 1) {
                count++;
            }
        }
        return (count == linearArray.size() - 2);
    }

    public static boolean isSolvable(ElementProperties[][] candidate) { //CHATGPT
        int gridSize = SelectionMenu.getGridSize();
        int[] linearArray = new int[gridSize * gridSize];
        int inverseCount = 0;
        int blankRow = 0;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                linearArray[row * gridSize + col] = candidate[row][col].index;
                if (candidate[row][col].index == 0) {
                    blankRow = row;
                }
            }
        }

        for (int i = 0; i < linearArray.length - 1; i++) {
            for (int j = i + 1; j < linearArray.length; j++) {
                if (linearArray[i] != 0 && linearArray[j] != 0 && linearArray[i] > linearArray[j]) {
                    inverseCount++;
                }
            }
        }

        if (gridSize % 2 == 1) {
            return inverseCount % 2 == 0;
        } else {
            int blankRowFromBottom = gridSize - blankRow;
            if (blankRowFromBottom % 2 == 0) {
                return inverseCount % 2 == 1;
            } else {
                return inverseCount % 2 == 0;
            }
        }
    }

    public static boolean checkKeys(KeyCode candidate, String session) {
        if (
                Objects.requireNonNull(candidate) == Settings.getKey_UP()
                        || Objects.requireNonNull(candidate) == Settings.getKey_DOWN()
                        || Objects.requireNonNull(candidate) == Settings.getKey_RIGHT()
                        || Objects.requireNonNull(candidate) == Settings.getKey_LEFT()
                        || Objects.requireNonNull(candidate) == KeyCode.ESCAPE
                        || Objects.requireNonNull(candidate) == KeyCode.WINDOWS
        ) {
            return false;
        }
        return true;
    }

    public static boolean isRecord(int time, LeaderBoard leaderBoard) {
        int[] times = leaderBoard.getTime();

        for (int i = 0; i < times.length; i++) {
            if (time < times[i]) {
                return true;
            } else if (times[i] == 0) {
                return true;
            }
        }
        return false;
    }

}