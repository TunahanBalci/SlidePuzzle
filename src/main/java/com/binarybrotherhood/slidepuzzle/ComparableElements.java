package com.binarybrotherhood.slidepuzzle;

public class ComparableElements {

    //START-------------------------------------------------
    // FOR THE INDEX (NUMERICAL VALUE) OF LABELS
    // WILL BE CHECKED FOR ADJACENCY

    public int index;

    //END--------------------------------------------------




    //START-------------------------------------------------
    // FOR THE TYPE OF LABELS (PICTURE, NUMBER OR OTHER)
    // WILL BE CHECKED TO DETERMINE PUZZLE TYPE

    public String type;

    //END--------------------------------------------------



    //START-------------------------------------------------

    // CONSTRUCTOR (INITIALIZES TYPE AND INDEX)

    public ComparableElements (int index, String type){

        this.index = index;
        this.type = type;
    }

    //END--------------------------------------------------

}
