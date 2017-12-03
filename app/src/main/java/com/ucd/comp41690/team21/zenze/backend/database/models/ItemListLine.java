package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by timothee on 18/10/17.
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

@Entity(tableName = "ItemListLine", foreignKeys = {
        @ForeignKey(entity = ItemList.class,
                parentColumns = "id",
                childColumns = "ItemListId"),
        @ForeignKey(entity = Item.class,
                parentColumns = "id",
                childColumns = "ItemId")
}
)
public class ItemListLine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Amount")
    private int amount;

    @ColumnInfo(name = "ItemId")
    private int itemId;

    @ColumnInfo(name = "ItemListId")
    private int itemListId;

    public ItemListLine() {}

    @Ignore
    public ItemListLine(int amount, int itemId, int itemListId) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
