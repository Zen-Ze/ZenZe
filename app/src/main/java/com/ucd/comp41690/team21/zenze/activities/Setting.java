package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ucd.comp41690.team21.zenze.R;

import java.util.ArrayList;
import java.util.List;

public class Setting extends Activity implements AdapterView.OnItemSelectedListener {

    private final String graphicsOption = "GRAPHICS_OPTION";
    private final String weatherOption = "WEATHER_OPTION";
    private final String savedItemOnSpinner = "savedItemOnSpinner";
    private final String savedItemOnToggle = "savedItemOnToggle";
    Spinner weatherSpinner;
    ToggleButton graphicButton;


/*    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(savedItemOnSpinner, weatherSpinner.getSelectedItemPosition());
        savedInstanceState.putBoolean(savedItemOnToggle, graphicButton.isChecked());
        super.onSaveInstanceState(savedInstanceState);


    }*/
   /* @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        graphicButton.setChecked(savedInstanceState.getBoolean(savedItemOnToggle,false));
        weatherSpinner.setSelection(savedInstanceState.getInt(savedItemOnSpinner,0));
    }*/

   // saving state of the buttons
    private void SavePreferences(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(savedItemOnToggle, graphicButton.isChecked());
        editor.putInt(savedItemOnSpinner,weatherSpinner.getSelectedItemPosition());
        editor.apply();
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );

        Boolean  state = sharedPreferences.getBoolean(savedItemOnToggle, false);
        int  position= sharedPreferences.getInt(savedItemOnSpinner,3);

        graphicButton.setChecked(state);
        weatherSpinner.setSelection(position);
    }
    public void onBackPressed() {
        SavePreferences();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_settings );

        //getActionBar().setDisplayHomeAsUpEnabled( true );
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        final SharedPreferences.Editor editor = pref.edit();
        // Loading already saved preferences else put default values


        // Spinner elements
        graphicButton = findViewById( R.id.graphic_switch );
        weatherSpinner = findViewById( R.id.weather_spinner );

        LoadPreferences();

        graphicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // The toggle is enabled
                    editor.putBoolean(graphicsOption,true);
                    editor.apply();

                } else {
                    // The toggle is disabled
                    editor.putBoolean(graphicsOption,false);
                    editor.apply();
                }
            }
        });

        // set up listener for weather spinner
        weatherSpinnerMethod();

    }


    private void weatherSpinnerMethod () {

        // Spinner click listener
        weatherSpinner.setOnItemSelectedListener( this );

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add( "Auto" );
        categories.add( "Rainy" );
        categories.add( "Snowy" );
        categories.add( "Sunny" );

        // setting default selection chosen by the user, if not then the factory default
       // weatherSpinner.setSelection( defaultSelection );

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>( this,
                android.R.layout.simple_spinner_item, categories );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        // attaching data adapter to spinner
        weatherSpinner.setAdapter( dataAdapter );
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();

            // For items related to the weather options

                switch (position) {
                    case 0:
                        editor.putString( weatherOption, "auto" );
                        editor.apply();
                        break;
                    case 1:
                        editor.putString( weatherOption, "RAINY" );
                        editor.apply();
                        break;
                    case 2:
                        editor.putString( weatherOption, "SNOWY" );
                        editor.apply();
                        break;
                    case 3:
                        editor.putString( weatherOption, "SUNNY" );
                        editor.apply();
                        break;
                }



    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    protected void onStop(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();
        super.onStop();
        // Commit the edits!
        editor.apply();
    }
}
