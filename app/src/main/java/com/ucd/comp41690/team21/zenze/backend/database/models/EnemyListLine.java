package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by timothee on 06/11/17.
 * ItemListLine
 * +-----------------------------------------+------------------+-----------------+
 * |                   Field                 |       type       |       Key       |
 * +-----------------------------------------+------------------+-----------------+
 * | _ID                                     |  INTEGER         |  PRI            |
 * | Amount                                  |  INTEGER         |                 |
 * | EnemyId                                 |  INTEGER         |  FRN            |
 * | EnemyListId                             |  INTEGER         |  FRN            |
 * +-----------------------------------------+------------------+-----------------+
 */

@Entity(tableName = "EnemyListLine", foreignKeys = {
        @ForeignKey(entity = EnemyList.class,
                parentColumns = "id",
                childColumns = "EnemyListId"),
        @ForeignKey(entity = Enemy.class,
                parentColumns = "id",
                childColumns = "EnemyId")
    }
)
public class EnemyListLine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Amount")
    private int amount;

    @ColumnInfo(name = "EnemyId")
    private int enemyId;

    @ColumnInfo(name = "EnemyListId")
    private int enemyListId;

    public EnemyListLine() {
    }

    @Ignore
    public EnemyListLine(int amount, int enemyId, int enemyListId) {
        this.amount = amount;
        this.enemyId = enemyId;
        this.enemyListId = enemyListId;
    }

    public int getAmount() {
        return amount;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public int getEnemyListId() {
        return enemyListId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public void setEnemyListId(int enemyListId) {
        this.enemyListId = enemyListId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
