package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * Created by timothee on 06/11/17.
 */

@Entity(tableName = "enemy")
public class Enemy {

    /**
     * Enemy
     * +-----------------------------------------+------------------+-----------------+
     * |                   Field                 |       type       |       Key       |
     * +-----------------------------------------+------------------+-----------------+
     * | _ID                                     |  INTEGER         |  PRI            |
     * | Name                                    |  VARCHAR(64)     |                 |
     * | Damage                                  |  INTEGER         |                 |
     * | Scale                                   |  INTEGER         |                 |
     * | Description                             |  TEXT            |                 |
     * | GraphicsPath                            |  TEXT            |                 |
     * | Speed                                   |  INTEGER         |                 |
     * | WeatherStatus                           |  INTEGER         |                 |
     * +-----------------------------------------+------------------+-----------------+
     */

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Damage")
    private int damage;

    @ColumnInfo(name = "Speed")
    private int speed;

    @ColumnInfo(name = "GraphicsPath")
    private String graphicsPath;

    @ColumnInfo(name = "Description")
    private String desc;

    @ColumnInfo(name = "Scale")
    private int scale;

    @ColumnInfo(name = "WeatherStatus")
    private int weatherStatus;

    public Enemy() {}

    @Ignore
    public Enemy(String name, int damage, int speed, String graphicsPath, String desc, int scale, int weatherStatus) {
        this.name = name;
        this.damage = damage;
        this.speed = speed;
        this.graphicsPath = graphicsPath;
        this.desc = desc;
        this.scale = scale;
        this.weatherStatus = weatherStatus;
    }

    public String getName() { return name; }

    public String getDesc() {
        return desc;
    }

    public int getDamage() {
        return damage;
    }

    public int getScale() {
        return scale;
    }

    public int getSpeed() {
        return speed;
    }

    public String getGraphicsPath() {
        return graphicsPath;
    }

    public int getWeatherStatus() {
        return weatherStatus;
    }

    public void setName(String name) { this.name = name; }

    public void setDesc(String desc) { this.desc = desc; }

    public void setDamage(int damage) { this.damage = damage; }

    public void setGraphicsPath(String graphicsPath) { this.graphicsPath = graphicsPath; }

    public void setScale(int scale) { this.scale = scale; }

    public void setSpeed(int speed) { this.speed = speed; }

    public void setWeatherStatus(int weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
