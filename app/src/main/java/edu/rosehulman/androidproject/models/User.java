package edu.rosehulman.androidproject.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palssoa on 1/25/2015.
 */
public class User {

    private List<Drink> mDrinkHistory;

    public User() {
        mDrinkHistory = new ArrayList<>();
    }
}
