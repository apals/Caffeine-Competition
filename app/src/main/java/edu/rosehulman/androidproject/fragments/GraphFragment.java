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
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.Random;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;

/**
 * Created by palssoa on 12/20/2014.
 */
public class GraphFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private GraphicalView mLineChart;
    private XYMultipleSeriesRenderer renderer;
    private XYMultipleSeriesDataset dataset;


    private static GraphFragment instance;
    public static GraphFragment getInstance() {
        if(instance == null)
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

        for(int i = 0; i < 6; i++) {
            CheckBox button = new CheckBox(getActivity());
            button.setText("Button #" + i);
            button.setOnCheckedChangeListener(this);
            ((LinearLayout) rootView.findViewById(R.id.linearlayout_scrollview)).addView(button);
        }

        return rootView;
    }

    public void updateGraph() {
        dataset.getSeriesAt(0).add(new Random().nextInt(10), 3);
        mLineChart.repaint();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked)
            renderer.getSeriesRendererAt(0).setColor(Color.TRANSPARENT);
        else
            renderer.getSeriesRendererAt(0).setColor(getResources().getColor(R.color.blue));
        mLineChart.repaint();
    }
}
