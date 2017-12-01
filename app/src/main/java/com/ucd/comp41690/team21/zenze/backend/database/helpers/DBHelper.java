package com.ucd.comp41690.team21.zenze.backend.database.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ucd.comp41690.team21.zenze.backend.database.generators.AttackGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.AttackListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.AttackListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.EnemyGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.EnemyListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.EnemyListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.ItemListLineGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.generators.PlayerGenerator;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.AttackListLineInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.EnemyListLineInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.ItemListLineInfo;
import com.ucd.comp41690.team21.zenze.backend.database.misc.PlayerInfo;
import com.ucd.comp41690.team21.zenze.backend.database.models.Attack;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Enemy;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

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
        sqLiteDatabase.execSQL(EnemyListGenerator.CREATE_ENEMY_LIST);
        sqLiteDatabase.execSQL(AttackListGenerator.CREATE_ATTACK_LIST);
        sqLiteDatabase.execSQL(ItemListGenerator.CREATE_ITEM_LIST_TABLE);
        sqLiteDatabase.execSQL(ItemGenerator.CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(AttackGenerator.CREATE_ATTACK_TABLE);
        sqLiteDatabase.execSQL(EnemyGenerator.CREATE_ENEMY_TABLE);
        sqLiteDatabase.execSQL(PlayerGenerator.CREATE_PLAYER_TABLE);
        sqLiteDatabase.execSQL(ItemListLineGenerator.CREATE_ITEM_LIST_LINE_TABLE);
        sqLiteDatabase.execSQL(EnemyListLineGenerator.CREATE_ENEMY_LIST_LINE_TABLE);
        sqLiteDatabase.execSQL(AttackListLineGenerator.CREATE_ATTACK_LIST_LINE_TABLE);
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
                                PlayerInfo.PlayerEntry.COLUMN_NAME_ITEM_LIST_ID,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_ATTACK_LIST_ID,
                                PlayerInfo.PlayerEntry.COLUMN_NAME_ENEMY_LIST_ID
                            }, PlayerInfo.PlayerEntry._ID + "=?", new String[] { String.valueOf(playerId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Player player = new Player(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),
                    Integer.parseInt(cursor.getString(8))
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
                        Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)),
                        Integer.parseInt(cursor.getString(8))
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
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_ATTACK_LIST_ID, player.getAttackListId());
        contentValues.put(PlayerInfo.PlayerEntry.COLUMN_NAME_ENEMY_LIST_ID, player.getEnemyListId());

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
                        ItemInfo.ItemEntry.COLUMN_NAME_SPRITE_PATH,
                        ItemInfo.ItemEntry.COLUMN_NAME_WEATHER_STATUS
                }, ItemInfo.ItemEntry._ID + "=?", new String[] { String.valueOf(itemId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Item item = new Item(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
        );

        return item;
    }

    /**
     * Retrieve items from the database
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Item> getItems(int pageNumber, int pageSize, WeatherStatus weatherStatus) {
        String query =
                "SELECT * FROM " + ItemInfo.ItemEntry.TABLE_NAME
                        + " WHERE " + ItemInfo.ItemEntry.COLUMN_NAME_WEATHER_STATUS + " = ? "
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(weatherStatus.getValue())});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Item item = new Item(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(3),
                        WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
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

    /**
     * Attack
     */

    /**
     * Retrieve an attack from the database.
     * @param attackId
     * @return
     */
    public Attack getAttack(int attackId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AttackInfo.AttackEntry.TABLE_NAME,
                new String[] {
                        AttackInfo.AttackEntry._ID,
                        AttackInfo.AttackEntry.COLUMN_NAME_ATTACK_STATE,
                        AttackInfo.AttackEntry.COLUMN_NAME_NAME,
                        AttackInfo.AttackEntry.COLUMN_NAME_DAMAGE,
                        AttackInfo.AttackEntry.COLUMN_NAME_GRAPHICS_PATH,
                        AttackInfo.AttackEntry.COLUMN_NAME_SCALE,
                        AttackInfo.AttackEntry.COLUMN_NAME_DESCRIPTION,
                        AttackInfo.AttackEntry.COLUMN_NAME_WEATHER_STATUS
                }, AttackInfo.AttackEntry._ID + "=?", new String[] { String.valueOf(attackId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Attack attack = new Attack(
                Integer.parseInt(cursor.getString(0)),
                Boolean.valueOf(cursor.getString(1)),
                cursor.getString(2),
                Integer.valueOf(cursor.getString(3)),
                cursor.getString(4),
                Integer.valueOf(cursor.getString(5)),
                cursor.getString(6),
                WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
        );

        return attack;
    }

    /**
     * Retrieve attacks from the database
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Attack> getAttacks(int pageNumber, int pageSize, WeatherStatus weatherStatus) {
        String query =
                "SELECT * FROM " + AttackInfo.AttackEntry.TABLE_NAME
                        + " WHERE " + AttackInfo.AttackEntry.COLUMN_NAME_WEATHER_STATUS + " = ? "
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<Attack> attacks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(weatherStatus.getValue())});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Attack attack = new Attack(
                        Integer.parseInt(cursor.getString(0)),
                        Boolean.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        Integer.valueOf(cursor.getString(3)),
                        cursor.getString(4),
                        Integer.valueOf(cursor.getString(5)),
                        cursor.getString(6),
                        WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
                );

                attacks.add(attack);

                cursor.moveToNext();
            }
        }

        return attacks;

    }

    /**
     * AttackList
     */

    /**
     * Add an attack list and link it to a player.
     * This is done by creating the attack list, retrieving the id of the created attack list, and
     * proceed to update the player.
     * @param player
     */
    public void addAttackList(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        db.insert(AttackListInfo.AttackListEntry.TABLE_NAME, null, contentValues);

        String queryToGetLastItem = "SELECT * FROM " + AttackListInfo.AttackListEntry.TABLE_NAME
                + " ORDER BY " + AttackListInfo.AttackListEntry._ID + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(queryToGetLastItem, null);
        cursor.moveToLast();

        int attackListId = Integer.valueOf(cursor.getString(0));

        player.setAttackListId(attackListId);

        this.updatePlayer(player);

        db.close();
    }

    /**
     * Retrieve an item list
     * @param attackListId
     * @return
     */
    public AttackList getAttackList(int attackListId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AttackListInfo.AttackListEntry.TABLE_NAME,
                new String[] {
                        AttackListInfo.AttackListEntry._ID
                }, AttackListInfo.AttackListEntry._ID + "=?", new String[] { String.valueOf(attackListId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        AttackList attackList = new AttackList(
                Integer.parseInt(cursor.getString(0))
        );

        return attackList;
    }

    public void deleteAttackList(AttackList attackList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AttackListInfo.AttackListEntry.TABLE_NAME, AttackListInfo.AttackListEntry._ID + " = ?", new String[] { String.valueOf(attackList.getId()) });
        db.close();
    }

    /**
     * AttackListLine
     */

    /**
     * Insert an item list line in the database.
     * @param attackListLine
     */
    public void addAttackListLine(AttackListLine attackListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_AMOUNT, attackListLine.getAmount());
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_ID, attackListLine.getAttackId());
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID, attackListLine.getAttackListId());

        db.insert(AttackListLineInfo.AttackListLineEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * Retrieve an item list line from the database.
     * @param attackListLineId
     * @return
     */
    public AttackListLine getAttackListLine(int attackListLineId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // int id, int amount, int itemId, int itemListId


        Cursor cursor = db.query(ItemInfo.ItemEntry.TABLE_NAME,
                new String[] {
                        AttackListLineInfo.AttackListLineEntry._ID,
                        AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_AMOUNT,
                        AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_ID,
                        AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID
                }, ItemListLineInfo.ItemListLineEntry._ID + "=?", new String[] { String.valueOf(attackListLineId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        AttackListLine attackListLine = new AttackListLine(
                Integer.parseInt(cursor.getString(0)),
                Integer.valueOf(cursor.getString(1)),
                Integer.valueOf(cursor.getString(2)),
                Integer.valueOf(cursor.getString(3))
        );

        return attackListLine;
    }

    /**
     * Retrieve item list lines from item list id.
     * You should use this and then retrieve items 1 by 1 for displaying.
     * Also, if you change page, PLEASE do not re-create an instance, we're working with mobile devices
     * so there are limitations.
     * @param pageNumber
     * @param pageSize
     * @param attackListId
     * @return
     */
    public List<AttackListLine> getAttackListLines(int pageNumber, int pageSize, int attackListId) {
        String query =
                "SELECT * FROM " + AttackListLineInfo.AttackListLineEntry._ID
                        + "WHERE " + AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID + " = " + String.valueOf(attackListId)
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<AttackListLine> attackListLines = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                AttackListLine attackListLine = new AttackListLine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.valueOf(cursor.getString(1)),
                        Integer.valueOf(cursor.getString(2)),
                        Integer.valueOf(cursor.getString(3))
                );

                attackListLines.add(attackListLine);

                cursor.moveToNext();
            }
        }

        return attackListLines;

    }

    /**
     * Update an item list line.
     * @param attackListLine
     * @return
     */
    public int updateAttackListLine(AttackListLine attackListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_AMOUNT, attackListLine.getAmount());
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_LIST_ID, attackListLine.getAttackListId());
        contentValues.put(AttackListLineInfo.AttackListLineEntry.COLUMN_NAME_ATTACK_ID, attackListLine.getAttackId());

        return db.update(AttackListLineInfo.AttackListLineEntry.TABLE_NAME, contentValues, AttackListLineInfo.AttackListLineEntry._ID + " = ?",
                new String[] { String.valueOf(attackListLine.getId()) });
    }

    /**
     * Delete an item list line from the database.
     * @param attackListLine
     */
    public void deleteAttackListLine(AttackListLine attackListLine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AttackListLineInfo.AttackListLineEntry.TABLE_NAME, AttackListLineInfo.AttackListLineEntry._ID + " = ?"
                , new String[] { String.valueOf(attackListLine.getId()) });
        db.close();
    }

    /**
     * Enemy
     */

    /**
     * Retrieve an enemy from the database.
     * @param enemyId
     * @return
     */
    public Enemy getEnemy(int enemyId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AttackInfo.AttackEntry.TABLE_NAME,
                new String[] {
                        EnemyInfo.EnemyEntry._ID,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_NAME,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_DAMAGE,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_SPEED,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_GRAPHICS_PATH,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_DESCRIPTION,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_SCALE,
                        EnemyInfo.EnemyEntry.COLUMN_NAME_WEATHER_STATUS
                }, AttackInfo.AttackEntry._ID + "=?", new String[] { String.valueOf(enemyId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        Enemy enemy = new Enemy(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.valueOf(cursor.getString(2)),
                Integer.valueOf(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                Integer.valueOf(cursor.getString(6)),
                WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
        );

        return enemy;
    }

    /**
     * Retrieve enemies from the database
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<Enemy> getEnemies(int pageNumber, int pageSize, WeatherStatus weatherStatus) {
        String query =
                "SELECT * FROM " + EnemyInfo.EnemyEntry.TABLE_NAME
                        + " WHERE " + EnemyInfo.EnemyEntry.COLUMN_NAME_WEATHER_STATUS + " = ? "
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<Enemy> enemies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(weatherStatus.getValue())});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Enemy enemy = new Enemy(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.valueOf(cursor.getString(2)),
                        Integer.valueOf(cursor.getString(3)),
                        cursor.getString(4),
                        cursor.getString(5),
                        Integer.valueOf(cursor.getString(6)),
                        WeatherStatus.valueOf(Integer.parseInt(cursor.getString(4)))
                );

                enemies.add(enemy);

                cursor.moveToNext();
            }
        }

        return enemies;

    }

    /**
     * EnemyList
     */
    /**
     * Add an enemy list and link it to a player.
     * This is done by creating the enemy list, retrieving the id of the created enemy list, and
     * proceed to update the player.
     * @param player
     */
    public void addEnemyList(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        db.insert(EnemyListInfo.EnemyListEntry.TABLE_NAME, null, contentValues);

        String queryToGetLastItem = "SELECT * FROM " + EnemyListInfo.EnemyListEntry.TABLE_NAME
                + " ORDER BY " + EnemyListInfo.EnemyListEntry._ID + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(queryToGetLastItem, null);
        cursor.moveToLast();

        int enemyListId = Integer.valueOf(cursor.getString(0));

        player.setAttackListId(enemyListId);

        this.updatePlayer(player);

        db.close();
    }

    /**
     * Retrieve an item list
     * @param enemyListId
     * @return
     */
    public EnemyList getEnemyList(int enemyListId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EnemyListInfo.EnemyListEntry.TABLE_NAME,
                new String[] {
                        EnemyListInfo.EnemyListEntry._ID
                }, EnemyListInfo.EnemyListEntry._ID + "=?", new String[] { String.valueOf(enemyListId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        EnemyList enemyList = new EnemyList(
                Integer.parseInt(cursor.getString(0))
        );

        return enemyList;
    }

    public void deleteEnemyList(EnemyList enemyList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnemyListInfo.EnemyListEntry.TABLE_NAME, EnemyListInfo.EnemyListEntry._ID + " = ?", new String[] { String.valueOf(enemyList.getId()) });
        db.close();
    }


    /**
     * EnemyListLine
     */

    /**
     * Insert an item list line in the database.
     * @param enemyListLine
     */
    public void addEnemyListLine(EnemyListLine enemyListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_AMOUNT, enemyListLine.getAmount());
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_ID, enemyListLine.getEnemyId());
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID, enemyListLine.getEnemyListId());

        db.insert(EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * Retrieve an item list line from the database.
     * @param enemyListLineId
     * @return
     */
    public EnemyListLine getEnemyListLine(int enemyListLineId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // int id, int amount, int itemId, int itemListId

        Cursor cursor = db.query(EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME,
                new String[] {
                        EnemyListLineInfo.EnemyListLineEntry._ID,
                        EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_AMOUNT,
                        EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_ID,
                        EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID
                }, EnemyListLineInfo.EnemyListLineEntry._ID + "=?", new String[] { String.valueOf(enemyListLineId) }, null, null, null, null);

        if (cursor!=null) cursor.moveToFirst();

        EnemyListLine enemyListLine = new EnemyListLine(
                Integer.parseInt(cursor.getString(0)),
                Integer.valueOf(cursor.getString(1)),
                Integer.valueOf(cursor.getString(2)),
                Integer.valueOf(cursor.getString(3))
        );

        return enemyListLine;
    }

    /**
     * Retrieve item list lines from item list id.
     * You should use this and then retrieve items 1 by 1 for displaying.
     * Also, if you change page, PLEASE do not re-create an instance, we're working with mobile devices
     * so there are limitations.
     * @param pageNumber
     * @param pageSize
     * @param enemyListId
     * @return
     */
    public List<EnemyListLine> getEnemyListLines(int pageNumber, int pageSize, int enemyListId) {
        String query =
                "SELECT * FROM " + EnemyListLineInfo.EnemyListLineEntry._ID
                        + "WHERE " + EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID + " = " + String.valueOf(enemyListId)
                        + " LIMIT " + String.valueOf(pageSize)
                        + " OFFSET " + String.valueOf(pageSize*pageNumber);

        List<EnemyListLine> enemyListLines = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                EnemyListLine enemyListLine = new EnemyListLine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.valueOf(cursor.getString(1)),
                        Integer.valueOf(cursor.getString(2)),
                        Integer.valueOf(cursor.getString(3))
                );

                enemyListLines.add(enemyListLine);

                cursor.moveToNext();
            }
        }

        return enemyListLines;

    }

    /**
     * Update an item list line.
     * @param enemyListLine
     * @return
     */
    public int updateEnemyListLine(EnemyListLine enemyListLine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_AMOUNT, enemyListLine.getAmount());
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_LIST_ID, enemyListLine.getEnemyListId());
        contentValues.put(EnemyListLineInfo.EnemyListLineEntry.COLUMN_NAME_ENEMY_ID, enemyListLine.getEnemyId());

        return db.update(EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME, contentValues, EnemyListLineInfo.EnemyListLineEntry._ID + " = ?",
                new String[] { String.valueOf(enemyListLine.getId()) });
    }

    /**
     * Delete an item list line from the database.
     * @param enemyListLine
     */
    public void deleteEnemyListLine(EnemyListLine enemyListLine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnemyListLineInfo.EnemyListLineEntry.TABLE_NAME, EnemyListLineInfo.EnemyListLineEntry._ID + " = ?"
                , new String[] { String.valueOf(enemyListLine.getId()) });
        db.close();
    }

}
