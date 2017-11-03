package com.ucd.comp41690.team21.zenze.Activities;

import android.os.Bundle;
import android.app.Activity;

import com.ucd.comp41690.team21.zenze.R;

public class Help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_help );
        getActionBar().setDisplayHomeAsUpEnabled( true );
    }
}
