package com.ucd.comp41690.team21.zenze.backend.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * Created by timothee on 18/10/17.
 * Item
 * +-----------------------------------------+------------------+-----------------+
 * |                   Field                 |       type       |       Key       |
 * +-----------------------------------------+------------------+-----------------+
 * | _ID                                     |  INTEGER         |  PRI            |
 * | Name                                    |  VARCHAR(64)     |                 |
 * | Description                             |  TEXT            |                 |
 * | SpritePath                              |  TEXT            |                 |
 * | WeatherStatus                           |  INTEGER         |                 |
 * +-----------------------------------------+------------------+-----------------+
 */

@Entity(tableName = "item")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Description")
    private String desc;

    @ColumnInfo(name = "SpritePath")
    private String imgPath;

    @ColumnInfo(name = "WeatherStatus")
    private int weatherStatus;

    public Item() { }

    @Ignore
    public Item(String name, String desc, String imgPath, WeatherStatus weatherStatus) {
        this.name = name;
        this.desc = desc;
        this.imgPath = imgPath;
        this.weatherStatus = weatherStatus.getValue();
    }

    @Ignore
    public Item(String name, String imgPath, WeatherStatus weatherStatus) {
        this(name, null, imgPath, weatherStatus);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeatherStatus(int weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public int getWeatherStatus() {
        return weatherStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
