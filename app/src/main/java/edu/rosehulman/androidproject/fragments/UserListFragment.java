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

import edu.rosehulman.androidproject.ExpandAnimation;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.adapters.UserListAdapter;

/**
 * Created by palssoa on 12/20/2014.
 */
public class UserListFragment extends ListFragment {

    String[] countries = new String[] {
            "India",
            "Pakistan",
            "Sri Lanka",
            "China",
            "Bangladesh",
            "Nepal",
            "Afghanistan",
            "North Korea",
            "South Korea",
            "Japan"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of countries **/
        ArrayAdapter<String> listAdapter = new UserListAdapter(getActivity(), R.layout.userlist_row_layout);
        listAdapter.addAll(countries);
        /** Setting the list adapter for the ListFragment */
        setListAdapter(listAdapter);

        return inflater.inflate(R.layout.fragment_userlist, container, false);

    }



}
