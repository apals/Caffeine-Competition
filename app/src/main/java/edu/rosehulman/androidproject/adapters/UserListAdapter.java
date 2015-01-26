package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import edu.rosehulman.androidproject.ExpandAnimation;
import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.User;

/**
 * A simple implementation of list adapter.
 */
public class UserListAdapter extends ArrayAdapter<User> {

    private Activity context;
    private GraphicalView mLineChart;



    public UserListAdapter(Context context, int textViewResourceId, User[] objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.userlist_row_layout, null);
            final View a = convertView;

            //TODO: the following code should probably be in onitemclick up there but the collapse of the item didn't work
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout toolbar = (LinearLayout) a.findViewById(R.id.toolbar);

                    mLineChart = ChartFactory.getLineChartView(context, GraphUtils.getDataset(), GraphUtils.getMultipleSeriesRenderer(context));
                    toolbar.addView(mLineChart);
                    // Creating the expand animation for the item
                    ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                    // Start the animation on the toolbar
                    toolbar.startAnimation(expandAni);
                }
            });
        }

        ((TextView) convertView.findViewById(R.id.username)).setText(getItem(position).getUsername());
        ((TextView) convertView.findViewById(R.id.caffeinelevel)).setText(getItem(position).getCaffeineLevel() + " mg");
        ((TextView) convertView.findViewById(R.id.subtext_date)).setText(getItem(position).getDrinkHistory().get(getItem(position).getDrinkHistory().size() - 1).getFormattedDate());


        // Resets the toolbar to be closed
        View toolbar = convertView.findViewById(R.id.toolbar);
        ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -100;
        toolbar.setVisibility(View.GONE);

        return convertView;
    }
}