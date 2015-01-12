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
        ArrayAdapter<String> listAdapter = new CustomListAdapter(getActivity(), R.layout.userlist_row_layout);
        listAdapter.addAll(countries);
        /** Setting the list adapter for the ListFragment */
        setListAdapter(listAdapter);

        return inflater.inflate(R.layout.fragment_userlist, container, false);

    }

    /**
     * A simple implementation of list adapter.
     */
    class CustomListAdapter extends ArrayAdapter<String> {

        public CustomListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.userlist_row_layout, null);
                final View a = convertView;

                //the following code should probably be in onitemclick up there but the collapse of the item didn't work
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View toolbar = a.findViewById(R.id.toolbar);

                        // Creating the expand animation for the item
                        ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                        // Start the animation on the toolbar
                        toolbar.startAnimation(expandAni);
                    }
                });
            }

            ((TextView)convertView.findViewById(R.id.username)).setText(getItem(position));

            // Resets the toolbar to be closed
            View toolbar = convertView.findViewById(R.id.toolbar);
            ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
            toolbar.setVisibility(View.GONE);

            return convertView;
        }
    }




}
