package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class EnemyListLineInfo {

    private EnemyListLineInfo() { }

    public static final class EnemyListLineEntry implements BaseColumns {
        public static final String TABLE_NAME = "EnemyListLine";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_ENEMY_ID = "EnemyId";
        public static final String COLUMN_NAME_ENEMY_LIST_ID = "EnemyListId";
    }

}
