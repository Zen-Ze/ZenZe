package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 */

public final class PlayerModel {
    /**
     * Private constructor to prevent someone from accidentally trying to instanciate a PlayerModel
     * object.
     */
    private PlayerModel() {}

    public static class PlayerModelEntry implements BaseColumns {
        public static final String TABLE_NAME = "Player";
        public static final String COLUMN_NAME_LAST_COORD_X = "LastCoordX";
        public static final String COLUMN_NAME_LAST_COORD_Y = "LastCoordY";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_SAVED_HEALTH = "SavedHealth";
        public static final String COLUMN_NAME_ITEM_LIST_ID = "ItemListId";
    }

}
