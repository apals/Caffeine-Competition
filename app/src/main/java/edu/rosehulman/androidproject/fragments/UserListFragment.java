package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Date;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;
import edu.rosehulman.androidproject.adapters.UserListAdapter;
import edu.rosehulman.androidproject.models.User;

/**
 * Created by palssoa on 12/20/2014.
 */
public class UserListFragment extends Fragment {

    private static final long CALCULATE_INTERVAL = 10000;
    private static UserListFragment instance;
    private UserListAdapter listAdapter;
    public double highestCaffeineLevel = 0;
    private View rootView;
    private ExpandableListView mExpandableListView;


    public static UserListFragment getInstance() {
        if (instance == null)
            instance = new UserListFragment();
        return instance;
    }

    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_userlist, container, false);
        mExpandableListView = (ExpandableListView) rootView.findViewById(R.id.list);
        listAdapter = new UserListAdapter(getActivity(), ((MainActivity) getActivity()).getUsers());
        mExpandableListView.setAdapter(listAdapter);

        mHandler.postDelayed(updateTask, CALCULATE_INTERVAL);
        return rootView;
    }

    public void stopUpdating() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void startUpdating() {
        mHandler.postDelayed(updateTask, 1);
    }

    private Runnable updateTask = new Runnable() {
        public void run() {
            ArrayList<User> userList = ((MainActivity) getActivity()).getUsers();
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
