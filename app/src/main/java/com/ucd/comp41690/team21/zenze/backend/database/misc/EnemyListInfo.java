package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class EnemyListInfo {

    private EnemyListInfo() { }

    public static final class EnemyListEntry implements BaseColumns {
        public static final String TABLE_NAME = "EnemyList";
    }

}
