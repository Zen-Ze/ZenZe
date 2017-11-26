package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.util.InputEvent;

/**
 * the main game screen
 */
public class GameActivity extends Activity implements SensorEventListener {
    private final static float ROTATION_ANGLE = 0.15f;

    private Game game;

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];
    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];


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

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //create a new game
        WeatherStatus gameState = (WeatherStatus) (getIntent().getExtras().get("Game State"));
        boolean graphicsRenderer = getIntent().getBooleanExtra("Graphics Renderer", false);
        game = new Game(this, width, height, gameState, graphicsRenderer);
        setContentView(game.getView());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        game.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        game.resume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                game.onInputEvent(InputEvent.TOUCH_DOWN);
            break;
            case MotionEvent.ACTION_UP:
                game.onInputEvent(InputEvent.TOUCH_UP);
            break;
            default:
                game.onInputEvent(InputEvent.NULL);
        }
        return true;
    }

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

        if(mOrientationAngles[1]>ROTATION_ANGLE){
            game.onInputEvent(InputEvent.TILT_LEFT);
        } else if (mOrientationAngles[1]<-ROTATION_ANGLE){
            game.onInputEvent(InputEvent.TILT_RIGHT);
        } else {
            game.onInputEvent(InputEvent.TILT_NONE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
