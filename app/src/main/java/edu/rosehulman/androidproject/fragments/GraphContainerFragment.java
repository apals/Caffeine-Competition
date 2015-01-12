package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.ScreenSlidePagerActivity;
import edu.rosehulman.androidproject.fragments.BottomFragment;
import edu.rosehulman.androidproject.fragments.GraphFragment;

public class GraphContainerFragment extends Fragment {

    private static GraphContainerFragment instance;
    public static GraphContainerFragment getInstance() {
        if(instance == null) {
            instance = new GraphContainerFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        System.out.println("Creating new graph container fragment");
        Fragment graphFragment = new GraphFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(rootView.getId(), graphFragment);

        BottomFragment bottomFragment = new BottomFragment();
        bottomFragment.setPage(ScreenSlidePagerActivity.GRAPH_ID);
        transaction.add(rootView.getId(), bottomFragment).commit();

        return rootView;
    }
}