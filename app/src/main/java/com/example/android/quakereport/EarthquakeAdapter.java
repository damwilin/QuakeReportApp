package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Damian on 5/22/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeItem> {
    String nearLocText;
    String locText;
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
        //Split text of localization
        splitLocalizationText(currEq.getLoc());
        //Find view that shows near localization
        TextView nearLoc = (TextView) listView.findViewById(R.id.near_localization);
        //Set text on that view
        nearLoc.setText(nearLocText);
        //Find view that shows localization
        TextView loc = (TextView) listView.findViewById(R.id.localization);
        //Set text on that view
        loc.setText(locText);
        //Find view that shows date
        TextView date = (TextView) listView.findViewById(R.id.date);
        //Set text on that view
        date.setText(currEq.getDate());
        //Find view that shows time
        TextView time = (TextView) listView.findViewById(R.id.time);
        //Set text on that view
        time.setText(currEq.getTime());
        return listView;
    }

    private void splitLocalizationText(String localization) {
        int ofIndex = localization.indexOf("of");
        if (ofIndex == -1) {
            nearLocText = "Near the";
            locText = localization;
        } else {
            nearLocText = localization.substring(0, ofIndex + 2);
            locText = localization.substring(ofIndex + 2);
        }

    }
}
