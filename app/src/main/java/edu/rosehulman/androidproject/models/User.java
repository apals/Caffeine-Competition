package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by palssoa on 1/25/2015.
 */

public class User implements Comparable, Serializable {
    private static final long KEEP_HISTORY = 48; //Hours to keep drink history
    private static final double MAX_CAFFEINE_LEVEL = 0.3;

    private ArrayList<Drink> mDrinkHistory;
    private String mUsername;
    private ArrayList<DateCaffeinePoint> points = new ArrayList<>();
    private int mWeight;
    private String mGender;
    private String mEmail;
    private String mPictureBase64;
    private int mId;

    public User(String username, String email, int weight, String gender, ArrayList<Drink> drinkHistory, String picture, int id) {
        mEmail = email;
        mWeight = weight;
        mGender = gender;
        mUsername = username;
        mDrinkHistory = drinkHistory;
        mPictureBase64 = picture;
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }


    public double getCaffeineLevel(Date date) {
        double caffeineLevel = 0;

        for (int i = 0; i < mDrinkHistory.size(); i++) {
            double caffeine = mDrinkHistory.get(i).getRemainingCaffeine(date, mWeight, mGender);
            caffeineLevel += caffeine;
        }

        //No user should have a caffeine level over MAX_CAFFEINE_LEVEL3
        return (caffeineLevel > MAX_CAFFEINE_LEVEL) ? MAX_CAFFEINE_LEVEL : caffeineLevel;
    }

    public double getCaffeineLevel() {
        return getCaffeineLevel(new Date());
    }

    public void drink(Drink drink) {
        mDrinkHistory.add(drink);
    }
    public ArrayList<Drink> getDrinkHistory() {
        return mDrinkHistory;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", mUsername);
        userMap.put("drinkHistory", mDrinkHistory);
        return userMap;

    }

    @Override
    public int compareTo(Object another) {
        double compare = ((User) another).getCaffeineLevel() - getCaffeineLevel();
        if(compare < 0)
            return -1;
        if(compare == 0)
            return 0;
        return 1;
    }


    public void setDrinkHistory(ArrayList<Drink> drinkHistory) {
        this.mDrinkHistory = drinkHistory;
    }

    public void addPoint(Date date, double caffeine) {
        points.add(new DateCaffeinePoint(date, caffeine));
    }

    public void removePoint(int i) {
        points.remove(i);
    }

    public ArrayList<DateCaffeinePoint> getPoints() {
        return points;
    }

    public String getEmail() {
        return mEmail;
    }

    public boolean clearOldDrinks() {
        boolean someThingRemoved = false;
        for (int i = 0; i < mDrinkHistory.size(); i++) {
            if (mDrinkHistory.get(i).getSecondsPassed() > 3600*KEEP_HISTORY) {
                mDrinkHistory.remove(i);
                someThingRemoved = true;
            }
        }
        return someThingRemoved;
    }

    public int getWeight() {
        return mWeight;
    }

    public String getGender() {
        return mGender;
    }

    public String getmPictureBase64() {
        return mPictureBase64;
    }

    public void setmPictureBase64(String mPictureBase64) {
        this.mPictureBase64 = mPictureBase64;
    }

    public void removeAllPoints() {
        points = new ArrayList<>();
    }

    public int getId() {
        return mId;
    }
}
