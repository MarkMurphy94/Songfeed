package com.example.mark.SongFeed;

import java.util.ArrayList;

/**
 * Created by mark on 12/10/2017.
 */

//Potential server for metadata

public class Pie {
    int mID;
    String mName;
    String mDescription;
    double mPrice;
    boolean mFavourite;

    public Pie(){

    }

    public Pie (String name, String description, double price){
        this.mName = name;
        this.mDescription = description;
        this.mPrice = price;
        this.mFavourite = false;
    }

    public static ArrayList<Pie> makePies(){
        ArrayList<Pie> pies = new ArrayList<Pie>();
        pies.add(new Pie("Apple","An old fashioned favourite. ", 1.0));
        return pies;
    }
}
