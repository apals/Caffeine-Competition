package edu.rosehulman.androidproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;

import edu.rosehulman.androidproject.ExpandAnimation;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.adapters.UserListAdapter;
import edu.rosehulman.androidproject.models.Drink;
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
            new User("John", 10),
            new User("Percy", 15),
            new User("Honken", 10),
            new User("McLovin", 17),
            new User("Barbossa", 4),
            new User("Adhi", 14),
            new User("Abdullah", 29),
            new User("Zlatan", 8)
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
            users[i].getDrinkHistory().add(new Drink("Drink #" + i, i*2, new Date()));
        }
        Arrays.sort(users);
    }
}
