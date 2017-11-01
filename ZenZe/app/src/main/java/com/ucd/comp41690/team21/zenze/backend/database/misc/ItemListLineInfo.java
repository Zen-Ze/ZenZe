package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 *
 * This class' purpose is to store all SQL related information for Item List Line.
 */

public final class ItemListLineInfo {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an
     * ItemListLineInfo object.
     */
    private ItemListLineInfo() {}

    public static final class ItemListLineEntry implements BaseColumns {
        public static final String TABLE_NAME = "ItemListLine";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_ITEM_ID = "ItemId";
        public static final String COLUMN_NAME_ITEM_LIST_ID = "ItemListId";
    }

}
