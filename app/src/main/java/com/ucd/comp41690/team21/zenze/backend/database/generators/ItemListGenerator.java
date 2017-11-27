package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListInfo;

/**
 * Created by timothee on 19/10/17.
 */

public class ItemListGenerator {

    public static final String CREATE_ITEM_LIST_TABLE = "CREATE TABLE " + ItemListInfo.ItemListEntry.TABLE_NAME + " ("
            + ItemListInfo.ItemListEntry._ID + " INTEGER PRIMARY KEY NOT NULL);";

    public static final String DELETE_ITEM_LIST_TABLE = "DROP TABLE IF EXISTS " + ItemListInfo.ItemListEntry.TABLE_NAME;

}
