package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListLineInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class EnemyListLineGenerator {

    public static final String CREATE_ENEMY_LIST_LINE_TABLE = "CREATE TABLE " + EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME + " ("
            + EnemyListLineInfo.EnemyListLineEntry._ID + " INTEGER AUTOINCREMENT,"
            + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_AMOUNT + " INTEGER,"
            + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_ID + " INTEGER,"
            + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID + " INTEGER,"
            + "PRIMARY KEY (" + EnemyListLineInfo.EnemyListLineEntry._ID +")"
            + "FOREIGN KEY (" + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID + ") REFERENCES " + EnemyListInfo.EnemyListEntry.TABLE_NAME +"(" + EnemyListInfo.EnemyListEntry._ID + ")"
            + "FOREIGN KEY (" + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_ID + ") REFERENCES " + EnemyInfo.EnemyEntry.TABLE_NAME +"(" + EnemyInfo.EnemyEntry._ID + "))";

    public static final String DELETE_ENEMY_LIST_LINE_TABLE = "DROP TABLE IF EXISTS " + EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME;

}
