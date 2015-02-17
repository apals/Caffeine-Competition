package edu.rosehulman.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
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
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.Drink;
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
        ImageView img =((ImageView) convertView.findViewById(R.id.userlist_profile_pic));
        img.setImageBitmap(decodeBase64(((User) getGroup(groupPosition)).getmPictureBase64()));
        ((TextView) convertView.findViewById(R.id.username)).setText(((User) getGroup(groupPosition)).getUsername());
        ((TextView) convertView.findViewById(R.id.caffeinelevel)).setText(String.format("%.2f", ((User) getGroup(groupPosition)).getCaffeineLevel()) + "‰");
        List<Drink> drinkHistory = ((User) getGroup(groupPosition)).getDrinkHistory();
        if(drinkHistory.size() > 0)
            ((TextView) convertView.findViewById(R.id.subtext_last_drink)).setText(context.getString(R.string.last_drink) + drinkHistory.get(drinkHistory.size() - 1).getDrinkType().getDrinkName());
        return convertView;
    }

    public Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(R.layout.userlist_row_layout_child, null);

        XYMultipleSeriesDataset dataset = GraphUtils.getDataset();
        TimeSeries series = new TimeSeries("");
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