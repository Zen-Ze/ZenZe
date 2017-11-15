package com.ucd.comp41690.team21.zenze.backend.weather;

/**
 * Created by timothee on 16/10/17.
 */

<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.Map;

>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
/**
 * This represents the status of the weather, wether it's sunny, rainy, or snowy
 */
public enum WeatherStatus {
<<<<<<< HEAD
    SUNNY,
    RAINY,
    SNOWY
=======
    SUNNY(0),
    RAINY(1),
    SNOWY(2);

    private int value;
    private static Map map = new HashMap<Integer, WeatherStatus>();

    private WeatherStatus(int value) {
        this.value = value;
    }

    static {
        for (WeatherStatus weatherStatus : WeatherStatus.values()) {
            map.put(weatherStatus.value, weatherStatus);
        }
    }

    public static WeatherStatus valueOf(int weatherStatus) {
        return (WeatherStatus) map.get(weatherStatus);
    }

    public int getValue() {
        return value;
    }
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
}
