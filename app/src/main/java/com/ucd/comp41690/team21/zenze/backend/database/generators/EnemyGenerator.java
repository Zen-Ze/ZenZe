package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class EnemyGenerator {
    public static final String CREATE_ENEMY_TABLE = "CREATE TABLE " + EnemyInfo.EnemyEntry.TABLE_NAME + "("
            + EnemyInfo.EnemyEntry._ID + " INTEGER PRIMARY KEY NOT NULL,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_NAME + " VARCHAR,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_DAMAGE + " INTEGER,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_SCALE + " INTEGER,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_GRAPHICS_PATH + " TEXT,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_SPEED + " INTEGER,"
            + EnemyInfo.EnemyEntry.COLUMN_NAME_WEATHER_STATUS + " INTEGER,"
            + ");";

    public static final String DELETE_ENEMY_TABLE = "DROP TABLE IF EXISTS " + EnemyInfo.EnemyEntry.TABLE_NAME;


}
