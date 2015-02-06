package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * A simple implementation of list adapter.
 */
public class CommonDrinkAdapter extends ArrayAdapter<DrinkType> {

    private Activity context;

    public CommonDrinkAdapter(Context context, int textViewResourceId, ArrayList<DrinkType> objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = context.getLayoutInflater().inflate(R.layout.drinklist_row_layout, null);

        ((TextView) convertView.findViewById(R.id.common_drink_list_drink_name)).setText(getItem(position).getDrinkName());
        ((TextView) convertView.findViewById(R.id.common_drink_list_caffeine_amount)).setText(getItem(position).getCaffeineAmount() + " mg");
        convertView.findViewById(R.id.common_drink_list_caffeine_amount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        return convertView;
    }

}