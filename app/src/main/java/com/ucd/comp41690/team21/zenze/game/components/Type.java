package com.ucd.comp41690.team21.zenze.game.components;

import android.graphics.Bitmap;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * represents specific behaviour and state of game objects
 */
public class Type {
    private int health;
    private float speed;
    private float scale;
    private float damage;
    private WeatherStatus state;
    private Bitmap image;
    private int colour;
    private String info;

    /**
     *
     * @param health
     * @param speed
     * @param scale
     * @param image
     * @param colour
     * @param state
     * @param info
     */
    public Type(int health, float speed, float scale, float damage, Bitmap image,
                int colour, WeatherStatus state, String info){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.damage = damage;
        this.image = image;
        this.colour = colour;
        this.state = state;
        this.info = info;
    }

    public Bitmap getImage(){
        return image;
    }
    public int getColour() {return colour;}

    public WeatherStatus getState(){
        return state;
    }

    public String getInfo(){
        return info;
    }
    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public float getScale() {
        return scale;
    }

    public float getDamage() {
        return damage;
    }
}
