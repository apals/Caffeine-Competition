package edu.rosehulman.androidproject.models;

import java.io.Serializable;

/**
 * Created by fruktprins on 26/01/15.
 */
public class DrinkType implements Serializable {

    private String mDrinkName;
    private float mCaffeineAmount;

    public DrinkType(String mDrinkName, float mCaffeineAmount) {
        this.mDrinkName = mDrinkName;
        this.mCaffeineAmount = mCaffeineAmount;
    }

    public String getDrinkName() {
        return mDrinkName;
    }

    public void setmDrinkName(String mDrinkName) {
        this.mDrinkName = mDrinkName;
    }

    public float getCaffeineAmount() {
        return mCaffeineAmount;
    }

    public void setmCaffeineAmount(float mCaffeineAmount) {
        this.mCaffeineAmount = mCaffeineAmount;
    }
}
