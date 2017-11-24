package com.ucd.comp41690.team21.zenze.game.components;

import android.graphics.Bitmap;

/**
 * represents specific behaviour and state of game objects
 */
public class Type {
    public int health;
    public float speed;
    public float scale;
    private Bitmap image;

    public Type(int health, float speed, float scale, Bitmap image){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.image = image;
    }

    public Bitmap getImage(){
        return image;
    }
}
