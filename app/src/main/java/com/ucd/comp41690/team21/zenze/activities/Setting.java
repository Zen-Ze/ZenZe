package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    Spinner weatherSpinner;
    ToggleButton graphicButton;


    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //Put your spinner values to restore later...

        //outState.putInt("yourSpinner", weatherSpinner.getSelectedItemPosition());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_settings );

        //getActionBar().setDisplayHomeAsUpEnabled( true );

        if (savedInstanceState!= null) {
            //get your values to restore...
            weatherSpinner.setSelection(savedInstanceState.getInt("yourSpinner", 0));
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        final SharedPreferences.Editor editor = pref.edit();
        // Spinner elements
        graphicButton = findViewById( R.id.graphic_switch );
        weatherSpinner = findViewById( R.id.weather_spinner );


        graphicButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
        weatherSpinnerMethod( getWeatherDefault() );

    }


    private void weatherSpinnerMethod (int defaultSelection) {

        // Spinner click listener
        weatherSpinner.setOnItemSelectedListener( this );

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add( "Automatic (default)" );
        categories.add( "rainy" );
        categories.add( "snowy" );
        categories.add( "sunny" );

        // setting default selection chosen by the user, if not then the factory default
        weatherSpinner.setSelection( defaultSelection );

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

        String item;
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();

            // For items related to the weather options

                switch (position) {
                    case 0:
                        editor.putString( weatherOption, "auto" );
                        break;
                    case 1:
                        editor.putString( weatherOption, "rainy" );
                        break;
                    case 2:
                        editor.putString( weatherOption, "snowy" );
                        break;
                    case 3:
                        editor.putString( weatherOption, "sunny" );
                        break;
                }
                item = parent.getItemAtPosition( position ).toString();
                Toast.makeText( parent.getContext(), "Selected: " +
                        item, Toast.LENGTH_SHORT ).show();

                editor.apply();


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private int getWeatherDefault(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        int bit = 0;

        // checking the currently chosen value by user
        String check = pref.getString( weatherOption,"null");

        switch (check){
            case "auto":  bit = 0; break;
            case "rainy": bit = 1; break;
            case "snowy": bit = 2; break;
            case "sunny": bit = 3; break;
            case  "null": bit = 0; break;
        }

        return  bit;
    }
    protected void onStop(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences( "ZenzePref", MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();
        super.onStop();
        // Commit the edits!
        editor.commit();
    }
}
