package edu.rosehulman.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * Created by palssoa on 1/12/2015.
 */
public class AddDrinkActivity extends Activity implements View.OnClickListener {

    private String mDrinkName;
    private int mCaffeineAmount;
    public static final String KEY_DRINK_NAME = "KEY_DRINK_NAME";
    public static final String KEY_CAFFEINE_AMOUNT = "KEY_CAFFEINE_AMOUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        findViewById(R.id.add_drink_button_coffee).setOnClickListener(this);
        findViewById(R.id.add_drink_button_energy_drink).setOnClickListener(this);
        findViewById(R.id.add_drink_button_espresso).setOnClickListener(this);
        findViewById(R.id.add_drink_button_latte).setOnClickListener(this);
        findViewById(R.id.add_drink_button_custom).setOnClickListener(this);


        /*
        dynamically create buttons instead of using add_drink_button_coffee etc
        ArrayList<DrinkType> drinkTypes = new ArrayList<DrinkType>();
        for(DrinkType d : drinkTypes) {
            Button b = new Button(this);
            b.setText(d.getmDrinkName());
            b.setOnClickListener(this);
            //add b to parent view
        }*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_drink_button_coffee:
                mDrinkName = getString(R.string.coffee);
                mCaffeineAmount = getResources().getInteger(R.integer.caffeine_amount_coffee);
                break;
            case R.id.add_drink_button_energy_drink:
                mDrinkName = getString(R.string.energy_drink);
                mCaffeineAmount = getResources().getInteger(R.integer.caffeine_amount_energy_drink);
                break;
            case R.id.add_drink_button_espresso:
                mDrinkName = getString(R.string.espresso);
                mCaffeineAmount = getResources().getInteger(R.integer.caffeine_amount_espresso);
                break;
            case R.id.add_drink_button_latte:
                mDrinkName = getString(R.string.latte);
                mCaffeineAmount = getResources().getInteger(R.integer.caffeine_amount_latte);
                break;
            case R.id.add_drink_button_custom:
                mDrinkName = ((EditText) findViewById(R.id.add_drink_edittext_name)).getText().toString();
                try {
                    mCaffeineAmount = Integer.parseInt(((EditText) findViewById(R.id.add_drink_edittext_caffine_amount)).getText().toString());
                } catch (NumberFormatException e) {
                    mCaffeineAmount = -1;
                }
              if(mDrinkName == null || mDrinkName.equals("") || mCaffeineAmount < 0)
                    return;
        }

        Intent i = new Intent();
        i.putExtra(KEY_DRINK_NAME, mDrinkName);
        i.putExtra(KEY_CAFFEINE_AMOUNT, mCaffeineAmount);
        setResult(RESULT_OK, i);
        finish();
    }
}