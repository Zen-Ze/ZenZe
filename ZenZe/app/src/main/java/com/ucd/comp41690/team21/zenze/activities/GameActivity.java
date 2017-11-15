package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;

/**
 * the main game screen
 */
public class GameActivity extends Activity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make fullscreen and horizontal
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //get Display size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = Math.max(size.x, size.y);
        int height = Math.min(size.x, size.y);
        //create a new game
        WeatherStatus gameState = (WeatherStatus)(getIntent().getExtras().get("Game State"));
        game = new Game(this, width, height, gameState);
        setContentView(game.getView());
    }

    @Override
    protected void onPause(){
        super.onPause();
        game.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.onTouchEvent(event);
        return true;
    }
}
