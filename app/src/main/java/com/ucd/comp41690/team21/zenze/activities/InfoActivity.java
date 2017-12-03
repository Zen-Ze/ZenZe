package com.ucd.comp41690.team21.zenze.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();

        Player player = db.playerDao().getAll().get(0);
        //List<AttackListLine> attacks = db.attackListLineDao().getByAttackListId(db.attackListDao().findById(player.getAttackListId()
        /* Bundle info = getIntent().getExtras();
        String name = info.getString("Name");
        String infoText = info.getString("Info");
        int weatherStatus = info.getInt("Status");
        int DBId = info.getInt("DBId");*/
    }
}
