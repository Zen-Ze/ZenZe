package com.ucd.comp41690.team21.zenze.game.components;

import android.graphics.Bitmap;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * represents specific behaviour and state of game objects
 */
public class Type {

    //Tags to identify different game Objects
    public static final String PLAYER_TAG = "Player";
    public static final String PLATFORM_TAG = "Platform";
    public static final String M_PLATFORM_TAG = "PlatformMiddle";
    public static final String ITEM_TAG = "Item";
    public static final String S_ITEM_TAG = "SpecialItem";
    public static final String A_ITEM_TAG = "AttackItem";
    public static final String ENEMY_TAG = "Enemy";
    public static final String ATTACK_TAG = "Attack";

    private int health;
    private float speed;
    private float scale;
    private float damage;
    private WeatherStatus state;
    private Bitmap image;
    private int colour;
    private String[] info;
    private int DBId;
    private String tag;

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
                int colour, WeatherStatus state, String[] info, int DBid, String tag){
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.damage = damage;
        this.image = image;
        this.colour = colour;
        this.state = state;
        this.info = info;
        this.DBId = DBid;
        this.tag = tag;
    }

    public Bitmap getImage(){
        return image;
    }
    public int getColour() {return colour;}

    public WeatherStatus getState(){
        return state;
    }

    public String[] getInfo() {return info;}
    public String getName(){
        return info[0];
    }
    public String getShortInfo(){
        return info[1];
    }
    public String getFullInfo(){
        return info[2];
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

    public int getDBId(){
        return DBId;
    }

    public String getTag(){
        return tag;
    }
}
