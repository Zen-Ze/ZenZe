package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by timothee on 06/11/17.
 * EnemyList
 * +-----------------------------------------+------------------+-----------------+
 * |                   Field                 |       type       |       Key       |
 * +-----------------------------------------+------------------+-----------------+
 * | _ID                                     |  INTEGER         |  PRI            |
 * +-----------------------------------------+------------------+-----------------+
 */

@Entity(tableName = "enemylist")
public class EnemyList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
