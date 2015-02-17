package edu.rosehulman.androidproject;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.rosehulman.androidproject.fragments.HomeFragment;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 1/15/2015.
 */
public class UserUtils {

    public static double prePopulatePoints(User user, double highestCaffeineLevel) {
        if (user.getDrinkHistory().size() > 0) {
            long firstDrink = user.getDrinkHistory().get(user.getDrinkHistory().size() - 1).getDateTime().getTime();
            System.out.println("FIRST DRINK: " + user.getDrinkHistory().get(user.getDrinkHistory().size() - 1).getDateTime());
            long now = new Date().getTime();
            Date curDate = new Date();
            curDate.setTime(firstDrink);
            user.addPoint(curDate, 0);
            while(firstDrink < now) {
                Date nowDate = new Date();
                nowDate.setTime(firstDrink);
                System.out.println("prutt nowDate: " + nowDate);
                double caffeineLevel = user.getCaffeineLevel(nowDate);
                if (caffeineLevel > highestCaffeineLevel) {
                    highestCaffeineLevel = caffeineLevel;
                }
                if (caffeineLevel > 0) {
                    user.addPoint(nowDate, caffeineLevel);
                    System.out.println("prutt " + nowDate + " - " + caffeineLevel);
                }
                firstDrink += HomeFragment.CALCULATE_INTERVAL*10;
            }
        }
        return highestCaffeineLevel;
    }
}
