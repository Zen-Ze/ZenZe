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

    private Bitmap tileImage;
    private Bitmap backgroundImage;
    private Bitmap playerImage;
    private Bitmap enemyImage;


    public GameState(int background, Bitmap tileImage, Bitmap backgroundImage, Bitmap playerImage, Bitmap enemyImage){
        this.background = background;
        this.tileImage = tileImage;
        this.backgroundImage = backgroundImage;
        this.playerImage = playerImage;
        this.enemyImage = enemyImage;
    }

    public int getBackground() {
        return background;
    }

    public Bitmap getTileImage() {
        return tileImage;
    }

    public Bitmap getBackgroundImage() {
        return backgroundImage;
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }

    public Bitmap getEnemyImage() {
        return enemyImage;
    }
}
