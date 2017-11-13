package com.ucd.comp41690.team21.zenze.backend.database.models;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * Created by timothee on 18/10/17.
 */

public class Item extends BaseModel {

    /**
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

    private String name;
    private String desc;
    private String imgPath;
    private WeatherStatus weatherStatus;

    public Item(int id, String name, String desc, String imgPath, WeatherStatus weatherStatus) {
        super(id);
        this.name = name;
        this.desc = desc;
        this.imgPath = imgPath;
        this.weatherStatus = weatherStatus;
    }

    public Item(int id, String name, String imgPath, WeatherStatus weatherStatus) {
        this(id, name, null, imgPath, weatherStatus);
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

    public void setWeatherStatus(WeatherStatus weatherStatus) {
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

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }
}
