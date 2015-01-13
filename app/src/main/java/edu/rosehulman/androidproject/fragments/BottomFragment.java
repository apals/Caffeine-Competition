package edu.rosehulman.androidproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.activities.MainActivity;


/**
 * Created by palssoa on 12/20/2014.
 */
public class BottomFragment extends Fragment {

    private static BottomFragment instance;
    private int previousPage;
    private int nextPage;


    public static BottomFragment getInstance() {
        if(instance == null)
            instance = new BottomFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_bottom, container, false);
        if(getParentFragment() instanceof GraphContainerFragment) {
            setPage(MainActivity.GRAPH_ID);
        } else if(getParentFragment() instanceof HomeContainerFragment) {
            setPage(MainActivity.HOME_ID);
        } else if(getParentFragment() instanceof ListContainerFragment) {
            setPage(MainActivity.LIST_ID);
        }


        ((Button) rootView.findViewById(R.id.previous_page_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getPager().setCurrentItem(previousPage);
            }
        });

        ((Button) rootView.findViewById(R.id.next_page_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getPager().setCurrentItem(nextPage);
            }
        });


        return rootView;
    }


    public void setPage(int page) {
        switch(page) {
            case MainActivity.HOME_ID:
                previousPage = MainActivity.HOME_ID;
                nextPage = MainActivity.LIST_ID;
                break;
            case MainActivity.LIST_ID:
                previousPage = MainActivity.HOME_ID;
                nextPage = MainActivity.GRAPH_ID;
                break;
            case MainActivity.GRAPH_ID:
                previousPage = MainActivity.LIST_ID;
                nextPage = MainActivity.GRAPH_ID;
                break;
        }
    }
}