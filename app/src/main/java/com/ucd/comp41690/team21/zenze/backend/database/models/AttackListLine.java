package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 06/11/17.
 */

public class AttackListLine extends BaseModel {

    /**
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

    private int amount;
    private int attackId;
    private int attackListId;

    public AttackListLine(int id, int amount, int attackId, int attackListId) {
        super(id);
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
}
