package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Damian on 5/22/2017.
 */

public class EarthquakeItem {
    private String mag;
    private String loc;
    private String date;
    private String time;
    private Long timeInMiliseconds;

    public EarthquakeItem(String mag, String loc, Long timeInMiliseconds) {
        this.mag = mag;
        this.loc = loc;
        this.timeInMiliseconds = timeInMiliseconds;
        changeTimeToDate();
        changeTimeToTime();
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

}
