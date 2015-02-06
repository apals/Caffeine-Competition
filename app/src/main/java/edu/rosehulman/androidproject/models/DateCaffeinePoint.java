package edu.rosehulman.androidproject.models;

import java.util.Date;

/**
 * Created by palssoa on 2/5/2015.
 */
public class DateCaffeinePoint {

    private Date date;
    private int caffeine;
    public DateCaffeinePoint(Date date, int caffeine) {
        this.date = date;
        this.caffeine = caffeine;
    }
    public Date getDate() {
        return date;
    }
    public int getCaffeine() {
        return caffeine;
    }
}
