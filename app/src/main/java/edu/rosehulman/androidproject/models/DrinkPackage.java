package edu.rosehulman.androidproject.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by palssoa on 1/26/2015.
 */
public class DrinkPackage {

    private String mUser;
    private Drink mDrink;

    public DrinkPackage(String user, Drink drink) {
        mUser = user;
        mDrink = drink;
    }


    public Map<String, Object> toMap(){
        Map<String, Object> messageMap = new HashMap<String, Object>();
        messageMap.put("user", this.mUser);
        messageMap.put("drink", this.mDrink);
        return messageMap;
    }

}
