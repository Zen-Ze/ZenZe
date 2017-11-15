package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class AttackListInfo {

    private AttackListInfo() { }

    public static final class AttackListEntry implements BaseColumns {
        public static final String TABLE_NAME = "AttackList";
    }

}
