package com.binarybrotherhood.slidepuzzle;

import java.awt.*;

public class Resolution {

    //START-------------------------------------------------
    // CREATE AND INITIALIZE GRAPHICSDEVICE AND GRAPHICSENVIRONMENT OBJECTS

    static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

    //END-------------------------------------------------



    //START-------------------------------------------------
    // GET THE WIDTH OF SCREEN (AS PIXELS)

    public static int width(){

        return graphicsDevice.getDisplayMode().getWidth();
    }

    //END-------------------------------------------------



    //START-------------------------------------------------
    // GET THE HEIGHT OF SCREEN (AS PIXELS)

    public static int height(){

        return graphicsDevice.getDisplayMode().getHeight();
    }

    //END-------------------------------------------------



    //START-------------------------------------------------
    // GET THE ASPECT RATIO OF SCREEN

    public static String ratio(){

        if ((width() / height()) == (16 / 9)) return ("16:9");
        if ((width() / height()) == (16 / 10)) return ("16:10");
        if ((width() / height()) == (4 / 3)) return ("4:3");
        if ((width() / height()) == (5 / 4)) return ("5:4");

        return null;
    }

    //END-------------------------------------------------



    //START-------------------------------------------------
    // GET THE HORIZONTAL SPACING (AS PIXELS)

    public static double spacingWidth = width() * (3.0 / 8.0);

    //END-------------------------------------------------


    //START-------------------------------------------------
    // GET THE VERTICAL SPACING (AS PIXELS)

    public static double spacingHeight(){

        ratio();

        // 4X FOR RECTANGLES, 5X FOR SPACING (TOTAL 9x)

        if (ratio().equals("16:9")){

            return height() * (5.0 / 18.0);
        }



        // 4X FOR RECTANGLES, 6X FOR SPACING (TOTAL 10x)

        if (ratio().equals("16:10")){

            return height() * (3.0 / 10.0);
        }



        // 4X FOR RECTANGLES, 8X FOR SPACING (TOTAL 12x)

        if (ratio().equals("4:3")){

            return height() * (1.0 / 3.0);
        }



        // 4X FOR RECTANGLES, 8.8X FOR SPACING (TOTAL 12.8X)

        if (ratio().equals("5:4")){ // 80:64, 20x

            return height() * (22.0 / 64.0);
        }



        // RETURN 0 IF ASPECT RATIO IS NOT RECOGNIZED

        return 0;
    }

    //END-------------------------------------------------



}
