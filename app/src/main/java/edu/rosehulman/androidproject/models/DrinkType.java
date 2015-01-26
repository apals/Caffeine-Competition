package edu.rosehulman.androidproject.models;

/**
 * Created by fruktprins on 26/01/15.
 */
public class DrinkType {

    private String mDrinkName;
    private float mCaffeineAmount;

    public DrinkType(String mDrinkName, float mCaffeineAmount) {
        this.mDrinkName = mDrinkName;
        this.mCaffeineAmount = mCaffeineAmount;
    }

    public String getmDrinkName() {
        return mDrinkName;
    }

    public void setmDrinkName(String mDrinkName) {
        this.mDrinkName = mDrinkName;
    }

    public float getmCaffeineAmount() {
        return mCaffeineAmount;
    }

    public void setmCaffeineAmount(float mCaffeineAmount) {
        this.mCaffeineAmount = mCaffeineAmount;
    }
}
