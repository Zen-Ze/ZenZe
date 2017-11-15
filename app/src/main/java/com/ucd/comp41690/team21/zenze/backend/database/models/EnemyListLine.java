package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 06/11/17.
 */

public class EnemyListLine extends BaseModel {

    /**
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

    private int amount;
    private int enemyId;
    private int enemyListId;

    public EnemyListLine(int id, int amount, int enemyId, int enemyListId) {
        super(id);
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
}
