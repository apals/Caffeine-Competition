package edu.rosehulman.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.adapters.CommonDrinkAdapter;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * Created by palssoa on 1/12/2015.
 */
public class AddDrinkActivity extends ActionBarActivity implements View.OnClickListener {

    private String mDrinkName;
    private int mCaffeineAmount;
    public static final String KEY_DRINK_NAME = "KEY_DRINK_NAME";
    public static final String KEY_CAFFEINE_AMOUNT = "KEY_CAFFEINE_AMOUNT";
    private ListView mListView;
    private CommonDrinkAdapter mAdapter;
    private ArrayList<DrinkType> mCommonDrinkTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up the action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setContentView(R.layout.activity_add_drink);
        findViewById(R.id.add_drink_button_custom).setOnClickListener(this);
        mCommonDrinkTypes = new ArrayList<DrinkType>();
        mCommonDrinkTypes.add(new DrinkType("RED BULL", 10));
        mListView = (ListView) findViewById(R.id.drink_list);
        mAdapter = new CommonDrinkAdapter(this, R.layout.common_drink_list_row_layout, mCommonDrinkTypes);


    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        i.putExtra(KEY_DRINK_NAME, mDrinkName);
        i.putExtra(KEY_CAFFEINE_AMOUNT, mCaffeineAmount);
        setResult(RESULT_OK, i);
        finish();
    }
}