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
<<<<<<< HEAD
=======
     * | EnemyListId                             |  INTEGER         |  FGN            |
     * | AttackListId                            |  INTEGER         |  FGN            |
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
     * +-----------------------------------------+------------------+-----------------+
     */

    private int lastCoordX;
    private int lastCoordY;
    private int savedHealth;
    private String username;
    private int currentLevel;
    private int itemListId;
<<<<<<< HEAD

    public Player(int id, int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId) {
=======
    private int attackListId;
    private int enemyListId;

    public Player(int id, int lastCoordX, int lastCoordY, int savedHealth, String username, int currentLevel, int itemListId, int attackListId, int enemyListId) {
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
        super(id);
        this.lastCoordX = lastCoordX;
        this.lastCoordY = lastCoordY;
        this.savedHealth = savedHealth;
        this.username = username;
        this.currentLevel = currentLevel;
        this.itemListId = itemListId;
<<<<<<< HEAD
=======
        this.attackListId = attackListId;
        this.enemyListId = enemyListId;
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
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

<<<<<<< HEAD
=======
    public void setAttackListId(int attackListId) { this.attackListId = attackListId; }

    public void setEnemyListId(int enemyListId) { this.enemyListId = enemyListId; }

>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
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
<<<<<<< HEAD
=======

    public int getAttackListId() { return attackListId; }

    public int getEnemyListId() { return enemyListId; }
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
}

