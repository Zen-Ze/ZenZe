package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListLineInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class AttackListLineGenerator {

    public static final String CREATE_ATTACK_LIST_LINE_TABLE = "CREATE TABLE " + AttackListLineInfo.AttackListLineEntry.TABLE_NAME + " ("
            + AttackListLineInfo.AttackListLineEntry._ID + " INTEGER AUTOINCREMENT,"
            + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_AMOUNT + " INTEGER,"
            + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_ID + " INTEGER,"
            + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID + " INTEGER,"
            + "PRIMARY KEY (" + AttackListLineInfo.AttackListLineEntry._ID +")"
            + "FOREIGN KEY (" + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID+ ") REFERENCES " + AttackListInfo.AttackListEntry.TABLE_NAME +"(" + AttackListInfo.AttackListEntry._ID + ")"
            + "FOREIGN KEY (" + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_ID + ") REFERENCES " + AttackInfo.AttackEntry.TABLE_NAME +"(" + AttackInfo.AttackEntry._ID + "))";

    public static final String DELETE_ATTACK_LIST_LINE_TABLE = "DROP TABLE IF EXISTS " + AttackListLineInfo.AttackListLineEntry.TABLE_NAME;


}
