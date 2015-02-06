package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink implements Serializable, Comparable {

    private static final double CAFFEINE_HALF_LIFE = 5.7; //Caffeine half-life in hours
    private DrinkType mDrinkType;
    private Date mDateTime;

    public Drink(DrinkType drinkType, Date date) {
        mDrinkType = drinkType;
        mDateTime = date;
    }

    public double getRemainingCaffeineFrom(Date date) {
        long seconds = Math.abs((date).getTime() - getDateTime().getTime())/1000;
        if (seconds > 60) {
            return getDrinkType().getCaffeineAmount() * Math.pow(0.5D, seconds / 3600*CAFFEINE_HALF_LIFE);
        } else {
            return (getDrinkType().getCaffeineAmount() / 60) * seconds;
        }
    }

    public double getRemainingCaffeine() {
        return getRemainingCaffeineFrom(new Date());
    }

    public String getFormattedDate() {
        String[] dateObjects = mDateTime.toString().split(" ");
        String[] timeObjects = dateObjects[3].split(":");
        return timeObjects[0] + ":" + timeObjects[1];
    }

    public String toString() {
        return "drinkName: " + mDrinkType.getDrinkName() + " caffienAmount: " + mDrinkType.getCaffeineAmount();

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
