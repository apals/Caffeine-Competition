package edu.rosehulman.androidproject.models;

import java.io.Serializable;

/**
 * Created by fruktprins on 26/01/15.
 */
public class DrinkType implements Serializable {

    private String mDrinkName;
    private double mCaffeineAmount;

    public DrinkType(String mDrinkName, double mCaffeineAmount) {
        this.mDrinkName = mDrinkName;
        this.mCaffeineAmount = mCaffeineAmount;
    }

    public String getDrinkName() {
        return mDrinkName;
    }

    public void setmDrinkName(String mDrinkName) {
        this.mDrinkName = mDrinkName;
    }

    public double getCaffeineAmount() {
        return mCaffeineAmount;
    }

    public void setmCaffeineAmount(float mCaffeineAmount) {
        this.mCaffeineAmount = mCaffeineAmount;
    }
}
