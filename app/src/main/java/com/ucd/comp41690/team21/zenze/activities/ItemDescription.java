package com.ucd.comp41690.team21.zenze.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;

import org.w3c.dom.Text;

public class ItemDescription extends Activity {


    String getTitle, getDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_description );
        final TextView itemTitle = findViewById( R.id.textview_item_title );
        final TextView itemDescription =  findViewById( R.id.textview_item_description );
        final ImageView itemImage =  findViewById( R.id.item_image );

        // Fetching data from the game activity
        Bundle info = getIntent().getExtras();
        String name = info.getString("Name");
        String infoText = info.getString("Info");

        // Fetch title
        getTitle = name;
        // Fetch description
        getDescription = infoText;
        // Fetch image
        //Bitmap image = BitmapFactory.decodeResource(getResources().R.drawable.enemy_rainy);

        // setting item title
        itemTitle.setText(getTitle);
        // setting item description
        itemDescription.setText(getDescription);
        //setting item image to be displayed
        //itemImage.setImageDrawable(findViewById( R.drawable.item_bg ));
    }

}
