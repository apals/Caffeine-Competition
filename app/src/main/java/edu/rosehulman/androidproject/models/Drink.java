package edu.rosehulman.androidproject.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink implements Serializable {

    private DrinkType mDrinkType;
    private Date mDateTime;

    public Drink(DrinkType drinkType, Date date) {
        mDrinkType = drinkType;
        mDateTime = date;
    }

    public double getRemainingCaffeine() {

        Date nowDate = new Date();
        long seconds = Math.abs(nowDate.getTime() - getDateTime().getTime())/1000;
        double a = getDrinkType().getCaffeineAmount() - seconds;
        return a;
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
}
