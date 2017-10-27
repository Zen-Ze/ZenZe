package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 18/10/17.
 */

public class ItemListLine extends BaseModel {

    /**
     * ItemListLine
     * +-----------------------------------------+------------------+-----------------+
     * |                   Field                 |       type       |       Key       |
     * +-----------------------------------------+------------------+-----------------+
     * | _ID                                     |  INTEGER         |  PRI            |
     * | Amount                                  |  INTEGER         |                 |
     * | ItemId                                  |  INTEGER         |  FRN            |
     * | ItemListId                              |  INTEGER         |  FRN            |
     * +-----------------------------------------+------------------+-----------------+
     */

    private int amount;
    private int itemId;
    private int itemListId;

    public ItemListLine(int id, int amount, int itemId, int itemListId) {
        super(id);
        this.amount = amount;
        this.itemId = itemId;
        this.itemListId = itemListId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItemListId(int itemListId) {
        this.itemListId = itemListId;
    }

    public int getAmount() {
        return amount;
    }

    public int getItemId() {
        return itemId;
    }

    public int getItemListId() {
        return itemListId;
    }
}
