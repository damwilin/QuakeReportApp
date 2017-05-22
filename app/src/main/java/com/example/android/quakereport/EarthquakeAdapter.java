package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.R;

import java.util.ArrayList;

/**
 * Created by Damian on 5/22/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeItem> {
    public EarthquakeAdapter(Activity context, ArrayList<EarthquakeItem> earthquakeArrayList) {
        super(context, 0, earthquakeArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null)
            listView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        EarthquakeItem currEq = getItem(position);
        //Find view that shows magnitude
        TextView mag = (TextView) listView.findViewById(R.id.mag);
        //Set text on that view
        mag.setText(currEq.getMag());
        //Find view that shows localization
        TextView loc = (TextView) listView.findViewById(R.id.loc);
        //Set text on that view
        loc.setText(currEq.getLoc());
        //Find view that shows date
        TextView date = (TextView) listView.findViewById(R.id.date);
        //Set text on that view
        date.setText(currEq.getDate());
        return listView;
    }
}
