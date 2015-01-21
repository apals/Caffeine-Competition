package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.androidproject.R;

public class HomeContainerFragment extends Fragment {

    private static HomeContainerFragment instance;
    public static HomeContainerFragment getInstance() {
        if(instance == null) {
            instance = new HomeContainerFragment();

        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        System.out.println("Creating new home container fragment");

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(rootView.getId(), homeFragment);

        BottomFragment bottomFragment = new BottomFragment();
        transaction.add(rootView.getId(), bottomFragment).commit();

        return rootView;
    }
}