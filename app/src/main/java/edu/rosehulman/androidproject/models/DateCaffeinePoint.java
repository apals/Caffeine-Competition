package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by palssoa on 2/5/2015.
 */
public class DateCaffeinePoint implements Serializable {

    private Date date;
    private double caffeine;
    public DateCaffeinePoint(Date date, double caffeine) {
        this.date = date;
        this.caffeine = caffeine;
    }
    public Date getDate() {
        return date;
    }
    public double getCaffeine() {
        return caffeine;
    }
}
