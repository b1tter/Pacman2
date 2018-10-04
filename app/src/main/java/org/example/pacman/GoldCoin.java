package org.example.pacman;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * This class should contain information about a single GoldCoin.
 * such as x and y coordinates (int) and whether or not the goldcoin
 * has been taken (boolean)
 */

public class GoldCoin implements Comparable<GoldCoin>{

    private int rndY = 0;
    private int rndX = 0;
    private Bitmap img;
    private boolean taken;

    public GoldCoin(Bitmap img) {
        this.img = img.createScaledBitmap(img, 90, 90, true);
    }

    public GoldCoin() {
    }

    public int getRndX()
    {
        return rndX;
    }

    public int getRndY()
    {
        return rndY;
    }

    public void setRndX(int rndX) {
        this.rndX = rndX;
    }

    public void setRndY(int rndY) {
        this.rndY = rndY;
    }

    public Bitmap getImg() {
        return img;
    }

    public boolean isTaken(){
        return taken;
    }

    public void setTaken (boolean taken) {
        this.taken = taken;
    }

    @Override
    public int compareTo(@NonNull GoldCoin c) {
        // usually toString should not be used,
        // instead one of the attributes or more in a comparator chain
        return getRndX() - c.getRndX();
    }
}


