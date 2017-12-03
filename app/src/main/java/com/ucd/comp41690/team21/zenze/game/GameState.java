package com.ucd.comp41690.team21.zenze.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * represents the state of the game, changes depending on the weather
 * contains important data for the state
 */
public class GameState {
    private int background;
    private Bitmap backgroundImage;
    private Bitmap enemyImage;
    private Bitmap attackImage;
    private Bitmap specialAttackImage;
    private String[] specialAttackInfo;
    private WeatherStatus status;

    /**
     * initialises a new game state
     * @param background the games background color for this weather state
     * @param backgroundImage the games background image for this weather state
     * @param enemyImage the enemy Image for this weather
     * @param attackImage the attack Image for this weather
     * @param specialAttackImage the Image for special attacks in this weather, also used for drop downs
     * @param specialAttackInfo the info text for the special attacks/drop downs
     * @param status the weather status of the game
     */
    public GameState(int background, Bitmap backgroundImage, Bitmap enemyImage,
                     Bitmap attackImage, Bitmap specialAttackImage, String[] specialAttackInfo,
                     WeatherStatus status) {
        this.background = background;
        this.backgroundImage = backgroundImage;
        this.enemyImage = enemyImage;
        this.attackImage = attackImage;
        this.specialAttackImage = specialAttackImage;
        this.specialAttackInfo = specialAttackInfo;
        this.status = status;
    }

    public int getBackground() {
        return background;
    }

    public Bitmap getBackgroundImage() {
        return backgroundImage;
    }

    public Bitmap getEnemyImage() {
        return enemyImage;
    }

    public Bitmap getAttackImage() {
        return attackImage;
    }

    public Bitmap getSpecialAttackImage() {
        return specialAttackImage;
    }

    public String[] getSpecialAttackInfo() {
        return specialAttackInfo;
    }

    public WeatherStatus getStatus() {
        return status;
    }

}
