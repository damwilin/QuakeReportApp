package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
    private static final String LOCATION_SEPARATOR = " of ";
    private String nearLocText;
    private String locText;

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
        //Set the proper background color on the magnitude circle
        //Fetch the background from the TextView, which is a GradientDrawble
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        //Get the appropriate color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currEq.getMagDouble());
        //Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
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
        if (localization.contains(LOCATION_SEPARATOR)) {
            String parts[] = localization.split(LOCATION_SEPARATOR);
            nearLocText = parts[0] + LOCATION_SEPARATOR;
            locText = parts[1];
        } else {
            nearLocText = "Near the";
            locText = localization;
        }
    }

    private int getMagnitudeColor(double magnitude) {
        int colorID = 0;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 1:
                colorID = R.color.magnitude1;
                break;
            case 2:
                colorID = R.color.magnitude2;
                break;
            case 3:
                colorID = R.color.magnitude3;
                break;
            case 4:
                colorID = R.color.magnitude4;
                break;
            case 5:
                colorID = R.color.magnitude5;
                break;
            case 6:
                colorID = R.color.magnitude6;
                break;
            case 7:
                colorID = R.color.magnitude7;
                break;
            case 8:
                colorID = R.color.magnitude7;
                break;
            case 9:
                colorID = R.color.magnitude9;
                break;
            default:
                colorID = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorID);
    }
}
