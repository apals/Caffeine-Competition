package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.androidproject.R;

public class ListContainerFragment extends Fragment {

    private static ListContainerFragment instance;
    public static ListContainerFragment getInstance() {
        if(instance == null)
            instance = new ListContainerFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        ListFragment userListFragment = new UserListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(rootView.getId(), userListFragment);

        BottomFragment bottomFragment = new BottomFragment();
        transaction.add(rootView.getId(), bottomFragment).commit();

        return rootView;
    }
}