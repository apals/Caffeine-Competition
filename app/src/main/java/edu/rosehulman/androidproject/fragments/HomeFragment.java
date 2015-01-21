package edu.rosehulman.androidproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.AddDrinkActivity;

/**
 * Created by palssoa on 12/20/2014.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        rootView.findViewById(R.id.fragment_home_button_drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDrinkActivity.class));
            }
        });


        return rootView;
    }
}
