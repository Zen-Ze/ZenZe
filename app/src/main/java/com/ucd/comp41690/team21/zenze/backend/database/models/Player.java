package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by timothee on 18/10/17.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = AttackList.class,
            parentColumns = "id",
            childColumns = "AttackListId"),
        @ForeignKey(entity = EnemyList.class,
                parentColumns = "id",
                childColumns = "EnemyListId"),
        @ForeignKey(entity = ItemList.class,
            parentColumns = "id",
            childColumns = "ItemListId")
        }, tableName = "player")
public class Player {

    /**
     * Player
     * +-----------------------------------------+------------------+-----------------+
     * |                   Field                 |       type       |       Key       |
     * +-----------------------------------------+------------------+-----------------+
     * | _ID                                     |  INTEGER         |  PRI            |
     * | LastCoordX                              |  INTEGER         |                 |
     * | LastCoordY                              |  INTEGER         |                 |
     * | SavedHealth                             |  INTEGER         |                 |
     * | Username                                |  VARCHAR(64)     |                 |
     * | CurrentLevel                            |  INTEGER         |                 |
     * | ItemListId                              |  INTEGER         |  FGN            |
     * | EnemyListId                             |  INTEGER         |  FGN            |
     * | AttackListId                            |  INTEGER         |  FGN            |
     * +-----------------------------------------+------------------+-----------------+
     */

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "LastCoordX")
    private int lastCoordX;

    @ColumnInfo(name = "LastCoordY")
    private int lastCoordY;

    @ColumnInfo(name = "SavedHealth")
    private int savedHealth;

    @ColumnInfo(name = "Username")
    private String username;

    @ColumnInfo(name = "CurrentLevel")
    private int currentLevel;

    @ColumnInfo(name = "ItemListId")
    private int itemListId;

    @ColumnInfo(name = "AttackListId")
    private int attackListId;

    @ColumnInfo(name = "EnemyListId")
    private int enemyListId;

    public Player() {}

    @Ignore
    public Player(int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId, int attackListId, int enemyListId) {
//        this.id = id;
        this.lastCoordX = lastCoordX;
        this.lastCoordY = lastCoordY;
        this.savedHealth = savedHealth;
        this.username = username;
        this.currentLevel = currentLevel;
        this.itemListId = itemListId;
        this.attackListId = attackListId;
        this.enemyListId = enemyListId;
    }
//
//    public Player(int id) {this.id = id;}

    public void setLastCoordX(int lastCoordX) {
        this.lastCoordX = lastCoordX;
    }

    public void setLastCoordY(int lastCoordY) {
        this.lastCoordY = lastCoordY;
    }

    public void setSavedHealth(int savedHealth) {
        this.savedHealth = savedHealth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public void setItemListId(int itemListId) { this.itemListId = itemListId; }

    public void setAttackListId(int attackListId) { this.attackListId = attackListId; }

    public void setEnemyListId(int enemyListId) { this.enemyListId = enemyListId; }

    public int getLastCoordX() {
        return lastCoordX;
    }

    public int getLastCoordY() {
        return lastCoordY;
    }

    public int getSavedHealth() {
        return savedHealth;
    }

    public String getUsername() {
        return username;
    }

    public int getCurrentLevel() { return currentLevel; }

    public int getItemListId() { return itemListId; }

    public int getAttackListId() { return attackListId; }

    public int getEnemyListId() { return enemyListId; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

