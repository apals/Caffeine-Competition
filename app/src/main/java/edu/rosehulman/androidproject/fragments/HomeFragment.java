package edu.rosehulman.androidproject.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.AddDrinkActivity;
import edu.rosehulman.androidproject.adapters.DrinkAdapter;
import edu.rosehulman.androidproject.models.Drink;

/**
 * Created by palssoa on 12/20/2014.
 */
public class HomeFragment extends Fragment {

    public static final int DRINK_REQUEST_CODE = 0;
    private ArrayList<Drink> mDrinks;
    private DrinkAdapter mDrinkAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        rootView.findViewById(R.id.fragment_home_button_drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddDrinkActivity.class), DRINK_REQUEST_CODE);
            }
        });

        ListView listView = (ListView) rootView.findViewById(R.id.fragment_home_drink_list);
        mDrinks = new ArrayList<Drink>();
        mDrinkAdapter = new DrinkAdapter(getActivity(), R.layout.drinklist_row_layout, mDrinks);
        listView.setAdapter(mDrinkAdapter);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != DRINK_REQUEST_CODE || resultCode != Activity.RESULT_OK)
            return;

        mDrinks.add(new Drink(
                data.getExtras().getString(AddDrinkActivity.KEY_DRINK_NAME),
                data.getExtras().getInt(AddDrinkActivity.KEY_CAFFEINE_AMOUNT), new Date()));
        mDrinkAdapter.notifyDataSetChanged();

    }
}
