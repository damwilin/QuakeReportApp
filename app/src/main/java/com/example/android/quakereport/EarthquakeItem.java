package com.example.android.quakereport;

/**
 * Created by Damian on 5/22/2017.
 */

public class EarthquakeItem {
    private String mag;
    private String loc;
    private String date;

    public EarthquakeItem(String mag, String loc, String date) {
        this.mag = mag;
        this.loc = loc;
        this.date = date;
    }

    public String getMag() {
        return mag;
    }

    public String getLoc() {
        return loc;
    }

    public String getDate() {
        return date;
    }
}
