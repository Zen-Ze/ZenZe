package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class EnemyListGenerator {
    public static final String CREATE_ENEMY_LIST = "CREATE TABLE " + EnemyListInfo.EnemyListEntry.TABLE_NAME + " ("
            + EnemyListInfo.EnemyListEntry._ID + " INTEGER PRIMARY KEY NOT NULL);";

    public static final String DELETE_ENEMY_LIST = "DROP TABLE IF EXISTS " + EnemyListInfo.EnemyListEntry.TABLE_NAME;
}
