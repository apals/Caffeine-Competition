package edu.rosehulman.androidproject.models;

import java.util.Date;

/**
 * Created by palssoa on 1/25/2015.
 */
public class Drink {

    private DrinkType mDrinkType;
    private Date mDateTime;

    public Drink(DrinkType drinkType, Date date) {
        mDrinkType = drinkType;
        mDateTime = date;
    }

    public String getFormattedDate() {
        String[] dateObjects = mDateTime.toString().split(" ");
        String[] timeObjects = dateObjects[3].split(":");
        return timeObjects[0] + ":" + timeObjects[1];
    }

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date mDateTime) {
        this.mDateTime = mDateTime;
    }
}
