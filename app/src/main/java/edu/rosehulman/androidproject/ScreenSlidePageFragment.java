package edu.rosehulman.androidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        ListFragment videoFragment = new TopFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(rootView.getId(), videoFragment);

        Fragment videoFragment2 = new BottomFragment();
        transaction.add(rootView.getId(), videoFragment2).commit();

        return rootView;
    }
}