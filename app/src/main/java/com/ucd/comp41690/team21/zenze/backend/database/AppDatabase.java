package com.ucd.comp41690.team21.zenze.backend.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ucd.comp41690.team21.zenze.backend.database.dao.AttackListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.EnemyListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.ItemDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.ItemListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.PlayerDao;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

/**
 * Created by timothee on 26/11/17.
 */
@Database(entities = {Player.class, EnemyList.class, AttackList.class, ItemList.class, Item.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract AttackListDao attackListDao();
    public abstract EnemyListDao enemyListDao();
    public abstract ItemListDao itemListDao();
    public abstract ItemDao itemDao();

}
