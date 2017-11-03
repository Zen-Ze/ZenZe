package com.ucd.comp41690.team21.zenze.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.ucd.comp41690.team21.zenze.R;

/**
 * Main Activity that launches on start
 * contains the Game Menu
 */
public class MainMenuActivity extends Activity {

    Button start_game = (Button) findViewById(R.id.start_game);
    Button bt_settings = (Button) findViewById(R.id.bt_settings);
    Button bt_help = (Button) findViewById(R.id.bt_help);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // When user clicks start
        start_game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startGame(view);
            }
        });

        // When user clicks help button
        bt_help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Help(view);
            }
        });
        // When user clicks settings button
        bt_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Settings(view);
            }
        });



    }

    /**
     * is called when start-button is clicked
     * @param v
     */
    private void startGame(View v){
        startActivity(new Intent( MainMenuActivity.this, GameActivity.class));
    }
    private void Help(View v){
        startActivity(new Intent( MainMenuActivity.this, Help.class));
    }
    private void Settings(View v){
        startActivity(new Intent( MainMenuActivity.this, Settings.class));
    }


}
