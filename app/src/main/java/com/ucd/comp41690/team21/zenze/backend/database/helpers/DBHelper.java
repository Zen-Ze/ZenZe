package com.ucd.comp41690.team21.zenze.backend.database.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.PlayerGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListLineInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.PlayerInfo;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;

import java.util.ArrayList;
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

    /**
     * Add a player to the database.
     * @param player
     */
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_USERNAME, player.getUsername());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_X, player.getLastCoordX());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_Y, player.getLastCoordY());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_SAVED_HEALTH, player.getSavedHealth());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID, player.getItemListId());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_CURRENT_LEVEL, player.getCurrentLevel());

        db.insert(PlayerInfo.PlayerEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * Retrieve a player from the database
     * @param playerId
     * @return
     */
    public Player getPlayer(int playerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        //int id, int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId

        Cursor cursor = db.query(PlayerInfo.PlayerEntry.TABLE_NAME,
                new String[] {
                                PlayerInfo.PlayerEntry._ID,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_X,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_Y,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_SAVED_HEALTH,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_USERNAME,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_CURRENT_LEVEL,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID
                            }, PlayerInfo.PlayerEntry._ID + "=?", new String[] { String.valueOf(playerId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Player player = new Player(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
                );

        return player;
    }

    /**
     * Retrieve players from the database in a paginated manner.
     * If you implement a paginated system, you should overwrite the list everytime, instead of
     * creating a new one.
     * @param pageNumber the page number, should start at 0
     * @param pageSize the amount of items per page
     * @return
     */
    public List<Player> getPlayers(int pageNumber, int pageSize) {
        String query =
                "SELECT * FROM " + PlayerInfo.PlayerEntry.TABLE_NAME
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<Player> players = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Player player = new Player(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6))
                );

                players.add(player);

                cursor.moveToNext();
            }
        }

        return players;
    }

    /**
     * Update a player in the database.
     * @param player
     * @return
     */
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_USERNAME, player.getUsername());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_X, player.getLastCoordX());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_LAST_COORD_Y, player.getLastCoordY());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_SAVED_HEALTH, player.getSavedHealth());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID, player.getItemListId());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_CURRENT_LEVEL, player.getCurrentLevel());

        return db.update(PlayerInfo.PlayerEntry.TABLE_NAME, contentValues, PlayerInfo.PlayerEntry._ID + " = ?",
                new String[] { String.valueOf(player.getId()) });
    }

    /**
     * Delete a player from the database.
     * @param player
     */
    public void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PlayerInfo.PlayerEntry.TABLE_NAME, PlayerInfo.PlayerEntry._ID + " = ?", new String[] { String.valueOf(player.getId()) });
        db.close();
    }

    /**
     * Items
     */

    /**
     * Retrieve an item from the database.
     * @param itemId
     * @return
     */
    public Item getItem(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();

        //int id, int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId

        Cursor cursor = db.query(ItemInfo.ItemEntry.TABLE_NAME,
                new String[] {
                        ItemInfo.ItemEntry._ID,
                        ItemInfo.ItemEntry.COLUMN_NAME_NAME,
                        ItemInfo.ItemEntry.COLUMN_NAME_DESCRIPTION,
                        ItemInfo.ItemEntry.COLUMN_NAME_SPRITE_PATH
                }, ItemInfo.ItemEntry._ID + "=?", new String[] { String.valueOf(itemId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Item item = new Item(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return item;
    }

    /**
     * Retrieve items from the database
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Item> getItems(int pageNumber, int pageSize) {
        String query =
                "SELECT * FROM " + ItemInfo.ItemEntry.TABLE_NAME
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Item item = new Item(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(3)
                );

                items.add(item);

                cursor.moveToNext();
            }
        }

        return items;

    }

    /**
     * Item Lists
     */

    /**
     * Add an item list and link it to a player.
     * This is done by creating the item list, retrieving the id of the created item list, and
     * proceed to update the player.
     * @param player
     */
    public void addItemList(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        db.insert(ItemListInfo.ItemListEntry.TABLE_NAME, null, contentValues);

        String queryToGetLastItem = "SELECT * FROM " + ItemListInfo.ItemListEntry.TABLE_NAME
                + " ORDER BY " + ItemListInfo.ItemListEntry._ID + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(queryToGetLastItem, null);
        cursor.moveToLast();

        int itemListId = Integer.valueOf(cursor.getString(0));

        player.setItemListId(itemListId);

        this.updatePlayer(player);

        db.close();
    }

    /**
     * Retrieve an item list
     * @param itemListId
     * @return
     */
    public ItemList getItemList(int itemListId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ItemListInfo.ItemListEntry.TABLE_NAME,
                new String[] {
                        ItemListInfo.ItemListEntry._ID
                }, ItemListInfo.ItemListEntry._ID + "=?", new String[] { String.valueOf(itemListId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        ItemList itemList = new ItemList(
                Integer.parseInt(cursor.getString(0))
        );

        return itemList;
    }

    public void deleteItemList(ItemList itemList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ItemListInfo.ItemListEntry.TABLE_NAME, ItemListInfo.ItemListEntry._ID + " = ?", new String[] { String.valueOf(itemList.getId()) });
        db.close();
    }

    /**
     * Item List Lines
     */

    /**
     * Insert an item list line in the database.
     * @param itemListLine
     */
    public void addItemListLine(ItemListLine itemListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_AMOUNT, itemListLine.getAmount());
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_ID, itemListLine.getItemId());
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID, itemListLine.getItemListId());

        db.insert(ItemListLineInfo.ItemListLineEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * Retrieve an item list line from the database.
     * @param itemListLineId
     * @return
     */
    public ItemListLine getItemListLine(int itemListLineId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // int id, int amount, int itemId, int itemListId

        Cursor cursor = db.query(ItemInfo.ItemEntry.TABLE_NAME,
                new String[] {
                        ItemListLineInfo.ItemListLineEntry._ID,
                        ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_AMOUNT,
                        ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_ID,
                        ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID
                }, ItemListLineInfo.ItemListLineEntry._ID + "=?", new String[] { String.valueOf(itemListLineId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        ItemListLine itemListLine = new ItemListLine(
                Integer.parseInt(cursor.getString(0)),
                Integer.valueOf(cursor.getString(1)),
                Integer.valueOf(cursor.getString(2)),
                Integer.valueOf(cursor.getString(3))
        );

        return itemListLine;
    }

    /**
     * Retrieve item list lines from item list id.
     * You should use this and then retrieve items 1 by 1 for displaying.
     * Also, if you change page, PLEASE do not re-create an instance, we're working with mobile devices
     * so there are limitations.
     * @param pageNumber
     * @param pageSize
     * @param itemListId
     * @return
     */
    public List<ItemListLine> getItemListLines(int pageNumber, int pageSize, int itemListId) {
        String query =
                "SELECT * FROM " + ItemListLineInfo.ItemListLineEntry._ID
                        + "WHERE " + ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID + " = " + String.valueOf(itemListId)
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<ItemListLine> itemListLines = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                ItemListLine itemListLine = new ItemListLine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.valueOf(cursor.getString(1)),
                        Integer.valueOf(cursor.getString(2)),
                        Integer.valueOf(cursor.getString(3))
                );

                itemListLines.add(itemListLine);

                cursor.moveToNext();
            }
        }

        return itemListLines;

    }

    /**
     * Update an item list line.
     * @param itemListLine
     * @return
     */
    public int updateItemListLine(ItemListLine itemListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_AMOUNT, itemListLine.getAmount());
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_LIST_ID, itemListLine.getItemListId());
        contentValues.put(ItemListLineInfo.ItemListLineEntry.COLUMN_NAME_ITEM_ID, itemListLine.getItemId());

        return db.update(ItemListLineInfo.ItemListLineEntry.TABLE_NAME, contentValues, ItemListLineInfo.ItemListLineEntry._ID + " = ?",
                new String[] { String.valueOf(itemListLine.getId()) });
    }

    /**
     * Delete an item list line from the database.
     * @param itemListLine
     */
    public void deleteItemListLine(ItemListLine itemListLine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ItemListLineInfo.ItemListLineEntry.TABLE_NAME, ItemListLineInfo.ItemListLineEntry._ID + " = ?"
                , new String[] { String.valueOf(itemListLine.getId()) });
        db.close();
    }

}
