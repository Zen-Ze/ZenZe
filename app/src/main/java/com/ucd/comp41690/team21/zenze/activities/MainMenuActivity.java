package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;

/**
 * Main Activity that launches on start
 * contains the game's Main Menu
 */
public class MainMenuActivity extends Activity {

    final Button helpButton = (Button) findViewById(R.id.help_button);
    final Button startButton = (Button) findViewById(R.id.start_button);
    final Button settingsButton = (Button) findViewById(R.id.setting_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main_menu );

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

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    /**
     * is called when start-button is clicked
     * @param v
     */

    // Intent to start game activity
    public void startGame(View v){
        Intent gameIntent = new Intent(MainMenuActivity.this, GameActivity.class);
        // TODO: Get Location from GPS, and change null to a Location
        gameIntent.putExtra("Game State", WeatherService.getWeatherStatus(null, getApplicationContext()));
        startActivity(gameIntent);
    }

    // Intent to start help activity
    public void startHelp(View v){
        Intent helpIntent = new Intent(MainMenuActivity.this, Help.class);
        startActivity(helpIntent);
    }

    // Intent to start setting activity
    public void startSetting(View v){
        Intent settingIntent = new Intent(MainMenuActivity.this, Settings.class);
        startActivity(settingIntent);
    }
}
