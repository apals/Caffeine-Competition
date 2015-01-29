package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.adapters.UserListAdapter;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class UserListFragment extends ListFragment {

    private static UserListFragment instance;

    public static UserListFragment getInstance() {
        if(instance == null)
            instance = new UserListFragment();
        return instance;
    }

    User[] users = new User[] {
            new User("John", new ArrayList<Drink>()),
            new User("Percy", new ArrayList<Drink>()),
            new User("Honken", new ArrayList<Drink>()),
            new User("McLovin", new ArrayList<Drink>()),
            new User("Barbossa", new ArrayList<Drink>()),
            new User("Adhi", new ArrayList<Drink>()),
            new User("Abdullah", new ArrayList<Drink>()),
            new User("Zlatan", new ArrayList<Drink>())
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of countries **/
        UserListAdapter listAdapter = new UserListAdapter(getActivity(), R.layout.userlist_row_layout, users);
        /** Setting the list adapter for the ListFragment */
        setListAdapter(listAdapter);

        return inflater.inflate(R.layout.fragment_userlist, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i = 0; i < 8; i++) {
            users[i].getDrinkHistory().add(new Drink(new DrinkType("Drink #" + i, i*2), new Date()));
        }
        Arrays.sort(users);
    }
}
