package edu.rosehulman.androidproject.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final int CALCULATE_INTERVAL = 10000; //Seconds
    private static final int REMOVE_OLD_DRINK_INTERVAL =1; //hours
    private DrinkAdapter mDrinkAdapter;
    private ViewGroup rootView;

    private static HomeFragment instance;

    int i = 0;

    public static HomeFragment getInstance() {
        if (instance == null)
            instance = new HomeFragment();
        return instance;
    }


    private Handler mHandler = new Handler();
    ImageView img;

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showRemoveDialog(position);
                return false;
            }
        });

        img = ((ImageView) rootView.findViewById(R.id.profile_picture));

        return rootView;
    }

    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void startUpdating() {
        mHandler.postDelayed(updateTask, 1);
        mHandler.postDelayed(updateTaskSlow, 1);
    }

    public Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private Runnable updateTask = new Runnable() {
        public void run() {

            if(img != null)
                img.setImageBitmap(decodeBase64(MainActivity.USER.getmPictureBase64()));

            double caffeineLevel = MainActivity.USER.getCaffeineLevel();
            updateCaffeineLevelTextView(caffeineLevel);
                if (caffeineLevel > ((MainActivity) getActivity()).getHighestCaffeineLevel()) {
                    ((MainActivity) getActivity()).setHighestCaffeineLevel(caffeineLevel);
                }
            if (caffeineLevel > 0) {
                //MainActivity.USER.addPoint(new Date(), caffeineLevel);
                updateList();
            }
            //GraphFragment.getInstance().updateGraph();

            mHandler.postDelayed(updateTask, CALCULATE_INTERVAL);
        }
    };

    private Runnable updateTaskSlow = new Runnable() {

        @Override
        public void run() {
            boolean somethingToClear = MainActivity.USER.clearOldDrinks();
            if (somethingToClear) {
                updateDataBase(MainActivity.USER.getDrinkHistory());
            }
            mHandler.postDelayed(updateTaskSlow, 3600*REMOVE_OLD_DRINK_INTERVAL);
        }
    };

    private void updateCaffeineLevelTextView(double caffeineLevel) {
        ((TextView) rootView.findViewById(R.id.fragment_home_textview_caffeine_level)).setText("CAFFEINE LEVEL: " + String.format("%.2f", caffeineLevel) + "‰");//setText(getString(R.string.caffeine_level, caffeineLevel));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != DRINK_REQUEST_CODE || resultCode != Activity.RESULT_OK)
            return;

        Drink d = new Drink(new DrinkType(
                data.getExtras().getString(AddDrinkActivity.KEY_DRINK_NAME),
                data.getExtras().getInt(AddDrinkActivity.KEY_CAFFEINE_AMOUNT)), new Date());
        MainActivity.USER.drink(d);


        updateDataBase(MainActivity.USER.getDrinkHistory());
        UserListFragment.getInstance().updateList();
        //GraphFragment.getInstance().updateGraph();

        updateList();
    }

    private void updateDataBase(ArrayList<Drink> drinkList) {
        Collections.sort(MainActivity.USER.getDrinkHistory());
        if (MainActivity.USER.getDrinkHistory().size() > 0) {
            ((MainActivity) getActivity()).getFirebaseReference().child(getString(R.string.KEY_USERS) + "/" + MainActivity.USER.getEmail() + "/" + getString(R.string.KEY_DRINK_HISTORY)).setValue(drinkList);
        } else {
            ((MainActivity) getActivity()).getFirebaseReference().child(getString(R.string.KEY_USERS) + "/" + MainActivity.USER.getEmail() + "/" + getString(R.string.KEY_DRINK_HISTORY)).setValue("");
        }
    }

    public void updateList() {
        mDrinkAdapter.notifyDataSetChanged();
    }

    public void showRemoveDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.remove_item);
        builder.setMessage(R.string.skip_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.USER.getDrinkHistory().remove(position);
                updateDataBase(MainActivity.USER.getDrinkHistory());
                updateList();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
