package edu.rosehulman.androidproject.models;

/**
 * Created by palssoa on 2/6/2015.
 */
public class CommonDrink implements Comparable {
    private int mTimesConsumed;
    private DrinkType mDrinkType;

    public CommonDrink(DrinkType drinkType, int timesConsumed) {
        mDrinkType = drinkType;
        mTimesConsumed = timesConsumed;
    }


    public int getTimesConsumed() {
        return mTimesConsumed;
    }

    public DrinkType getDrinkType() {
        return mDrinkType;
    }

    @Override
    public int compareTo(Object another) {
        return ((CommonDrink) another).getTimesConsumed() - mTimesConsumed;
    }
}
