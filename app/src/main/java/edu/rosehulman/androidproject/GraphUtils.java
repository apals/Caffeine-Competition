package edu.rosehulman.androidproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.models.DateCaffeinePoint;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.User;

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
        renderer.setYLabelsColor(0, context.getResources().getColor(R.color.blue));

        renderer.setShowGrid(false);
        renderer.setYLabelsAlign(Paint.Align.LEFT);
        renderer.setChartTitle("");

        renderer.setAxesColor(Color.BLACK);
        renderer.setLabelsColor(Color.BLACK);

        renderer.setXTitle("Time");
        renderer.setYTitle("Caffeine");

        renderer.setYAxisMin(0);

        /*for(User u : ((MainActivity) context).getUsers()) {
            XYSeriesRenderer s = getSeriesRenderer(context);
            renderer.addSeriesRenderer(s);
        }*/

        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);

        return renderer;
    }

    public static XYSeriesRenderer getSeriesRenderer(Context context) {
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(context.getResources().getColor(R.color.blue));
        return r;
    }

    public static XYMultipleSeriesDataset getDataset(List<User> users) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        /*for(User user : users) {
            TimeSeries series = new TimeSeries(user.getUsername());
            for(DateCaffeinePoint point : user.getPoints()) {
                series.add(point.getDate().getTime(), point.getCaffeine());
            }
            dataset.addSeries(series);

        }*/
        return dataset;
    }
}
