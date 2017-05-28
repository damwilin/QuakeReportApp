package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Damian on 5/28/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeItem>> {
    private static String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "Loader startLoading() method");
        forceLoad();
    }

    @Override
    public List<EarthquakeItem> loadInBackground() {
        Log.e(LOG_TAG, "Loader loadInBackground() method");
        if (url == null)
            return null;
        return QueryUtils.fetchEarthquakes(url);
    }
}
