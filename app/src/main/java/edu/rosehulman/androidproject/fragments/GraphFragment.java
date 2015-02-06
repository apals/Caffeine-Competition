package edu.rosehulman.androidproject.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.Random;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class GraphFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private GraphicalView mLineChart;
    private XYMultipleSeriesRenderer renderer;
    private XYMultipleSeriesDataset dataset;


    private static GraphFragment instance;

    public static GraphFragment getInstance() {
        if (instance == null)
            instance = new GraphFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_graph, container, false);

        renderer = GraphUtils.getMultipleSeriesRenderer(getActivity());
        dataset = GraphUtils.getDataset(((MainActivity) getActivity()).getUsers());
        mLineChart = ChartFactory.getLineChartView(getActivity(), dataset, renderer);
        mLineChart.setBackgroundColor(Color.WHITE);

        ((LinearLayout) rootView.findViewById(R.id.chart)).addView(mLineChart);

        for (int i = 0; i < 6; i++) {
            CheckBox button = new CheckBox(getActivity());
            button.setText("Button #" + i);
            button.setOnCheckedChangeListener(this);
            ((LinearLayout) rootView.findViewById(R.id.linearlayout_scrollview)).addView(button);
        }

        return rootView;
    }

    public void updateGraph() {
        if (dataset == null || mLineChart == null)
            return;
        ArrayList<User> users = ((MainActivity) getActivity()).getUsers();
        if(users == null || users.size() == 0)
            return;



        for(int i = 0; i < users.size(); i++) {
            ArrayList<DateCaffeinePoint> points = users.get(i).getPoints();
            System.out.println("TRYING TO UPDATE GRAPH: POINTS. SIZE == " + points.size());
            if(points.size() != 0) {
                if(i >= dataset.getSeriesCount()) {
                    TimeSeries series = new TimeSeries(users.get(i).getUsername());
                    dataset.addSeries(series);
                }
                System.out.println("ADDING A POINT TO SERIES, ADDING THIS POINT       X: " + (points.get(points.size() - 1).getDate() + ", Y: " + points.get(points.size() - 1).getCaffeine()));
                ((TimeSeries) dataset.getSeriesAt(i)).add(points.get(points.size() - 1).getDate(), points.get(points.size() - 1).getCaffeine());
            }
        }
        mLineChart.repaint();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked)
            renderer.getSeriesRendererAt(0).setColor(Color.TRANSPARENT);
        else
            renderer.getSeriesRendererAt(0).setColor(getResources().getColor(R.color.blue));
        mLineChart.repaint();
    }
}
