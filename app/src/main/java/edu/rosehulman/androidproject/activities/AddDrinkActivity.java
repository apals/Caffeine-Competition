package edu.rosehulman.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.adapters.CommonDrinkAdapter;
import edu.rosehulman.androidproject.models.CommonDrink;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * Created by palssoa on 1/12/2015.
 */
public class AddDrinkActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String KEY_DRINK_NAME = "KEY_DRINK_NAME";
    public static final String KEY_CAFFEINE_AMOUNT = "KEY_CAFFEINE_AMOUNT";
    private ListView mListView;
    private CommonDrinkAdapter mAdapter;
    private ArrayList<CommonDrink> mCommonDrinkTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up the action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setContentView(R.layout.activity_add_drink);

        mCommonDrinkTypes = new ArrayList<CommonDrink>();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Map<String, String> drinks = (Map<String, String>) prefs.getAll();

        for (Map.Entry<String, String> entry : drinks.entrySet()) {
            String drinkName = entry.getKey();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                mCommonDrinkTypes.add(new CommonDrink(new DrinkType(drinkName, jsonObject.getInt("caffeineAmount")), jsonObject.getInt("timesConsumed")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Collections.sort(mCommonDrinkTypes);

        findViewById(R.id.add_drink_button_custom).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.drink_list);
        mAdapter = new CommonDrinkAdapter(this, R.layout.common_drink_list_row_layout, mCommonDrinkTypes);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

        String drinkName = ((EditText) findViewById(R.id.add_drink_edittext_name)).getText().toString();
        Integer caffeineAmount = Integer.parseInt(((EditText) findViewById(R.id.add_drink_edittext_caffine_amount)).getText().toString());
        int oldTimesConsumed = 0;

        if (getPreferences(MODE_PRIVATE).contains(drinkName)) {
            try {
                oldTimesConsumed = new JSONObject(getPreferences(MODE_PRIVATE).getString(drinkName, null)).getInt("timesConsumed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        JSONObject json = null;
        try {
            json = new JSONObject("{\"caffeineAmount\":\"" + caffeineAmount + "\",\"timesConsumed\":\"" + oldTimesConsumed+1 + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString(drinkName, json.toString());
        editor.apply();
        editor.commit();

        Intent i = new Intent();
        i.putExtra(KEY_DRINK_NAME, drinkName);
        i.putExtra(KEY_CAFFEINE_AMOUNT, caffeineAmount);
        setResult(RESULT_OK, i);
        finish();
    }
}