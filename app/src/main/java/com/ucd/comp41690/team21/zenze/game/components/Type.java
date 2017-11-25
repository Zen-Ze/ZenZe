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
    private int colour;

    public Type(int health, float speed, float scale, Bitmap image, int colour){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.image = image;
        this.colour = colour;
    }

    public Bitmap getImage(){
        return image;
    }
    public int getColour() {return colour;}
}
