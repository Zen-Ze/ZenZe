package com.ucd.comp41690.team21.zenze.backend.database.models;

/**
 * Created by timothee on 06/11/17.
 */

public class Attack extends BaseModel {

    /**
     * Attack
     * +-----------------------------------------+------------------+-----------------+
     * |                   Field                 |       type       |       Key       |
     * +-----------------------------------------+------------------+-----------------+
     * | _ID                                     |  INTEGER         |  PRI            |
     * | Name                                    |  VARCHAR(64)     |                 |
     * | Damage                                  |  INTEGER         |                 |
     * | Scale                                   |  INTEGER         |                 |
     * | Description                             |  TEXT            |                 |
     * | GraphicsPath                            |  TEXT            |                 |
     * | AttackState                             |  INTEGER         |                 |
     * +-----------------------------------------+------------------+-----------------+
     */

    private boolean attackState;
    private String name;
    private int damage;
    private String graphicsPath;
    private int scale;
    private String desc;

    public Attack(int id, boolean attackState, String name, int damage, String graphicsPath, int scale, String desc) {
        super(id);
        this.attackState = attackState;
        this.name = name;
        this.damage = damage;
        this.graphicsPath = graphicsPath;
        this.scale = scale;
        this.desc = desc;
    }

    public String getGraphicsPath() {
        return graphicsPath;
    }

    public int getDamage() {
        return damage;
    }

    public int getScale() {
        return scale;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setGraphicsPath(String graphicsPath) {
        this.graphicsPath = graphicsPath;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttackState(boolean attackState) {
        this.attackState = attackState;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }
}
