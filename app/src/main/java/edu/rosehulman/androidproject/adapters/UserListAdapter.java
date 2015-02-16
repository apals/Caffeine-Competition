package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.List;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.User;

/**
 * A simple implementation of list adapter.
 */
public class UserListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private GraphicalView mLineChart;
    private List<User> users;



    public UserListAdapter(Context context, List<User> objects) {
        this.context = (Activity) context;
        this.users = objects;
    }



    @Override
    public int getGroupCount() {
        return users.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return users.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.userlist_row_layout, null);
            final View a = convertView;
        }

        ((TextView) convertView.findViewById(R.id.username)).setText(((User) getGroup(groupPosition)).getUsername());
        ((TextView) convertView.findViewById(R.id.caffeinelevel)).setText(String.format("%.2f", ((User) getGroup(groupPosition)).getCaffeineLevel()) + "‰");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(R.layout.userlist_row_layout_child, null);

        XYMultipleSeriesDataset dataset = GraphUtils.getDataset((((MainActivity) context).getUsers()));
        TimeSeries series = new TimeSeries("lol");
        for(DateCaffeinePoint d : ((User) getGroup(groupPosition)).getPoints()) {
            series.add(d.getDate(), d.getCaffeine());
        }
        dataset.addSeries(series);

        XYMultipleSeriesRenderer renderer = GraphUtils.getMultipleSeriesRenderer(context);
        renderer.addSeriesRenderer(GraphUtils.getSeriesRenderer(context));
        mLineChart = ChartFactory.getTimeChartView(context, dataset, renderer, "HH:mm");
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.list_container);
        linearLayout.addView(mLineChart);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}