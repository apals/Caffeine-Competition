package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import edu.rosehulman.androidproject.ExpandAnimation;
import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;

/**
 * A simple implementation of list adapter.
 */
public class UserListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private GraphicalView mLineChart;


    public UserListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.userlist_row_layout, null);
            final View a = convertView;

            //the following code should probably be in onitemclick up there but the collapse of the item didn't work
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout toolbar = (LinearLayout) a.findViewById(R.id.toolbar);

                    mLineChart = ChartFactory.getLineChartView(context, GraphUtils.getDataset(), GraphUtils.getRenderer());
                    toolbar.addView(mLineChart);
                    // Creating the expand animation for the item
                    ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                    // Start the animation on the toolbar
                    toolbar.startAnimation(expandAni);
                }
            });
        }

        ((TextView) convertView.findViewById(R.id.username)).setText(getItem(position));

        // Resets the toolbar to be closed
        View toolbar = convertView.findViewById(R.id.toolbar);
        ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
        toolbar.setVisibility(View.GONE);

        return convertView;
    }
}