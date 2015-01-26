package edu.rosehulman.androidproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by palssoa on 1/15/2015.
 */
public class GraphUtils {

    public static XYMultipleSeriesRenderer getMultipleSeriesRenderer(Context context) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setMarginsColor(Color.WHITE);


        renderer.setXLabelsColor(context.getResources().getColor(R.color.blue));
        renderer.setShowGrid(false);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setChartTitle("");

        renderer.setAxesColor(Color.BLACK);
        renderer.setXTitle("Time");
        renderer.setYTitle("Caffeine");


        XYSeriesRenderer r = getSeriesRenderer(context);
        renderer.addSeriesRenderer(r);

        XYSeriesRenderer s = getSeriesRenderer(context);
        renderer.addSeriesRenderer(s);

        renderer.setXAxisMax(24);
        renderer.setYAxisMax(10);
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);

        return renderer;
    }

    public static XYSeriesRenderer getSeriesRenderer(Context context) {
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(context.getResources().getColor(R.color.blue));

        return r;


    }

    public static XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        TimeSeries series = new TimeSeries("User #1");

        for(int i = 0; i < 10; i++) {
            series.add(i, i);
        }

        TimeSeries series2 = new TimeSeries("User #2");

        for(int i = 10; i > 0; i--) {
            series2.add(10-i, i);
        }


        dataset.addSeries(series);
        dataset.addSeries(series2);
        return dataset;
    }
}
