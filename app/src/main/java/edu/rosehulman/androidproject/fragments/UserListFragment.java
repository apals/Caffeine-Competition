package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.adapters.UserListAdapter;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class UserListFragment extends ListFragment {

    private static UserListFragment instance;
    private UserListAdapter listAdapter;
    private Handler mHandler = new Handler();

    public static UserListFragment getInstance() {
        if (instance == null)
            instance = new UserListFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of countries **/
        listAdapter = new UserListAdapter(getActivity(), R.layout.userlist_row_layout, ((MainActivity) getActivity()).getUsers());
        /** Setting the list adapter for the ListFragment */
        setListAdapter(listAdapter);

        return inflater.inflate(R.layout.fragment_userlist, container, false);
    }

    public void updateList() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startUpdating() {

    }


    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }

}
