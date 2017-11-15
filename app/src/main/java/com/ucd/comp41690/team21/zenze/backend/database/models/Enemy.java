package com.ucd.comp41690.team21.zenze.backend.database.models;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherService;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

/**
 * Created by timothee on 06/11/17.
 */

public class Enemy extends BaseModel {

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

    private String name;
    private int damage;
    private int speed;
    private String graphicsPath;
    private String desc;
    private int scale;
    private WeatherStatus weatherStatus;

    public Enemy(int id, String name, int damage, int speed, String graphicsPath, String desc, int scale, WeatherStatus weatherStatus) {
        super(id);
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

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }

    public void setName(String name) { this.name = name; }

    public void setDesc(String desc) { this.desc = desc; }

    public void setDamage(int damage) { this.damage = damage; }

    public void setGraphicsPath(String graphicsPath) { this.graphicsPath = graphicsPath; }

    public void setScale(int scale) { this.scale = scale; }

    public void setSpeed(int speed) { this.speed = speed; }

    public void setWeatherStatus(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
    }
}
