package edu.rosehulman.androidproject.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class GraphFragment extends Fragment {

    private GraphicalView mLineChart;
    private XYMultipleSeriesRenderer renderer;
    private XYMultipleSeriesDataset dataset;
    private View rootView;
    private ArrayList<CheckBox> checkBoxes;


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
        checkBoxes = new ArrayList<CheckBox>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_graph, container, false);

        renderer = GraphUtils.getMultipleSeriesRenderer(getActivity());
        dataset = GraphUtils.getDataset(((MainActivity) getActivity()).getUsers());
        mLineChart = ChartFactory.getTimeChartView(getActivity(), dataset, renderer, "HH-mm-ss"); //ChartFactory.getLineChartView(getActivity(), dataset, renderer);

        mLineChart.setBackgroundColor(Color.WHITE);

        ((LinearLayout) rootView.findViewById(R.id.chart)).addView(mLineChart);
        return rootView;
    }

    public void addUserCheckBox(User user) {

        CheckBox button = new CheckBox(getActivity());
        button.setChecked(true);
        button.setText(user.getUsername());
        button.setTextColor(getResources().getColor(R.color.blue));
        button.setHighlightColor(getResources().getColor(R.color.blue));
        final int buttonIndex = checkBoxes.size();
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    renderer.getSeriesRendererAt(buttonIndex).setColor(Color.TRANSPARENT);
                    //XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);
                    //fill.setColor(Color.TRANSPARENT);
                    //((XYSeriesRenderer) renderer.getSeriesRendererAt(buttonIndex)).addFillOutsideLine(fill);

                } else {
                    renderer.getSeriesRendererAt(buttonIndex).setColor(getResources().getColor(R.color.blue));
                    //XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BELOW);
                    //fill.setColor(getActivity().getResources().getColor(R.color.transparent_blue));
                    //((XYSeriesRenderer) renderer.getSeriesRendererAt(buttonIndex)).addFillOutsideLine(fill);
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


            for (User user : users) {
                boolean finns = false;
                for (CheckBox box : checkBoxes) {
                    if (box.getText().toString().equals(user.getUsername())) {
                        finns = true;
                        break;
                    }
                }
                if (!finns) {
                    TimeSeries series = new TimeSeries(user.getUsername());
                    dataset.addSeries(series);
                    XYSeriesRenderer r = GraphUtils.getSeriesRenderer(getActivity());
                    renderer.addSeriesRenderer(r);
                    addUserCheckBox(user);
                }
            }

            for (int i = 0; i < users.size(); i++) {
                ArrayList<DateCaffeinePoint> points = users.get(i).getPoints();
                if (points.size() != 0) {
                    ((TimeSeries) dataset.getSeriesAt(i)).add(points.get(points.size() - 1).getDate(), points.get(points.size() - 1).getCaffeine());
                }
            }
            mLineChart.repaint();
            mHandler.postDelayed(updateGraph, 100);
        }
    };

    public void startUpdating() {
        mHandler.postDelayed(updateGraph, 100);
    }


    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }

}
