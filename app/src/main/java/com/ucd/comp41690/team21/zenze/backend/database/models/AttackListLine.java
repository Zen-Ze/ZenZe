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
 * | AttackId                                |  INTEGER         |  FRN            |
 * | AttackListId                            |  INTEGER         |  FRN            |
 * +-----------------------------------------+------------------+-----------------+
 */
@Entity(tableName = "AttackListLine", foreignKeys = {
        @ForeignKey(entity = AttackList.class,
                parentColumns = "id",
                childColumns = "AttackListId"),
        @ForeignKey(entity = Attack.class,
            parentColumns = "id",
            childColumns = "AttackId")
        }
)
public class AttackListLine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Amount")
    private int amount;

    @ColumnInfo(name = "AttackId")
    private int attackId;

    @ColumnInfo(name = "AttackListId")
    private int attackListId;

    public AttackListLine() {}

    @Ignore
    public AttackListLine(int amount, int attackId, int attackListId) {
        this.amount = amount;
        this.attackId = attackId;
        this.attackListId = attackListId;
    }

    public int getAmount() {
        return amount;
    }

    public int getAttackId() {
        return attackId;
    }

    public int getAttackListId() {
        return attackListId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAttackId(int attackId) {
        this.attackId = attackId;
    }

    public void setAttackListId(int attackListId) {
        this.attackListId = attackListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
