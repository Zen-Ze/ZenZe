package com.ucd.comp41690.team21.zenze.backend.database.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.PlayerGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.List;

/**
 * Created by timothee on 18/10/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ZenZeDb.db";

    public static final String DATABASE_LOGCAT = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create all tables
        //
        /* WATCH OUT WHEN CREATING TABLES, FOLLOW THE ORDER OF EXECUTION OF THE QUERIES
        * FOREIGN KEY CONSTRAINTS
        * */

        sqLiteDatabase.execSQL(ItemListGenerator.CREATE_ITEM_LIST_TABLE);
        sqLiteDatabase.execSQL(ItemGenerator.CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(PlayerGenerator.CREATE_PLAYER_TABLE);
        sqLiteDatabase.execSQL(ItemListLineGenerator.CREATE_ITEM_LIST_LINE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Delete all dbs

        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Players
     */
    public void addPlayer(Player player) {}

    public Player getPlayer(int playerId) { return null; }

    public List<Player> getPlayers(int pageNumber, int pageSize) { return null; }

    public int updatePlayer(Player player) { return 0; }

    public void deletePlayer(Player player) {}

    /**
     * Items
     */
    public Item getItem(int itemId) {return null;}

    public List<Item> getItems(int pageNumber, int pageSize) { return null; }

    // No update, add or delete, shouldn't be useful, as we'll use dumps

    /**
     * Item Lists
     */
    public void addItemList(Player player) { }

    public ItemList getItemList(int itemListId) { return null; }

    public void deleteItemList(ItemList itemList) { }

    /**
     * Item List Lines
     */
    public void addItemListLine(ItemListLine itemListLine) { }

    public ItemListLine getItemListLine(int itemListLineId) { return null; }

    public List<ItemListLine> getItemListLines(int pageNumber, int pageSize, int itemListId) { return null; }

    public int updateItemListLine(ItemListLine itemListLine) { return 0; }

    public void deleteItemListLine(ItemListLine itemListLine) {}

}
