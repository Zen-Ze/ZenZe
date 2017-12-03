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
        assert info != null;
        String name = info.getString("Name");
        String infoText = info.getString("Info");

        // Fetch title
        getTitle = name;
        // Fetch description
        getDescription = infoText;
        // Fetch and Set Image
        if(name!=null) {
            switch (name) {
                case "Infections":
                    itemImage.setImageResource( R.drawable.enemy_rainy );
                    break;
                case "Cancer":
                    itemImage.setImageResource( R.drawable.enemy_sunny );
                    break;
                case "Depression":
                    itemImage.setImageResource( R.drawable.enemy_snowy );
                    break;
                case "Proteins":
                    itemImage.setImageResource( R.drawable.item_rainy );
                    break;
                case "Lemon":
                    itemImage.setImageResource( R.drawable.item_snowy );
                    break;
                case "Lettuce":
                    itemImage.setImageResource( R.drawable.item_sunny );
                    break;
                case "Pill":
                    itemImage.setImageResource( R.drawable.item_normal );
                    break;
                case "Syringe":
                    itemImage.setImageResource( R.drawable.attack_normal );
                    break;
                case "Sunlight":
                    itemImage.setImageResource( R.drawable.attack_rainy );
                    break;
                case "Cabbage":
                    itemImage.setImageResource( R.drawable.attack_snowy );
                    break;
                case "Banana":
                    itemImage.setImageResource( R.drawable.attack_sunny );
                    break;
            }
        }else itemImage.setImageResource( R.drawable.item_normal );
        // setting item title
        itemTitle.setText(getTitle);
        // setting item description
        itemDescription.setText(getDescription);

    }

}
