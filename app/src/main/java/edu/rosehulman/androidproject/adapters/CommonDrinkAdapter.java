package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.AddDrinkActivity;
import edu.rosehulman.androidproject.models.CommonDrink;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;

/**
 * A simple implementation of list adapter.
 */
public class CommonDrinkAdapter extends ArrayAdapter<CommonDrink> {

    private Activity context;
    private ArrayList<CommonDrink> objects;

    public CommonDrinkAdapter(Context context, int textViewResourceId, ArrayList<CommonDrink> objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = context.getLayoutInflater().inflate(R.layout.common_drink_list_row_layout, null);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showRemoveDialog(position);
                return true;
            }
        });

        ((TextView) convertView.findViewById(R.id.common_drink_list_drink_name)).setText(getItem(position).getDrinkType().getDrinkName());
        ((TextView) convertView.findViewById(R.id.common_drink_list_caffeine_amount)).setText(getItem(position).getDrinkType().getCaffeineAmount() + " mg");


        return convertView;
    }

    public void showRemoveDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.remove_item);
        builder.setMessage(R.string.skip_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = context.getPreferences(Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.remove(objects.get(position).getDrinkType().getDrinkName());
                edit.commit();
                edit.apply();
                objects.remove(position);
                notifyDataSetChanged();
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