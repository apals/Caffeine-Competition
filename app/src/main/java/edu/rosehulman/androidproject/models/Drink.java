package edu.rosehulman.androidproject.models;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink {

    private String mName;
    private int mCaffeineAmount;

    public Drink(String name, int caffeineAmount) {
        mName = name;
        mCaffeineAmount = caffeineAmount;
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
}
