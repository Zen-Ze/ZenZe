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
    private Bitmap itemImage;
    private Bitmap attackImage;
    private Bitmap enemyAttackImage;


    public GameState(int background, Bitmap tileImage, Bitmap backgroundImage,
                     Bitmap playerImage, Bitmap enemyImage, Bitmap itemImage,
                     Bitmap attackImage, Bitmap enemyAttackImage){
        this.background = background;
        this.tileImage = tileImage;
        this.backgroundImage = backgroundImage;
        this.playerImage = playerImage;
        this.enemyImage = enemyImage;
        this.itemImage = itemImage;
        this.attackImage = attackImage;
        this.enemyAttackImage = enemyAttackImage;
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

    public Bitmap getItemImage() {
        return itemImage;
    }

    public Bitmap getAttackImage() {
        return attackImage;
    }

    public Bitmap getEnemyAttackImage() {
        return enemyAttackImage;
    }

}
