package com.ucd.comp41690.team21.zenze.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.app.Activity;
import android.widget.CheckedTextView;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

public class StatsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats );
        // Gettings resources ready
        CheckedTextView item1 = findViewById( R.id.item1 );
        CheckedTextView item2 = findViewById( R.id.item2 );
        CheckedTextView item3 = findViewById( R.id.item3 );
        CheckedTextView item4 = findViewById( R.id.item4 );
        CheckedTextView enemy1 = findViewById( R.id.enemy1 );
        CheckedTextView enemy2 = findViewById( R.id.enemy2 );
        CheckedTextView enemy3 = findViewById( R.id.enemy3 );

        // Database related code
        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "zenze-db").allowMainThreadQueries().build();

        database.attackListDao().insertAll(new AttackList());
        database.enemyListDao().insertAll(new EnemyList());
        database.itemListDao().insertAll( new ItemList());

        // YOU NEED THESE FIRST
        EnemyList el = database.enemyListDao().getAll().get(0);
        AttackList al = database.attackListDao().getAll().get(0);
        ItemList il = database.itemListDao().getAll().get(0);
       // YOU CAN THEN CREATE A PLAYER
       Player p = new Player(5,2,8,"toto",3,il.getId(),al.getId(), el.getId());
        database.playerDao().insertAll(p);

        // changing text view state
        item1.setChecked( true );
        item2.setChecked( true);
        item3.setChecked( false );
        item4.setChecked( true );
        enemy1.setChecked( false);
        enemy2.setChecked( true);
        enemy3.setChecked( false);
    }

}
