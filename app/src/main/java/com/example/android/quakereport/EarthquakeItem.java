package com.example.android.quakereport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static com.example.android.quakereport.R.id.mag;

/**
 * Created by Damian on 5/22/2017.
 */

public class EarthquakeItem {
    private double magDouble;
    private String mag;
    private String loc;
    private String date;
    private String time;
    private Long timeInMiliseconds;
    private String website;

    public EarthquakeItem(double magDouble, String loc, Long timeInMiliseconds, String website) {
        this.magDouble = magDouble;
        this.loc = loc;
        this.timeInMiliseconds = timeInMiliseconds;
        this.website = website;
        changeTimeToDate();
        changeTimeToTime();
        formatMag();
    }

    public String getMag() {
        return mag;
    }

    public String getLoc() {
        return loc;
    }

    public double getMagDouble() {
        return magDouble;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    private void changeTimeToDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        date = dateFormat.format(timeInMiliseconds);
    }

    private void changeTimeToTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        time = timeFormat.format(timeInMiliseconds);
    }

    private void formatMag() {
        DecimalFormat formatter = new DecimalFormat("0.00");
        mag = formatter.format(magDouble);
    }

    public String getWebsite() {
        return website;
    }
}
