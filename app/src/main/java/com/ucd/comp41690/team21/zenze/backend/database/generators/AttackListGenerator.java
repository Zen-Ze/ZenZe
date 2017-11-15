package com.ucd.comp41690.team21.zenze.backend.database.generators;

import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListInfo;

/**
 * Created by timothee on 08/11/17.
 */

public class AttackListGenerator {
    
    public static final String CREATE_ATTACK_LIST = "CREATE TABLE " + AttackListInfo.AttackListEntry.TABLE_NAME + " ("
            + AttackListInfo.AttackListEntry._ID + " INTEGER AUTOINCREMENT,"
            + "PRIMARY KEY (" + AttackListInfo.AttackListEntry._ID +"))";
    
    public static final String DELETE_ATTACK_LIST = "DROP TABLE IF EXISTS " + AttackListInfo.AttackListEntry.TABLE_NAME;
    
}
