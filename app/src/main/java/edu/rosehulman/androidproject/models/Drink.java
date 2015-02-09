package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.Date;

import edu.rosehulman.androidproject.fragments.HomeFragment;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink implements Serializable, Comparable {

    private static final double CAFFEINE_HALF_LIFE = 5.7; //Caffeine half-life in hours
    private static final int START_PERIOD = 60; //Seconds
    private DrinkType mDrinkType;
    private Date mDateTime;

    public Drink(DrinkType drinkType, Date date) {
        mDrinkType = drinkType;
        mDateTime = date;
    }

    public double getRemainingCaffeineFrom(Date date, int weight, String gender) {
        long seconds = Math.abs((date).getTime() - getDateTime().getTime())/1000;
        long ms = Math.abs((date).getTime() - getDateTime().getTime());
        double bodyWater = 0.58;
        if (gender == "female") {
            bodyWater = 0.49;
        }
        double caffeine = ((0.806 * (getDrinkType().getCaffeineAmount() / 18) * 1.2)/((bodyWater*weight))) - (0.015*(seconds/360D));
        if (caffeine > 0) {
            if (seconds > START_PERIOD) {
                return caffeine;
            } else {
                return caffeine / (START_PERIOD*1000) * ms;
            }
        } else {
            return 0;
        }
    }

    public double getRemainingCaffeine(int weight, String gender) {
        return getRemainingCaffeineFrom(new Date(), weight, gender);
    }

    public String getFormattedDate() {
        String[] dateObjects = mDateTime.toString().split(" ");
        String[] timeObjects = dateObjects[3].split(":");
        return timeObjects[0] + ":" + timeObjects[1];
    }

    public String toString() {
        return "drinkName: " + mDrinkType.getDrinkName() + " caffeineAmount: " + mDrinkType.getCaffeineAmount();

    }

    public DrinkType getDrinkType() {
        return mDrinkType;
    }

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date mDateTime) {
        this.mDateTime = mDateTime;
    }

    @Override
    public int compareTo(Object another) {
        return ((Drink) another).getDateTime().compareTo(getDateTime());
    }

    public long getSecondsPassed() {
        return Math.abs((new Date()).getTime() - getDateTime().getTime())/1000;
    }
}
