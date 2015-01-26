package edu.rosehulman.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.rosehulman.androidproject.R;

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_drink_button_coffee:
                mDrinkName = getString(R.string.coffee);
                mCaffeineAmount = 10;
                break;
            case R.id.add_drink_button_energy_drink:
                mDrinkName = getString(R.string.energy_drink);
                mCaffeineAmount = 15;
                break;
            case R.id.add_drink_button_espresso:
                mDrinkName = getString(R.string.espresso);
                mCaffeineAmount = 20;
                break;
            case R.id.add_drink_button_latte:
                mDrinkName = getString(R.string.latte);
                mCaffeineAmount = 12;
                break;
            case R.id.add_drink_button_custom:
                mDrinkName = ((EditText) findViewById(R.id.add_drink_edittext_name)).getText().toString();
                mCaffeineAmount = Integer.parseInt(((EditText) findViewById(R.id.add_drink_edittext_caffine_amount)).getText().toString());
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