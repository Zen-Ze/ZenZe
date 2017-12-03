package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * Created by timothee on 06/11/17.
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
 * | WeatherStatus                           |  INTEGER         |                 |
 * +-----------------------------------------+------------------+-----------------+
 */
@Entity(tableName = "attack")
public class Attack {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Damage")
    private int damage;

    @ColumnInfo(name = "GraphicsPath")
    private String graphicsPath;

    @ColumnInfo(name = "Scale")
    private int scale;

    @ColumnInfo(name = "Description")
    private String desc;

    @ColumnInfo(name = "WeatherStatus")
    private int weatherStatus;

    public Attack() {}

    @Ignore
    public Attack(String name, int damage, String graphicsPath, int scale, String desc, int weatherStatus) {
        this.name = name;
        this.damage = damage;
        this.graphicsPath = graphicsPath;
        this.scale = scale;
        this.desc = desc;
        this.weatherStatus = weatherStatus;
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

    public int getWeatherStatus() {
        return weatherStatus;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

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
