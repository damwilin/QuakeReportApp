package com.example.android.quakereport;

/**
 * Created by Damian on 5/22/2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Sample JSON response for a USGS query
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthquakeItem} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthquakeItem> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeItem> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject root = new JSONObject(jsonResponse);
            //Extract “features”JSONArray
            JSONArray features = root.getJSONArray("features");
            //Loop through each feature in the array
            for (int i = 0; i < features.length(); i++) {
                //Get earthquake JSONObject at position i
                JSONObject currEq = features.getJSONObject(i);
                //Get “properties” JSONObject
                JSONObject properties = currEq.getJSONObject("properties");
                //Extract “mag” for magnitude
                double mag = properties.getDouble("mag");
                //Extract “place” for location
                String place = properties.getString("place");
                //Extract “time” for time
                Long time = properties.getLong("time");
                //Extract "url" for website
                String website = properties.getString("url");
                //Create Earthquake java object from magnitude, location, and time
                //Add earthquake to list of earthquakes
                earthquakes.add(new EarthquakeItem(mag, place, time, website));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving jsonResult", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (stream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


}