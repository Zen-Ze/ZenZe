package com.ucd.comp41690.team21.zenze.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.widget.Button;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;
import com.ucd.comp41690.team21.zenze.game.view.GraphicsRenderer;

import java.util.Objects;

/**
 * Main Activity that launches on start
 * contains the game's Main Menu
 */

public class MainMenuActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleApiClient googleApiClient;

    Boolean graphicsOption;
    String weather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
      
/*       // THE FOLLOWING CODE IS AN EXAMPLE OF HOW THE DB WORKS FOR ANNALENA
*        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
*
*      database.attackListDao().insertAll(new AttackList());
*      database.enemyListDao().insertAll(new EnemyList());
*      database.itemListDao().insertAll( new ItemList());
*
*        // YOU NEED THESE FIRST
*      EnemyList el = database.enemyListDao().getAll().get(0);
*      AttackList al = database.attackListDao().getAll().get(0);
*      ItemList il = database.itemListDao().getAll().get(0);
*        // YOU CAN THEN CREATE A PLAYER
*        Player p = new Player(5,2,8,"toto",3,il.getId(),al.getId(), el.getId());
*        database.playerDao().insertAll(p);
*
*        // in the end, remove the instance bc it's huge
*       database = null;
*/


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCESS_COARSE_LOCATION);
        }

        super.onCreate( savedInstanceState );
        googleApiClient = new GoogleApiClient.Builder(this, this, this ).
                addApi(LocationServices.API).build();
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

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        // testing
        String weatherOption = "WEATHER_OPTION";
        String gfx = "GRAPHICS_OPTION";

        weather = pref.getString( weatherOption, "null" );
        graphicsOption =  pref.getBoolean(gfx , true );
    }

    /**
     * is called when start-button is clicked
     *
     * @param v
     */
     
    public void startGame(View v) {
        Intent gameIntent = new Intent(MainMenuActivity.this, GameActivity.class);

        Location location = null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        if(weather.equals("auto")){
            gameIntent.putExtra("Game State", WeatherService.getWeatherStatus(location, getApplicationContext()));

        }

        else{
            gameIntent.putExtra("Game State", weather);
            Log.i("weather",weather);

        }
        gameIntent.putExtra("Graphics Renderer", graphicsOption);
        Log.i("graphics_setting", String.valueOf( graphicsOption ) );
        startActivity(gameIntent);
        }

    // Intent to start help activity
    public void startHelp(View v) {
        Intent helpIntent = new Intent( MainMenuActivity.this, Help.class );
        helpIntent.putExtra( "graphics_settings", graphicsOption );
        helpIntent.putExtra( "weather_settings", weather );
        startActivity( helpIntent );
    }

    // Intent to start setting activity
    public void startSetting(View v) {
        Intent settingIntent = new Intent( MainMenuActivity.this, Setting.class );
        startActivity( settingIntent );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "This application requires your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
