package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class AttackInfo {

    private AttackInfo() { }

    public static final class AttackEntry implements BaseColumns {
        public static final String TABLE_NAME = "Attack";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DAMAGE = "Damage";
        public static final String COLUMN_NAME_SCALE = "Scale";
        public static final String COLUMN_NAME_ATTACK_STATE = "AttackState";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_GRAPHICS_PATH = "GraphicsPath";
    }

}
