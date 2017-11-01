package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 *
 * This class' purpose is to store all SQL related information for Item List.
 */

public final class ItemListInfo {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an
     * ItemListInfo object.
     */
    private ItemListInfo() {}

    public static final class ItemListEntry implements BaseColumns {
        public static final String TABLE_NAME = "ItemList";
    }

}
