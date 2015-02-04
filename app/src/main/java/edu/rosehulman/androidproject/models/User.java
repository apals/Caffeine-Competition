package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by palssoa on 1/25/2015.
 */
public class User implements Comparable, Serializable {

    public static final int HALF_LIFE = 1;

    private ArrayList<Drink> mDrinkHistory;
    private String mUsername;
    private ArrayList<Drink> drinkHistory;

    public User(String username, ArrayList<Drink> drinkHistory) {
        mUsername = username;
        mDrinkHistory = drinkHistory;
    }

    public User(HashMap<String, Object> map) {
        this((String) map.get("username"), (ArrayList<Drink>) map.get("drinkHistory"));
    }

    public String getUsername() {
        return mUsername;
    }


    //TODO: make double or float (probably double since firebase stores doubles)
    public int getCaffeineLevel() {
        int caffeineLevel = 0;

        for (int i = 0; i < mDrinkHistory.size(); i++) {
            double a = mDrinkHistory.get(i).getRemainingCaffeine();
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

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("username", mUsername);
        userMap.put("drinkHistory", mDrinkHistory);
        return userMap;

    }

    @Override
    public int compareTo(Object another) {
        return ((User) another).getCaffeineLevel() - getCaffeineLevel();
    }

    public void setDrinkHistory(ArrayList<Drink> drinkHistory) {
        this.drinkHistory = drinkHistory;
    }
}
