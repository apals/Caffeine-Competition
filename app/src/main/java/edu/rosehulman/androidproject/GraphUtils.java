package edu.rosehulman.androidproject;

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

    public static XYMultipleSeriesRenderer getRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);
        renderer.setXLabelsColor(Color.YELLOW);
        renderer.setShowGrid(true);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setChartTitle("Closure Profile");
        renderer.setXTitle("Date");
        renderer.setYTitle("Closure");
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.WHITE);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    public static XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        TimeSeries series = new TimeSeries("Demo");
        series.add(1, 1);
        dataset.addSeries(series);
        return dataset;
    }
}
