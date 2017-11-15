package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 *
 * This class' purpose is to store all SQL related information for Item.
 */

public final class ItemInfo {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an ItemInfo
     * object.
     */
    private ItemInfo() { }

    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "Item";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_SPRITE_PATH = "SpritePath";
<<<<<<< HEAD
=======
        public static final String COLUMN_NAME_WEATHER_STATUS = "WeatherStatus";
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
    }

}
