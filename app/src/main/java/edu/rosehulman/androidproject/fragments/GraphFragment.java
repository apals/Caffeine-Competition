package edu.rosehulman.androidproject.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class GraphFragment extends Fragment {

    private static final long UPDATE_GRAPH_INTERVAL = 10000; //ms
    private static final long UPDATE_GRAPH_SLOW_INTERVAL = 20000; //ms
    public static final double MAX_Y = 0.35;
    private GraphicalView mLineChart;
    private XYMultipleSeriesRenderer renderer;
    private XYMultipleSeriesDataset dataset;
    private View rootView;
    private ArrayList<CheckBox> checkBoxes;
    private int[] colors;

    private static GraphFragment instance;
    private Handler mHandler = new Handler();

    public static GraphFragment getInstance() {
        if (instance == null)
            instance = new GraphFragment();
        return instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        checkBoxes = new ArrayList<>();
        colors = activity.getResources().getIntArray(R.array.colors);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_graph, container, false);

        renderer = GraphUtils.getMultipleSeriesRenderer(getActivity());
        dataset = GraphUtils.getDataset();
        mLineChart = ChartFactory.getTimeChartView(getActivity(), dataset, renderer, "HH:mm"); //ChartFactory.getLineChartView(getActivity(), dataset, renderer);

        mLineChart.setBackgroundColor(Color.WHITE);

        ((LinearLayout) rootView.findViewById(R.id.chart)).addView(mLineChart);

        return rootView;
    }

    public void addUserCheckBox(User user) {
        CheckBox button = new CheckBox(getActivity());
        button.setChecked(true);
        button.setText(user.getUsername());
        final int buttonIndex = checkBoxes.size();

        button.setTextColor(colors[(buttonIndex * 7) % colors.length]);
        button.setHighlightColor(colors[(buttonIndex * 7) % colors.length]);

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    renderer.getSeriesRendererAt(buttonIndex).setColor(Color.TRANSPARENT);
                } else {
                    renderer.getSeriesRendererAt(buttonIndex).setColor(colors[(buttonIndex * 7) % colors.length]);
                }
                mLineChart.repaint();

            }
        });
        checkBoxes.add(button);
        ((LinearLayout) rootView.findViewById(R.id.linearlayout_scrollview)).addView(button);
    }

    private Runnable updateGraph = new Runnable() {
        public void run() {
            if (dataset == null || mLineChart == null)
                return;
            ArrayList<User> users = ((MainActivity) getActivity()).getUsers();
            TimeSeries[] serieses = new TimeSeries[users.size()];
            XYSeriesRenderer[] renderers = new XYSeriesRenderer[users.size()];
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                boolean exists = false;
                for (CheckBox box : checkBoxes) {
                    if (box.getText().toString().equals(user.getUsername())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    TimeSeries series = new TimeSeries(user.getUsername());

                    ArrayList<DateCaffeinePoint> points = user.getPoints();
                    for (int p = 0; p < points.size(); p++) {
                        DateCaffeinePoint point = points.get(p);
                        series.add(point.getDate(), point.getCaffeine());
                    }
                    serieses[user.getId()] = series;
                    XYSeriesRenderer r = GraphUtils.getSeriesRenderer(getActivity());
                    r.setColor(colors[(i * 7) % colors.length]);
                    renderers[user.getId()] = r;
                    addUserCheckBox(user);
                }
            }
            for(int j = 0; j < serieses.length; j++) {
                if(serieses[j] != null) {
                    System.out.println(serieses[j] + " " + renderers[j]);
                    dataset.addSeries(serieses[j]);
                    renderer.addSeriesRenderer(renderers[j]);
                }
            }

            for (int i = 0; i < users.size(); i++) {
                ArrayList<DateCaffeinePoint> points = users.get(i).getPoints();
                if (points.size() != 0) {
                    ((TimeSeries) dataset.getSeriesAt(users.get(i).getId())).add(points.get(points.size() - 1).getDate(), points.get(points.size() - 1).getCaffeine());
                }
            }
            mLineChart.repaint();
            mHandler.postDelayed(updateGraph, UPDATE_GRAPH_INTERVAL);
        }
    };

    public void exchangeSeries(User user) {
        TimeSeries ts = (TimeSeries) dataset.getSeriesAt(user.getId());
        ts.clear();
        for (int i = 0; i < user.getPoints().size(); i++) {
            DateCaffeinePoint point = user.getPoints().get(i);
            ts.add(point.getDate(), point.getCaffeine());
        }


    }

    private Runnable updateGraphSlow = new Runnable() {
        public void run() {
            double newYBounds = ((MainActivity) getActivity()).getHighestCaffeineLevel() + 0.05;
            if (newYBounds > MAX_Y) {
                newYBounds = MAX_Y;
            }
            renderer.setYAxisMax(newYBounds, 0);
            mHandler.postDelayed(updateGraphSlow, UPDATE_GRAPH_SLOW_INTERVAL);
        }
    };

    public void startUpdating() {
        mHandler.postDelayed(updateGraph, 1);
        mHandler.postDelayed(updateGraphSlow, 1);
    }

    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
