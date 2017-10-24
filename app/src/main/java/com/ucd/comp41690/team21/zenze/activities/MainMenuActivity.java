package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;

/**
 * Main Activity that launches on start
 * contains the game Menu
 */
public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    /**
     * is called when start-button is clicked
     * @param v
     */
    public void startGame(View v){
//        WeatherService.getWeatherStatus();
        startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
    }
}
