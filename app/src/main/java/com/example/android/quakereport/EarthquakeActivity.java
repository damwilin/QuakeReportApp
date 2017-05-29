/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<EarthquakeItem>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static final int LOADER_ID = 1;
    private EarthquakeAdapter earthquakeAdapter;
    private TextView empty;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        empty = (TextView) findViewById(R.id.empty);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(empty);
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<EarthquakeItem>());
        earthquakeListView.setAdapter(earthquakeAdapter);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
        Log.e(LOG_TAG, "initLoader()");
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthquakeItem currentItem = (EarthquakeItem) adapterView.getItemAtPosition(i);
                openWebPage(currentItem.getWebsite());
            }
        });
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    public Loader<List<EarthquakeItem>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "onCreateLoader() callback");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeItem>> loader, List<EarthquakeItem> data) {
        Log.e(LOG_TAG, "onLoadFinished() callback");
        progressBar.setVisibility(View.GONE);
        empty.setText("No earthquakes found");
        earthquakeAdapter.clear();
        if (data != null || !data.isEmpty())
            earthquakeAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeItem>> loader) {
        Log.e(LOG_TAG, "onLoaderReset() callback");
        earthquakeAdapter.clear();
    }
}
