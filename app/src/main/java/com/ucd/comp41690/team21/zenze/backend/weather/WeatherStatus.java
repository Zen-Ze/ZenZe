package com.ucd.comp41690.team21.zenze.backend.weather;

/**
 * Created by timothee on 16/10/17.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * This represents the status of the weather, wether it's sunny, rainy, or snowy
 */
public enum WeatherStatus {
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
}
