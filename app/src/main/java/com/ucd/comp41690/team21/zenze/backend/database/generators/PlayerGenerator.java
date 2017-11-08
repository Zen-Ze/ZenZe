package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.PlayerInfo;

/**
 * Created by timothee on 18/10/17.
 */

public class PlayerGenerator {

    public static final String CREATE_PLAYER_TABLE = "CREATE TABLE " + PlayerInfo.PlayerEntry.TABLE_NAME + "("
            + PlayerInfo.PlayerEntry._ID + " INTEGER AUTOINCREMENT,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_USERNAME + " VARCHAR(64),"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_X + " INTEGER,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_Y + " INTEGER,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_SAVED_HEALTH + " INTEGER,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_CURRENT_LEVEL + " INTEGER,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID + " INTEGER,"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_ATTACK_LIST_ID + "INTEGER"
            + PlayerInfo.PlayerEntry.COLUMN_NAME_ENEMY_LIST_ID + "INTEGER"
            + "PRIMARY KEY (" + PlayerInfo.PlayerEntry._ID + "),"
            + "FOREIGN KEY (" + PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID + ") REFERENCES " + ItemListInfo.ItemListEntry.TABLE_NAME +"(" + ItemListInfo.ItemListEntry._ID + ")"
            + "FOREIGN KEY (" + PlayerInfo.PlayerEntry.COLUMN_NAME_ATTACK_LIST_ID + ") REFERENCES " + AttackListInfo.AttackListEntry.TABLE_NAME +"(" + AttackListInfo.AttackListEntry._ID + ")"
            + "FOREIGN KEY (" + PlayerInfo.PlayerEntry.COLUMN_NAME_ENEMY_LIST_ID + ") REFERENCES " + EnemyListInfo.EnemyListEntry.TABLE_NAME +"(" + EnemyListInfo.EnemyListEntry._ID + ")"
            + ")";

    public static final String DELETE_PLAYER_TABLE = "DROP TABLE IF EXISTS " + PlayerInfo.PlayerEntry.TABLE_NAME;

}