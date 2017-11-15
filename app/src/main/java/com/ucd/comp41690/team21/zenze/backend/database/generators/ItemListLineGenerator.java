package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListLineInfo;

/**
 * Created by timothee on 19/10/17.
 */

public class ItemListLineGenerator {

    public static final String CREATE_ITEM_LIST_LINE_TABLE = "CREATE TABLE " + ItemListLineInfo.ItemListLineEntry.TABLE_NAME + " ("
            + ItemListLineInfo.ItemListLineEntry._ID + " INTEGER AUTOINCREMENT,"
            + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_AMOUNT + " INTEGER,"
            + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_ID + " INTEGER,"
            + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID + " INTEGER,"
<<<<<<< HEAD
            + "PRIMARY KEY (" + ItemListLineInfo.ItemListLineEntry._ID +")"
            + "FOREIGN KEY (" + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID + ") REFERENCES " + ItemListInfo.ItemListEntry.TABLE_NAME +"(" + ItemListInfo.ItemListEntry._ID + ")"
=======
            + "PRIMARY KEY (" + ItemListLineInfo.ItemListLineEntry._ID +"),"
            + "FOREIGN KEY (" + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID + ") REFERENCES " + ItemListInfo.ItemListEntry.TABLE_NAME +"(" + ItemListInfo.ItemListEntry._ID + "),"
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
            + "FOREIGN KEY (" + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_ID + ") REFERENCES " + ItemInfo.ItemEntry.TABLE_NAME +"(" + ItemInfo.ItemEntry._ID + "))";

    public static final String DELETE_ITEM_LIST_LINE_TABLE = "DROP TABLE IF EXISTS " + ItemListLineInfo.ItemListLineEntry.TABLE_NAME;

}
