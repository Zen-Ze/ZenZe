package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.ucd.comp41690.team21.zenze.R;

/**
 * the main game screen
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
