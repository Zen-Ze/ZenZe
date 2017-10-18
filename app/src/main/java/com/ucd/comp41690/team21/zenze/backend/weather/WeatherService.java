package com.ucd.comp41690.team21.zenze.backend.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.ucd.comp41690.team21.zenze.backend.JSONParser;

import org.json.JSONObject;

/**
 * Created by timothee on 16/10/17.
 */

public class WeatherService {

    /**
     * URL to the openweather app, will need to hide the appid at some point somehow...
     */
    private static String OPENWEATHERMAP_DEFAULT_URL = "http://api.openweathermap.org/data/2.5/weather?appid=d9ae475ffdf0ffe9da6c449cb86acb8b";

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

        double[] location = {};
        String url = getUrlFromLocation(location);

        // TODO: retrieve weather from openweathermap, get a status accordingly
        JSONParse jsonParse = (JSONParse) new JSONParse() {
            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                Log.d("WeatherService", "onPostExec");
                // do someshit with the jsonObject here, like getting a weather status
            }
        }.execute(url);

        // As of now, before I start coding anything else...
        return WeatherStatus.SUNNY;
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getUrlFromLocation(double[] location) {
        if(location.length==2) {
            String urlFromLocation = OPENWEATHERMAP_DEFAULT_URL + "&lat=" + location[0] + "&lon=" + location[1];
            return urlFromLocation;
        }
        return OPENWEATHERMAP_DEFAULT_URL + "&lat=" + DEFAULT_LOCATION[0] + "&lon=" + DEFAULT_LOCATION[1];
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

    private static class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
//            Log.d("WeatherService", "IS this my url ?? ?? ?? ? ?? " + strings[0]);
            JSONParser jParser = new JSONParser();
            JSONObject jsonObject = jParser.getJSONFromUrl(strings[0]);
            return jsonObject;
        }

    }

}
