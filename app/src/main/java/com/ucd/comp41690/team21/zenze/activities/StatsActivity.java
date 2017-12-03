package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.widget.CheckedTextView;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.List;

public class StatsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats );

        // Gettings resources ready
        CheckedTextView itemNormal = findViewById( R.id.item_normal );
        CheckedTextView itemSnowy = findViewById( R.id.item_snowy );
        CheckedTextView itemSunny = findViewById( R.id.item_sunny );
        CheckedTextView itemRainy = findViewById( R.id.item_rainy );
        CheckedTextView enemySunny = findViewById( R.id.enemy_sunny );
        CheckedTextView enemyRainy = findViewById( R.id.enemy_rainy );
        CheckedTextView enemySnowy = findViewById( R.id.enemy_snowy );
        CheckedTextView attackSnowy = findViewById( R.id.attack_snowy );
        CheckedTextView attackRainy = findViewById( R.id.attack_rainy );
        CheckedTextView attackNormal = findViewById( R.id.attack_normal );
        CheckedTextView attackSunny = findViewById( R.id.attack_sunny );

        // Database related code
        AppDatabase database = Room.databaseBuilder( getApplicationContext(), AppDatabase.class, "zenze-db" ).allowMainThreadQueries().build();

        AttackList al = database.attackListDao().findById( database.playerDao().getAll().get( 0 ).getAttackListId() );

        List<AttackListLine> attacks = database.attackListLineDao().getByAttackListId( al.getId() );
        int check;
        for (AttackListLine attack : attacks) {
            check = attack.getAmount();
            if (check > 0) {
                if (attack.getId() == FileParser.DBIdNormalAttack) {
                    attackNormal.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdRainyAttack) {
                    attackRainy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSnowyAttack) {
                    attackSnowy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSunnyAttack) {
                    attackSunny.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdNormalItem) {
                    itemNormal.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSunnyItem) {
                    itemSunny.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSnowyItem) {
                    itemSnowy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdRainyItem) {
                    itemRainy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdRainyEnemy) {
                    enemyRainy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSnowyEnemy) {
                    enemySnowy.setChecked( true );
                }
                if (attack.getId() == FileParser.DBIdSunnyEnemy) {
                    enemySunny.setChecked( true );
                }
            }
        }
        // changing text view state


    }

}
