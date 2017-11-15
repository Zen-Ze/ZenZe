package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemInfo;

/**
 * Created by timothee on 19/10/17.
 */

public class ItemGenerator {

    public static final String CREATE_ITEM_TABLE = "CREATE TABLE " + ItemInfo.ItemEntry.TABLE_NAME + " ("
            + ItemInfo.ItemEntry._ID + " INTEGER AUTOINCREMENT,"
            + ItemInfo.ItemEntry.COLUMN_NAME_NAME + " VARCHAR(64),"
            + ItemInfo.ItemEntry.COLUMN_NAME_DESCRIPTION + " TEXT,"
            + ItemInfo.ItemEntry.COLUMN_NAME_SPRITE_PATH + " TEXT,"
<<<<<<< HEAD
=======
            + ItemInfo.ItemEntry.COLUMN_NAME_WEATHER_STATUS + " INTEGER,"
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
            + "PRIMARY KEY (" + ItemInfo.ItemEntry._ID +"))";

    public static final String DELETE_ITEM_TABLE = "DROP TABLE IF EXISTS " + ItemInfo.ItemEntry.TABLE_NAME;

}
