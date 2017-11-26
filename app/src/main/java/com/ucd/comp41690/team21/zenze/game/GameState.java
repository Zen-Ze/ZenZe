package com.ucd.comp41690.team21.zenze.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * represents the state of the game, changes depending on the weather
 */
public class GameState {
    private int background;
    private Bitmap backgroundImage;
    private Bitmap enemyImage;
    private Bitmap attackImage;
    private Bitmap specialAttackImage;
    private WeatherStatus status;

    public GameState(int background, Bitmap backgroundImage, Bitmap enemyImage,
                     Bitmap attackImage, Bitmap specialAttackImage, WeatherStatus status) {
        this.background = background;
        this.backgroundImage = backgroundImage;
        this.enemyImage = enemyImage;
        this.attackImage = attackImage;
        this.specialAttackImage = specialAttackImage;
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

    public WeatherStatus getStatus() {
        return status;
    }

}
