package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.provider.BaseColumns;

/**
 * Created by timothee on 18/10/17.
 */

public final class ItemListModel {

    /**
     * Private constructor to prevent someone from accidentally trying to instanciate an
     * ItemListModel object.
     */
    private ItemListModel() {}

    public static final class ItemListModelEntry implements BaseColumns {
        public static final String TABLE_NAME = "ItemList";
    }

}
