package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;

/**
 * Main Activity that launches on start
 * contains the game's Main Menu
 */
public class MainMenuActivity extends Activity {

    private final String graphicsOption = "GRAPHICS_OPTION";
    private final String weatherOption = "WEATHER_OPTION";
    String gfx;
    String weather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );

        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main_menu );
        // Buttons for this activity
        final Button helpButton = findViewById( R.id.help_button );
        final Button startButton = findViewById( R.id.start_button );
        final Button settingsButton = findViewById( R.id.setting_button );

        // Start game
        startButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startGame( v );
            }
        } );

        // Start help
        helpButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startHelp( v );
            }
        } );

        // Start settings
        settingsButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                startSetting( v );
            }
        } );


        // testing
        gfx = pref.getString( weatherOption, "null" );
        weather = String.valueOf( pref.getBoolean( graphicsOption, true ) );
        Log.i( "weather_setting", gfx );
        Log.i( "graphics_setting", weather );

    }

    /**
     * is called when start-button is clicked
     *
     * @param v
     */

    // Intent to start game activity
    public void startGame(View v) {
        Intent gameIntent = new Intent( MainMenuActivity.this, GameActivity.class );
        // TODO: Get Location from GPS, and change null to a Location
        gameIntent.putExtra( "Game State", WeatherService.getWeatherStatus( null, getApplicationContext() ) );
        startActivity( gameIntent );
    }

    // Intent to start help activity
    public void startHelp(View v) {
        Intent helpIntent = new Intent( MainMenuActivity.this, Help.class );
        helpIntent.putExtra( "graphics_settings", gfx );
        helpIntent.putExtra( "weather_settings", weather );
        startActivity( helpIntent );
    }

    // Intent to start setting activity
    public void startSetting(View v) {
        Intent settingIntent = new Intent( MainMenuActivity.this, Setting.class );
        startActivity( settingIntent );
    }
}
