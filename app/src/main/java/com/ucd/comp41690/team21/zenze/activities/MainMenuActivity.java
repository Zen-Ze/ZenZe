package com.ucd.comp41690.team21.zenze.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

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
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.view.GraphicsRenderer;

import java.util.Objects;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Main Activity that launches on start
 * contains the game's Main Menu
 */

public class MainMenuActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken ;
    private final static String TAG = MainMenuActivity.class.getName().toString();

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

        //Initialize FB SDK
        //This line must be put here before the  setContentView(R.layout.activity_login);
        //Or else you will get null object error
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
                    @Override
                    public void onInitialized() {
                        //AccessToken is for us to check whether we have previously logged in into
                        //this app, and this information is save in shared preferences and sets it during SDK initialization
                        accessToken = AccessToken.getCurrentAccessToken();
                        if (accessToken == null) {
                            Log.d(TAG, "not log in yet");
                        } else {
                            Log.d(TAG, "Logged in");
                            Intent main = new Intent(MainMenuActivity.this,ShareActivity.class);
                            startActivity(main);
                        }
                    }
        });
        setContentView(R.layout.activity_main_menu);

   //register a callback to respond to a login result,
                callbackManager = CallbackManager.Factory.create();
//register access token to check whether user logged in before
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                accessToken = newToken;
            }
        };
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);

        googleApiClient = new GoogleApiClient.Builder(this, this, this ).
                addApi(LocationServices.API).build();
        setContentView( R.layout.activity_main_menu );

        // Buttons for this activity
        final Button helpButton = findViewById( R.id.help_button );
        final Button startButton = findViewById( R.id.start_button );
        final Button settingsButton = findViewById( R.id.setting_button );
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Once authorized from facebook will directly go to MainActivity
                accessToken = loginResult.getAccessToken();
                Intent main = new Intent(MainMenuActivity.this,ShareActivity.class);
                startActivity(main);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        { List<String> permissionNeeds = Arrays.asList("user_friends","email","user_birthday");
        loginButton.setReadPermissions(permissionNeeds);

        accessTokenTracker.startTracking();

        //Generate Hash Key, need get this key update
        // into https://developers.facebook.com/quickstarts/1584671128490867/?platform=android
        showHashKey(this);
    }

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

        // Checking current user settings
        final  String weatherOption = "WEATHER_OPTION";
        final  String gfx = "GRAPHICS_OPTION";
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
        // Switch case for switching between which weather level to start
        switch (weather) {
            case "auto":
                gameIntent.putExtra( "Game State", WeatherService.getWeatherStatus( location, getApplicationContext() ) );
                break;
            case "RAINY":
                gameIntent.putExtra( "Game State", WeatherStatus.RAINY );
                Log.i( "weather", weather );
                break;
            case "SNOWY":
                gameIntent.putExtra( "Game State", WeatherStatus.SNOWY );
                Log.i( "weather", weather );
                break;
            case "SUNNY":
                gameIntent.putExtra( "Game State", WeatherStatus.SUNNY );
                Log.i( "weather", weather );
                break;
        }

        // Switching renderer On or OFF
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
    //Set permission to use in this app



    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.instinctcoder.fb", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        accessTokenTracker.stopTracking();
    }

}
