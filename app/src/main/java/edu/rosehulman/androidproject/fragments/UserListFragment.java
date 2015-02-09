package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.adapters.UserListAdapter;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class UserListFragment extends ListFragment {

    private static final long CALCULATE_INTERVAL = 1000;
    private static UserListFragment instance;
    private UserListAdapter listAdapter;
    public double highestCaffeineLevel = 0;

    public static UserListFragment getInstance() {
        if (instance == null)
            instance = new UserListFragment();
        return instance;
    }

    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Creating an array adapter to store the list of countries **/
        listAdapter = new UserListAdapter(getActivity(), R.layout.userlist_row_layout, ((MainActivity) getActivity()).getUsers());
        /** Setting the list adapter for the ListFragment */
        setListAdapter(listAdapter);

        mHandler.postDelayed(updateTask, CALCULATE_INTERVAL);
        return inflater.inflate(R.layout.fragment_userlist, container, false);
    }

    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void startUpdating() {
        mHandler.postDelayed(updateTask, CALCULATE_INTERVAL);
    }

    private Runnable updateTask = new Runnable() {
        public void run() {
            ArrayList<User> userList = ((MainActivity) getActivity()).getUsers();
            ((MainActivity) getActivity()).setHighestCaffeineLevel(0);
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                double caffeineLevel = user.getCaffeineLevel();
                if (caffeineLevel > ((MainActivity) getActivity()).getHighestCaffeineLevel()) {
                    ((MainActivity) getActivity()).setHighestCaffeineLevel(caffeineLevel);
                }
                if (caffeineLevel > 0) {
                    user.addPoint(new Date(), caffeineLevel);
                }
            }
            updateList();
            //GraphFragment.getInstance().updateGraph();
            mHandler.postDelayed(updateTask, CALCULATE_INTERVAL);
        }
    };

    public void updateList() {
        if(listAdapter != null)
            listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
