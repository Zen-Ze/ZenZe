package com.ucd.comp41690.team21.zenze.backend.database.misc;

import android.provider.BaseColumns;

/**
 * Created by timothee on 06/11/17.
 */

public class EnemyInfo {

    private EnemyInfo() { }

    public static final class EnemyEntry implements BaseColumns {
        public static final String TABLE_NAME = "Enemy";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DAMAGE = "Damage";
        public static final String COLUMN_NAME_SCALE = "Scale";
        public static final String COLUMN_NAME_SPEED = "Speed";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_GRAPHICS_PATH = "GraphicsPath";
        public static final String COLUMN_NAME_WEATHER_STATUS = "WeatherStatus";
    }

}
