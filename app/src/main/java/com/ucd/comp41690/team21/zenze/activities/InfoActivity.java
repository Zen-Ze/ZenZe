package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle info = getIntent().getExtras();
        String name = info.getString("Name");
        String infoText = info.getString("Info");
        //Bitmap image = info.getParcelable("Image");
    }
}
