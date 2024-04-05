package com.binarybrotherhood.slidepuzzle;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;

public class Resolution {

    static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    static GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();


    public static int width(){
        return graphicsDevice.getDisplayMode().getWidth();
    }

    public static int height(){
        return graphicsDevice.getDisplayMode().getHeight();
    }

    public static String ratio(){

        if ((width() / height()) == (16 / 9)) return ("16:9");
        if ((width() / height()) == (16 / 10)) return ("16:10");
        if ((width() / height()) == (4 / 3)) return ("4:3");
        if ((width() / height()) == (5 / 4)) return ("5:4");

        return null;
    }

    public static double spacingWidth = width() * (3.0 / 8.0);

    public static double spacingHeight(){

        ratio();

        if (ratio().equals("16:9")){ // 4x
            return height() * (5.0 / 18.0);
        }


        if (ratio().equals("16:10")){ //4x
            return height() * (3.0 / 10.0);
        }


        if (ratio().equals("4:3")){ // 16:12 , 4x
            return height() * (1.0 / 3.0);
        }


        if (ratio().equals("5:4")){ // 80:64, 20x
            return height() * (22.0 / 64.0);
        }

        return 0;
    }
}
