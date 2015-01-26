package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by palssoa on 1/25/2015.
 */
public class User implements Comparable, Serializable {

    private int mCaffeineLevel;
    private List<Drink> mDrinkHistory;
    private String mUsername;

    public User(String username, int caffeineLevel) {
        mUsername = username;
        mCaffeineLevel = caffeineLevel;
        mDrinkHistory = new ArrayList<>();
    }

    public String getUsername() {
        return mUsername;
    }

    public int getCaffeineLevel() {
        return mCaffeineLevel;
    }

    public void setCaffeineLevel(int mCaffeineLevel) {
        this.mCaffeineLevel = mCaffeineLevel;
    }

    public List<Drink> getDrinkHistory() {
        return mDrinkHistory;
    }

    public void setDrinkHistory(List<Drink> mDrinkHistory) {
        this.mDrinkHistory = mDrinkHistory;
    }

    @Override
    public int compareTo(Object another) {
        return ((User) another).getCaffeineLevel() - mCaffeineLevel;
    }
}
