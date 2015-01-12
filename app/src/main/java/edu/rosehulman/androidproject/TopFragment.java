package edu.rosehulman.androidproject;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by palssoa on 12/20/2014.
 */
public class TopFragment extends ListFragment {

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,countries);

        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);

        View rootView = inflater.inflate(
                R.layout.fragment_top, container, false);
        //((TextView) rootView.findViewById(R.id.textView)).setText("bb");

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }


        });

        return rootView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }



}
