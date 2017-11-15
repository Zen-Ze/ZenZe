package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class AttackListLineInfo {

    private AttackListLineInfo() { }

    public static final class AttackListLineEntry implements BaseColumns {
        public static final String TABLE_NAME = "AttackListLine";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_ATTACK_ID = "AttackId";
        public static final String COLUMN_NAME_ATTACK_LIST_ID = "AttackListId";
    }

}
