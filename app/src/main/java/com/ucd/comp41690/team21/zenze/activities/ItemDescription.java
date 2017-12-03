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
        int weatherStatus = info.getInt("Status");
        int DBId = info.getInt("DBId");


        // Fetch title
        getTitle = name;
        // Fetch description
        getDescription = infoText;
        // Fetch image
        if(name!=null) {
            switch (name) {
                case "enemy_rainy":
                    itemImage.setImageResource( R.drawable.enemy_rainy );
                    break;
                case "enemy_sunny":
                    itemImage.setImageResource( R.drawable.enemy_sunny );
                    break;
                case "enemy_snowy":
                    itemImage.setImageResource( R.drawable.enemy_snowy );
                    break;
                case "item_rainy":
                    itemImage.setImageResource( R.drawable.item_rainy );
                    break;
                case "item_snowy":
                    itemImage.setImageResource( R.drawable.item_snowy );
                    break;
                case "item_sunny":
                    itemImage.setImageResource( R.drawable.item_sunny );
                    break;
                case "item_normal":
                    itemImage.setImageResource( R.drawable.item_normal );
                    break;
            }
        }else itemImage.setImageResource( R.drawable.item_normal );
        // setting item title
        itemTitle.setText(getTitle);
        // setting item description
        itemDescription.setText(getDescription);
        //setting item image to be displayed
        //itemImage.setImageDrawable(findViewById( R.drawable.item_bg ));
    }

}
