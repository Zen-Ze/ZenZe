package com.ucd.comp41690.team21.zenze.game.components;

/**
 * represents specific behaviour and state of game objects
 */
public class Type {
    public int health;
    public float speed;
    public float scale;
    //Bitmap

    public Type(int health, float speed, float scale){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
    }
}
