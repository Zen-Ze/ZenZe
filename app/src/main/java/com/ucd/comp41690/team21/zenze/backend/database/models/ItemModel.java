package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 */

public final class ItemModel {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an ItemModel
     * object.
     */
    private ItemModel() { }

    public static final class ItemModelEntry implements BaseColumns {
        public static final String TABLE_NAME = "Item";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_SPRITE_PATH = "SpritePath";
    }

}
