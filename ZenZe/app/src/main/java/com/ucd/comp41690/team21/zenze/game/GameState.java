package com.ucd.comp41690.team21.zenze.game;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * represents the state of the game, changes depending on the weather
 */
public class GameState {
    private int background;

    /**
     * Creates a new Game State
     * @param background the background color of this state as defined in android.graphics.Color
     *                   use eg. Color.RED
     */
    public GameState(int background){
        this.background = background;
    }

    public int getBackground() {
        return background;
    }
}
