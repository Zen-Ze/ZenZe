package com.ucd.comp41690.team21.zenze.activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;

import org.w3c.dom.Text;

public class ItemDescription extends Activity {

    final TextView itemTitle = (TextView) findViewById( R.id.textview_title_item );
    final TextView itemDescription = (TextView) findViewById( R.id.textview_item_description );
    final ImageView itemImage = (ImageView) findViewById( R.id.item_image );
    String getTitle, getDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_description );
        // Fetching data from the game activity
        // Fetch title
        getTitle = " Dummy title";
        // Fetch description
        getDescription = "dummy description";
        // Fetch image


        // setting item title
        itemTitle.setText(getTitle);
        // setting item description
        itemDescription.setText(getDescription);
        //setting item image to be displayed
        //itemImage.setImageDrawable(findViewById( R.drawable.item_bg ));
    }

}
