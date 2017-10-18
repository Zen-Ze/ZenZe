package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 */

public final class ItemListLineModel {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an
     * ItemListLineModel object.
     */
    private ItemListLineModel() {}

    public static final class ItemListLineModelEntry implements BaseColumns {
        public static final String TABLE_NAME = "ItemListLine";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_ITEM_ID = "ItemId";
        public static final String COLUMN_NAME_ITEM_LIST_ID = "ItemListId";
    }

}
