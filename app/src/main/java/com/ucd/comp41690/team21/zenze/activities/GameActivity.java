package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.fragments.GameOverDialogFragment;
import com.ucd.comp41690.team21.zenze.fragments.InfoDialogFragment;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.components.Type;
import com.ucd.comp41690.team21.zenze.game.util.InputEvent;

/**
 * the main game screen
 */
public class GameActivity extends FragmentActivity implements SensorEventListener,
        InfoDialogFragment.InfoDialogListener, GameOverDialogFragment.GameOverDialogListener {
    private final static float ROTATION_ANGLE = 0.15f;
    //Game variables
    private Game game;
    private int width;
    private int height;
    private WeatherStatus status;
    private boolean graphicsRenderer;
    private Type itemType;
    //Sensor variables
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];


    /**
     * is called when the Activity is created
     * initialises the sensors, reads configurations from the intent and creates a new Game
     * @param savedInstanceState
     */
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
        width = Math.max(size.x, size.y);
        height = Math.min(size.x, size.y);
        //initialise sensors
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //create a new game
        status = (WeatherStatus) (getIntent().getExtras().get("Game State"));
        graphicsRenderer = getIntent().getBooleanExtra("Graphics Renderer", false);
        game = new Game(this, width, height, status, graphicsRenderer);
        setContentView(game.getView());
    }

    /**
     * is called when the activity pauses
     * unregisters the sensors and stops the game
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if(game.isRunning()) {
            game.pause();
        }
    }

    /**
     * is called before the activity becomes visible
     * registers listensers and resumes game
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        game.resume();
    }

    /**
     * Gets called when the user touches the screen
     * @param event the Motion Event caused by the user
     * @return true if event was processed correctly
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            //touch down
            case MotionEvent.ACTION_DOWN:
                if (event.getY() < Game.getInstance().UIHeight) {
                    //the UI is clicked
                    Intent statsIntent = new Intent(GameActivity.this, StatsActivity.class);
                    startActivity(statsIntent);
                } else if (event.getX() <= Game.getInstance().getWidth() / 2) {
                    game.onInputEvent(InputEvent.TOUCH_DOWN_LEFT);
                } else if (event.getX() > Game.getInstance().getWidth() / 2) {
                    game.onInputEvent(InputEvent.TOUCH_DOWN_RIGHT);
                }
                break;
            //Finger lifted again
            case MotionEvent.ACTION_UP:
                game.onInputEvent(InputEvent.TOUCH_UP);
                break;
            //no interesting motion event
            default:
                game.onInputEvent(InputEvent.NULL);
        }
        return true;
    }

    /**
     * Is called if the user moves the device
     * @param event the sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
        }

        mSensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);
        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

        //if sensor values are for a significant movement tell the game
        if (mOrientationAngles[1] > ROTATION_ANGLE) {
            game.onInputEvent(InputEvent.TILT_LEFT);
        } else if (mOrientationAngles[1] < -ROTATION_ANGLE) {
            game.onInputEvent(InputEvent.TILT_RIGHT);
        } else {
            game.onInputEvent(InputEvent.TILT_NONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * is called when the user finds a new object to display a short info message
     * @param type the type of object which was found
     */
    public void onItemFound(Type type) {
        itemType = type;

        Bundle info = new Bundle();
        info.putString("Name", type.getName());
        info.putString("Info", type.getShortInfo());
        info.putParcelable("Image", type.getImage());
        info.putString("Title", getString(R.string.infoDialog_TitleItem));

        DialogFragment dialog = new InfoDialogFragment();
        dialog.setArguments(info);
        dialog.show(getSupportFragmentManager(), "infoItem");

        game.pause();
    }

    /**
     * is called when the user defeates a new unknown enemy
     * shows a short info message
     * @param type the type of enemy that was killed
     */
    public void onEnemyDefeated(Type type){
        itemType = type;

        Bundle info = new Bundle();
        info.putString("Name", type.getName());
        info.putString("Info", type.getShortInfo());
        info.putParcelable("Image", type.getImage());
        info.putString("Title", getString(R.string.infoDialog_TitleEnemy));

        DialogFragment dialog = new InfoDialogFragment();
        dialog.setArguments(info);
        dialog.show(getSupportFragmentManager(), "infoEnemy");

        game.pause();
    }

    /**
     * is called when the player runs out of life or touches the ground
     * shows a short info message giving the options to go to the main menu or retry
     */
    public void onGameOver() {
        DialogFragment dialog = new GameOverDialogFragment();
        dialog.show(getSupportFragmentManager(), "gameOver");
        game.pause();
    }

    /**
     * is called when the user clicks more info in the info dialog
     * @param dialog the dialog in which the button was clicked
     */
    @Override
    public void onInfoDialogPositiveClick(DialogFragment dialog) {
        Intent infoIntent = new Intent(GameActivity.this, ItemDescription.class);
        Bundle info = new Bundle();
        if(itemType!=null) {
            info.putString("Name", itemType.getName());
            info.putString("Info", itemType.getFullInfo());
            info.putInt("Status", itemType.getState()==null?3:itemType.getState().getValue());
            info.putInt("DBId", itemType.getDBId());
        } else{
            info.putString("Name", "Item Name");
            info.putString("Info", "Display short info here");
        }
        infoIntent.putExtras(info);
        startActivity(infoIntent);
    }

    /**
     * is called when the user chose to go back to the game after the short info message was displayed
     * @param dialog the dialog in which the option was clicked
     */
    @Override
    public void onInfoDialogNegativeClick(DialogFragment dialog) {
        game.resume();
    }

    /**
     * is called when the user wants to retry after he lost the game
     * @param dialog the dialog in which the option was clicked
     */
    @Override
    public void onGameOverDialogPositiveClick(DialogFragment dialog) {
        //retry
        game = new Game(this, width, height, status, graphicsRenderer);
        setContentView(game.getView());
        game.resume();
    }

    /**
     * is called when the user wants to go back to the main menu after loosing the game
     * @param dialog the dialog in which the option was clicked
     */
    @Override
    public void onGameOverDialogNegativeClick(DialogFragment dialog) {
        //back to main menu
        Intent menuIntent = new Intent(GameActivity.this, MainMenuActivity.class);
        startActivity(menuIntent);
    }
}
