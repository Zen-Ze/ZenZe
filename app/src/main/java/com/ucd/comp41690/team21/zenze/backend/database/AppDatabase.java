package com.ucd.comp41690.team21.zenze.backend.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ucd.comp41690.team21.zenze.backend.database.dao.AttackDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.AttackListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.AttackListLineDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.EnemyDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.EnemyListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.EnemyListLineDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.ItemDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.ItemListDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.ItemListLineDao;
import com.ucd.comp41690.team21.zenze.backend.database.dao.PlayerDao;
import com.ucd.comp41690.team21.zenze.backend.database.models.Attack;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Enemy;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

/**
 * Created by timothee on 26/11/17.
 */
@Database(entities = {
        Player.class,
        EnemyList.class,
        AttackList.class,
        ItemList.class,
        Item.class,
        Attack.class,
        Enemy.class,
        ItemListLine.class,
        AttackListLine.class, EnemyListLine.class
}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract AttackListDao attackListDao();
    public abstract EnemyListDao enemyListDao();
    public abstract ItemListDao itemListDao();
    public abstract ItemDao itemDao();
    public abstract AttackDao attackDao();
    public abstract EnemyDao enemyDao();
    public abstract ItemListLineDao itemListLineDao();
    public abstract EnemyListLineDao enemyListLineDao();
    public abstract AttackListLineDao attackListLineDao();

}
