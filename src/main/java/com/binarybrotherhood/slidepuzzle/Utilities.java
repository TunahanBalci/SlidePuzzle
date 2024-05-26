package com.binarybrotherhood.slidepuzzle;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.awt.*;

public class Utilities {

    static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

    static { // COPILOT
        if (graphicsEnvironment != null) {
            graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        }
        if (graphicsDevice == null) {
            throw new RuntimeException("Unable to get default screen device");
        }
    }

    public static double windowPos_X = (width() - 672.0) / 2.0;
    public static double windowPos_Y = (height() - 456.0) / 2.0;
    private static String userName;

    public static int width() {
        return graphicsDevice.getDisplayMode().getWidth();
    }

    public static void setUsername(String name) {
        userName = name;
    }

    public static String getUsername() {
        return userName;
    }

    public static int height() {
        return graphicsDevice.getDisplayMode().getHeight();
    }

    public static ImageView[][] getImageCells(ImageView origin, ElementProperties[][] ghostArray) {
        ImageView[][] output = new ImageView[SelectionMenu.getGridSize()][SelectionMenu.getGridSize()];
        PixelReader reader = PicturePuzzle.getImage().getPixelReader();
        int cellWidth = (int) origin.getFitWidth() / SelectionMenu.getGridSize();
        int cellHeight = (int) origin.getFitHeight() / SelectionMenu.getGridSize();

        for (int row = 0; row < SelectionMenu.getGridSize(); row++) {
            for (int col = 0; col < SelectionMenu.getGridSize(); col++) {
                int r = (ghostArray[row][col].index - (ghostArray[row][col].index % SelectionMenu.getGridSize())) / SelectionMenu.getGridSize();
                int c = ghostArray[row][col].index % SelectionMenu.getGridSize();
                int x = c * cellWidth;
                int y = r * cellHeight;

                WritableImage writableImage = new WritableImage(reader, x, y, cellWidth, cellHeight);
                output[row][col] = new ImageView(writableImage);
            }
        }
        return output;
    }

    public static ImageView createPlaceholderImage(int size) {
        WritableImage writableImage = new WritableImage(size, size);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixelWriter.setColor(x, y, Color.valueOf("#ff0000"));
            }
        }
        return new ImageView(writableImage);
    }

    public static ImageView createCellBackground(int size) {
        WritableImage writableImage = new WritableImage(size, size);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixelWriter.setColor(x, y, Color.valueOf("#000000"));
            }
        }

        ImageView product = new ImageView(writableImage);
        product.setOpacity(0.5);
        return product;
    }

    public static int getRecordIndex(int time, LeaderBoard leaderBoard) {
        int[] times = leaderBoard.getTime();

        for (int i = 0; i < times.length; i++) {
            if (time < times[i]) {
                return i;
            } else if (times[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void finish(int time) {
        LeaderBoard leaderBoard = new LeaderBoard().getLeaderBoard();

        if (Checks.isRecord(time, leaderBoard)) {
            int index = getRecordIndex(time, leaderBoard);
            leaderBoard.registerRecord(userName, time, index);
        }
    }
}
