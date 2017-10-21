package com.ucd.comp41690.team21.zenze.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ucd.comp41690.team21.zenze.R;

/**
 * Main Activity that launches on start
 * contains the Game Menu
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
        startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
    }
}
