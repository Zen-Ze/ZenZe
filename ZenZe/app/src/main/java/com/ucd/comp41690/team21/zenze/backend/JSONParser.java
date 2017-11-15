package com.ucd.comp41690.team21.zenze.backend;

import android.webkit.HttpAuthHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by timothee on 18/10/17.
 */

public class JSONParser {

    private static JSONObject jObj = null;

    // constructor
    public JSONParser() {

    }

    /**
     * This method returns a JSON object from an URL.
     * @param url the URL of where the JSON is located
     * @return a JSON object
     */
    public JSONObject getJSONFromUrl(String url) {
        String jsonStr;
        HttpHandler httpHandler = new HttpHandler();
        jsonStr = httpHandler.makeServiceCall(url);
        if(jsonStr!=null) {
            try {
                jObj = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jObj;

    }

}
