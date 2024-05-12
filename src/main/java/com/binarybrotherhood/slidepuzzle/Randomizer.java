package com.binarybrotherhood.slidepuzzle;

import java.util.ArrayList;
import java.util.Random;

public class Randomizer {

    public static ElementProperties[][] getRandomArray(int limit, String type){

        while (true){

            ElementProperties[][] candidate = createRandomArray(limit, type);

            if (Checks.isSolvable(candidate)){
                return candidate;
            }
        }
    }

    private static ElementProperties[][] createRandomArray(int limit, String type){

        //START-------------------------------------------------
        // CREATE RANDOM OBJECT (FOR RANDOMIZED INDEX)

        Random random = new Random();

        //END-------------------------------------------------



        //START-------------------------------------------------
        // DECLARE REQUIRED ARRAYS

        ArrayList<Integer> initialNumbers = new ArrayList<Integer>();
        ArrayList<Integer> randomizedNumbers = new ArrayList<Integer>();

        ElementProperties[][] returnArray = new ElementProperties[limit][limit];

        //END-------------------------------------------------



        //START-------------------------------------------------
        // INITIALIZE EACH ELEMENT OF SOURCE ARRAY (DONE FOR OPTIMAL PERFORMANCE)

        for (int i = 1; i < limit * limit ; i++){
            initialNumbers.add(i);
        }

        //END-------------------------------------------------



        //START-------------------------------------------------
        for (int row = 0; row < limit; row++){
            for (int col = 0; col < limit; col++){

                if (initialNumbers.isEmpty()){
                    break;
                }

                int randomIndex = random.nextInt(initialNumbers.size());

                randomizedNumbers.add(initialNumbers.get(randomIndex));
                initialNumbers.remove(randomIndex);

                returnArray[row][col] = new ElementProperties(randomizedNumbers.get(row * limit + col), type);
            }
        }

        //END-------------------------------------------------



        //START-------------------------------------------------
        // FINALIZE THE ARRAY BY INITIALIZING ELEMENTS VIA CONSTRUCTOR

        returnArray[limit - 1][limit - 1] = new ElementProperties((0), type);

        //END-------------------------------------------------



        //START-------------------------------------------------
        // RETURN THE FINALIZED ARRAY

        return returnArray;

        //END-------------------------------------------------
    }
}
