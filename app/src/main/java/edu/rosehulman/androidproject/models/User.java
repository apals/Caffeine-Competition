package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by palssoa on 1/25/2015.
 */
public class User implements Comparable, Serializable {

    public static final int HALF_LIFE = 1;

    private ArrayList<Drink> mDrinkHistory;
    private String mUsername;

    public User(String username) {
        mUsername = username;
        mDrinkHistory = new ArrayList<>();
    }

    public String getUsername() {
        return mUsername;
    }

    public int getCaffeineLevel() {
        int caffeineLevel = 0;

        for (int i = 0; i < mDrinkHistory.size(); i++) {
            float a = mDrinkHistory.get(i).getRemainingCaffeine();
            if(a >= 0)
                caffeineLevel += a; //* 0.1; //tempDrink.getDrinkType().getCaffeineAmount() * (seconds/(HALF_LIFE * 10)); //3600));
        }
        return caffeineLevel;
    }

    public void drink(Drink drink) {
        mDrinkHistory.add(0, drink);
    }
    public ArrayList<Drink> getDrinkHistory() {
        return mDrinkHistory;
    }

    @Override
    public int compareTo(Object another) {
        return ((User) another).getCaffeineLevel() - getCaffeineLevel();
    }

}
