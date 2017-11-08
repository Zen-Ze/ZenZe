package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class EnemyGenerator {
    public static final String CREATE_ENEMY_TABLE = "CREATE TABLE " + EnemyInfo.EnemyEntry.TABLE_NAME + "("
            + EnemyInfo.EnemyEntry._ID + " INTEGER AUTOINCREMENT,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_NAME + " VARCHAR(64),"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_DAMAGE + " INTEGER,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_SCALE + " INTEGER,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_GRAPHICS_PATH + " TEXT,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_SPEED + "INTEGER"
            + "PRIMARY KEY (" + EnemyInfo.EnemyEntry._ID + "),"
            + ")";

    public static final String DELETE_ENEMY_TABLE = "DROP TABLE IF EXISTS " + EnemyInfo.EnemyEntry.TABLE_NAME;


}
