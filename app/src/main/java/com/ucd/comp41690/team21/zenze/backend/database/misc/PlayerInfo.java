package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 *
 * This class' purpose is to store all SQL related information for Player.
 */

public final class PlayerInfo {
    /**
     * Private constructor to prevent someone from accidentally trying to instanciate a PlayerInfo
     * object.
     */
    private PlayerInfo() {}

    public static class PlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "Player";
        public static final String COLUMN_NAME_LAST_COORD_X = "LastCoordX";
        public static final String COLUMN_NAME_LAST_COORD_Y = "LastCoordY";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_SAVED_HEALTH = "SavedHealth";
        public static final String COLUMN_NAME_ATTACK_LIST_ID = "AttackListId";
        public static final String COLUMN_NAME_ENEMY_LIST_ID = "EnemyListId";
        public static final String COLUMN_NAME_ITEM_LIST_ID = "ItemListId";
        public static final String COLUMN_NAME_CURRENT_LEVEL = "CurrentLevel";
    }

}
