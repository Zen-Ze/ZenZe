package com.ucd.comp41690.team21.zenze.game.components;

import android.graphics.Bitmap;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * represents specific behaviour and state of game objects
 */
public class Type {
    public int health;
    public float speed;
    public float scale;
    private WeatherStatus state;
    private Bitmap image;
    private int colour;

    public Type(int health, float speed, float scale, Bitmap image, int colour, WeatherStatus state){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.image = image;
        this.colour = colour;
        this.state = state;
    }

    public Bitmap getImage(){
        return image;
    }
    public int getColour() {return colour;}

    public WeatherStatus getState(){
        return state;
    }
}
