package edu.rosehulman.androidproject;

import java.util.HashMap;
import java.util.List;

import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 2/18/2015.
 */
public class DataPassHolder {
    private static HashMap<String, Object> data;
    static {
        data = new HashMap<String, Object>();
    }

    public static void setData(String key, Object object) {
        data.put(key, object);
    }

    public static Object getData(String key) {
        return data.get(key);
    }
}
