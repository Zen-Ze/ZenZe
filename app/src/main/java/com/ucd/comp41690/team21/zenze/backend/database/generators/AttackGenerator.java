package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.PlayerInfo;

/**
 * Created by timothee on 07/11/17.
 */

public class AttackGenerator {

    public static final String CREATE_ATTACK_TABLE = "CREATE TABLE " + AttackInfo.AttackEntry.TABLE_NAME + "("
            + AttackInfo.AttackEntry._ID + " INTEGER AUTOINCREMENT,"
            + AttackInfo.AttackEntry.COLUMN_NAME_NAME + " VARCHAR(64),"
            + AttackInfo.AttackEntry.COLUMN_NAME_DAMAGE + " INTEGER,"
            + AttackInfo.AttackEntry.COLUMN_NAME_SCALE + " INTEGER,"
            + AttackInfo.AttackEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
            + AttackInfo.AttackEntry.COLUMN_NAME_GRAPHICS_PATH + " TEXT,"
            + AttackInfo.AttackEntry.COLUMN_NAME_ATTACK_STATE + " INTEGER,"
            + AttackInfo.AttackEntry.COLUMN_NAME_WEATHER_STATUS + " INTEGER,"
            + "PRIMARY KEY (" + AttackInfo.AttackEntry._ID + ")"
            + ")";

    public static final String DELETE_ATTACK_TABLE = "DROP TABLE IF EXISTS " + AttackInfo.AttackEntry.TABLE_NAME;

}
