package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 18/10/17.
 */

public class Player extends BaseModel {

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

    private int lastCoordX;
    private int lastCoordY;
    private int savedHealth;
    private String username;
    private int currentLevel;
    private int itemListId;
    private int attackListId;
    private int enemyListId;

    public Player(int id, int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId, int attackListId, int enemyListId) {
        super(id);
        this.lastCoordX = lastCoordX;
        this.lastCoordY = lastCoordY;
        this.savedHealth = savedHealth;
        this.username = username;
        this.currentLevel = currentLevel;
        this.itemListId = itemListId;
        this.attackListId = attackListId;
        this.enemyListId = enemyListId;
    }

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
}

