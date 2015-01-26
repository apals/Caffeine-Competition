package edu.rosehulman.androidproject.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palssoa on 1/25/2015.
 */
public class User {

    private int mCaffeineLevel;
    private List<Drink> mDrinkHistory;

    public User() {
        mDrinkHistory = new ArrayList<>();
    }

    public int getmCaffeineLevel() {
        return mCaffeineLevel;
    }

    public void setmCaffeineLevel(int mCaffeineLevel) {
        this.mCaffeineLevel = mCaffeineLevel;
    }

    public List<Drink> getmDrinkHistory() {
        return mDrinkHistory;
    }

    public void setmDrinkHistory(List<Drink> mDrinkHistory) {
        this.mDrinkHistory = mDrinkHistory;
    }
}
