package edu.rosehulman.androidproject.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.AddDrinkActivity;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.adapters.DrinkAdapter;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * Created by palssoa on 12/20/2014.
 */
public class HomeFragment extends Fragment {

    public static final int DRINK_REQUEST_CODE = 0;
    private DrinkAdapter mDrinkAdapter;
    private ViewGroup rootView;


    private static HomeFragment instance;

    int i = 0;

    public static HomeFragment getInstance() {
        if(instance == null)
            instance = new HomeFragment();
        return instance;
    }


    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        rootView.findViewById(R.id.fragment_home_button_drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddDrinkActivity.class), DRINK_REQUEST_CODE);
            }
        });

        ((TextView) rootView.findViewById(R.id.fragment_home_textview_username)).setText(MainActivity.USER.getUsername());

        ListView listView = (ListView) rootView.findViewById(R.id.fragment_home_drink_list);
        mDrinkAdapter = new DrinkAdapter(getActivity(), R.layout.drinklist_row_layout, MainActivity.USER.getDrinkHistory());
        listView.setAdapter(mDrinkAdapter);

        mHandler.postDelayed(updateTask, 1000);


        return rootView;
    }

    private Runnable updateTask = new Runnable () {
        public void run() {
            updateCaffeineLevelTextView(MainActivity.USER.getCaffeineLevel());
            mHandler.postDelayed(updateTask, 1000);
        }
    };

    private void updateCaffeineLevelTextView(int caffeineLevel) {
        ((TextView) rootView.findViewById(R.id.fragment_home_textview_caffeine_level)).setText(getString(R.string.caffeine_level, caffeineLevel));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != DRINK_REQUEST_CODE || resultCode != Activity.RESULT_OK)
            return;

        Drink d = new Drink(new DrinkType(
                data.getExtras().getString(AddDrinkActivity.KEY_DRINK_NAME),
                data.getExtras().getInt(AddDrinkActivity.KEY_CAFFEINE_AMOUNT)), new Date());

        System.out.println("ONACTIVITY RESULT HOMEFRAGMENT");
        MainActivity.USER.drink(d);
        ((MainActivity) getActivity()).getFirebaseReference().child("users" + "/" + MainActivity.USER.getUsername() + "/drinkHistory").setValue(MainActivity.USER.getDrinkHistory());


        UserListFragment.getInstance().updateList();
        GraphFragment.getInstance().updateGraph();
        //((MainActivity) getActivity()).getFirebaseReference().child("users" + "/" + MainActivity.USER.getUsername() + "/drinkHistory").setValue(MainActivity.USER.getDrinkHistory());
        mDrinkAdapter.notifyDataSetChanged();
    }

    public void updateList() {
        System.out.println("UPDATE LIST HOMEFRAGMENT");
        mDrinkAdapter.notifyDataSetChanged();
    }
}
