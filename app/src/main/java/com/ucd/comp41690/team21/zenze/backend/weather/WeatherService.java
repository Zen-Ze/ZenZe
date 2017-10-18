package com.ucd.comp41690.team21.zenze.backend.weather;

/**
 * Created by timothee on 16/10/17.
 */

public class WeatherService {

    /**
     * Default Location, in our case Dublin.
     */
    private static double[] DEFAULT_LOCATION = {53.350140,-6.266155};

    /**
     * Retrieves the weather status based on the weather where the person is located:
     *     - Uses the GPS to figure out where the person is
     *     - Gets the weather from the location
     *     - Retrieves a weather status from this
     * @return The weather status
     */
    public static WeatherStatus getWeatherStatus() {
        // As of now, before I start coding anything else...
        return WeatherStatus.SUNNY;
    }

    /**
     * Method to get the location of the user. This uses the GPS sensor in order to find out the
     * player's location.
     * @return The location of the user as a String, by default Dublin, Format: {lat, lon}
     */
    private double[] getLocation() {
        double[] ret = DEFAULT_LOCATION;

        return ret;
    }

}
