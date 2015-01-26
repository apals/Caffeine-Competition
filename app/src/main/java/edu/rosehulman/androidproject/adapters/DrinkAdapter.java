package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.Drink;

/**
 * A simple implementation of list adapter.
 */
public class DrinkAdapter extends ArrayAdapter<Drink> {

    private Activity context;
    private ArrayList<Drink> mDrinks;


    public DrinkAdapter(Context context, int textViewResourceId, ArrayList<Drink> objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
        this.mDrinks = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = context.getLayoutInflater().inflate(R.layout.drinklist_row_layout, null);

        ((TextView) convertView.findViewById(R.id.date)).setText(getItem(position).getFormattedDate());
        ((TextView) convertView.findViewById(R.id.drink_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.caffeine_amount)).setText(getItem(position).getCaffeineAmount() + " mg");

        return convertView;
    }

}