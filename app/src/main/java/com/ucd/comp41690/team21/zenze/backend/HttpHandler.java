package com.ucd.comp41690.team21.zenze.backend;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by timothee on 18/10/17.
 */

public class HttpHandler {

    /**
     * This methods calls a "get" method on the url, and returns a string corresponding to the received data.
     * @param reqUrl the url to get the data from
     * @return a string corresponding to the data from the url
     */
    public String makeServiceCall(String reqUrl) {
        String ret = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            ret = convertStreamToString(in);
//            Log.d("HttpHandler", ret);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }

    /**
     * This method will convert everything coming in the InputStream to a String.
     * @param in Input Stream to convert to string
     * @return the converted Input Stream as a String
     */
    private String convertStreamToString(InputStream in) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();

    }
}
