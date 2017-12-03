package com.ucd.comp41690.team21.zenze.backend.weather;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.backend.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by timothee on 16/10/17.
 */

public class WeatherService {

    /**
     * URL to the openweather app, will need to hide the appid at some point somehow...
     */
    private static String OPENWEATHERMAP_DEFAULT_URL = "http://api.openweathermap.org/data/2.5/weather?appid=";

    /**
     * Default Location, in our case Dublin.
     */
    private static double[] DEFAULT_LOCATION = {53.350140,-6.266155};

    /**
     * Retrieves the weather status based on the weather where the person is located:
     *     - Uses the GPS to figure out where the person is
     *     - Gets the weather from the location
     *     - Retrieves a weather status from this
     * @param location a location retrieved from the GPS
     * @return The weather status
     */
    public static WeatherStatus getWeatherStatus(Location location, Context context) {

        double[] loc;

        if(location==null) {
            loc = DEFAULT_LOCATION;
        }
        else {
            loc = new double[]{location.getLatitude(), location.getLongitude()};
        }

        final WeatherStatus[] ret = {WeatherStatus.SUNNY};

        String url = getUrlFromLocation(loc, context);

        // TODO: retrieve weather from openweathermap, get a status accordingly
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONParse().execute(url).get();
//            Log.d("WEATHER SERVICE", "ARE WE HERE?? ?? ?");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(jsonObject != null) {
            try {
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject firstArrayObject = weatherArray.getJSONObject(0);

                int weatherId = firstArrayObject.getInt("id");

//                Log.d("WEATHER SERVICE", "ARE WE THERE?? ?? " + weatherId);
                // Taken from http://openweathermap.org/weather-conditions ROUGHLY, might need ajustement
                if((300<=weatherId && weatherId<600)
                        ||(weatherId==802)
                        ||(weatherId==803)
                        ||(weatherId==804)
                        ||(weatherId==960)
                        ||(weatherId==961)
                        ||(weatherId==962)
                        ||(weatherId==901))
                { ret[0] = WeatherStatus.RAINY; }
                else if((600<=weatherId && weatherId<700)
                        ||(weatherId == 903)
                        ||(weatherId == 906))
                { ret[0] = WeatherStatus.SNOWY; }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return ret[0];
    }

    /**
     *
     * @param location
     * @return
     */
    public static String getUrlFromLocation(double[] location, Context context) {
        //String apiKey = context.getString(R.string.openweathermap_api_key);
        String apiKey= "1d11da8b7fd7895acd7d78bf97be9839";

//        Log.d("WEATHER SERVICE", "getUrlFromLocation: apikey= " + apiKey);

        if(location.length==2) {
            String urlFromLocation = OPENWEATHERMAP_DEFAULT_URL + apiKey + "&lat=" + location[0] + "&lon=" + location[1];
            return urlFromLocation;
        }
        return OPENWEATHERMAP_DEFAULT_URL + "&lat=" + DEFAULT_LOCATION[0] + "&lon=" + DEFAULT_LOCATION[1];
    }

    private static class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
            JSONObject jsonObject = jParser.getJSONFromUrl(strings[0]);
            return jsonObject;
        }

    }

}
