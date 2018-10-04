package org.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.Random;

public class Enemy {

    Game game;

    private Bitmap enm;

    public Enemy(Bitmap enm) {
        this.enm = enm.createScaledBitmap(enm, 60, 60, true);
    }
}


