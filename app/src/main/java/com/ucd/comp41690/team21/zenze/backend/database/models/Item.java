package com.ucd.comp41690.team21.zenze.backend.database.models;

<<<<<<< HEAD
=======
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;

>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
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
<<<<<<< HEAD
=======
     * | WeatherStatus                           |  INTEGER         |                 |
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
     * +-----------------------------------------+------------------+-----------------+
     */

    private String name;
    private String desc;
    private String imgPath;
<<<<<<< HEAD

    public Item(int id, String name, String desc, String imgPath) {
=======
    private WeatherStatus weatherStatus;

    public Item(int id, String name, String desc, String imgPath, WeatherStatus weatherStatus) {
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
        super(id);
        this.name = name;
        this.desc = desc;
        this.imgPath = imgPath;
<<<<<<< HEAD
    }

    public Item(int id, String name, String imgPath) {
        this(id, name, null, imgPath);
=======
        this.weatherStatus = weatherStatus;
    }

    public Item(int id, String name, String imgPath, WeatherStatus weatherStatus) {
        this(id, name, null, imgPath, weatherStatus);
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
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

<<<<<<< HEAD
=======
    public void setWeatherStatus(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
    public String getDesc() {
        return desc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }
<<<<<<< HEAD
=======

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
}
