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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public ListView earthquakeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        earthquakeListView = (ListView) findViewById(R.id.list);
        //Create object for EarthquakeAsyncClass
        EarthquakeAsyncClass earthquakeAsyncClass = new EarthquakeAsyncClass();
        // Create a list of earthquake locations.
        earthquakeAsyncClass.execute(USGS_REQUEST_URL);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

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

    private void updateUI(ArrayList<EarthquakeItem> earthquakeItems) {
        EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakeItems);
        earthquakeListView.setAdapter(adapter);
    }

    private class EarthquakeAsyncClass extends AsyncTask<String, Void, ArrayList<EarthquakeItem>> {
        @Override
        protected ArrayList<EarthquakeItem> doInBackground(String... strings) {
            //strings[0] = USGS_REQUEST_URL;
            if (strings.length < 1 || strings[0] == null)
                return null;
            return QueryUtils.fetchEarthquakes(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<EarthquakeItem> earthquakeItems) {
            updateUI(earthquakeItems);
        }
    }
}
