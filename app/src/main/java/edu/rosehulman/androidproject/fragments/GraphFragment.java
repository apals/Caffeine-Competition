package edu.rosehulman.androidproject.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import edu.rosehulman.androidproject.GraphUtils;
import edu.rosehulman.androidproject.R;

/**
 * Created by palssoa on 12/20/2014.
 */
public class GraphFragment extends Fragment {

    private GraphicalView mLineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_graph, container, false);


        mLineChart = ChartFactory.getLineChartView(getActivity(), GraphUtils.getDataset(), GraphUtils.getRenderer());
        rootView.addView(mLineChart);


        return rootView;
    }


}
