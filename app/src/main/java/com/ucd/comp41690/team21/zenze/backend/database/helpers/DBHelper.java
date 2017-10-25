package com.ucd.comp41690.team21.zenze.backend.database.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.PlayerGenerator;
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
     * @param pageNumber
     * @param pageSize
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

    public int updatePlayer(Player player) {



        return 0;
    }

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
