package edu.rosehulman.androidproject.models;

import java.util.Date;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink {

    private String mName;
    private int mCaffeineAmount;
    private Date mDateTime;

    public Drink(String name, int caffeineAmount, Date date) {
        mName = name;
        mCaffeineAmount = caffeineAmount;
        mDateTime = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getCaffeineAmount() {
        return mCaffeineAmount;
    }

    public void setCaffeineAmount(int mCaffeineAmount) {
        this.mCaffeineAmount = mCaffeineAmount;
    }

    public Date getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(Date mDateTime) {
        this.mDateTime = mDateTime;
    }
}
