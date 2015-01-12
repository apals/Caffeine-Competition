package edu.rosehulman.androidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by palssoa on 12/20/2014.
 */
public class BottomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_bottom, container, false);

        ((Button) rootView.findViewById(R.id.previous_page_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScreenSlidePagerActivity) getActivity()).getPager().setCurrentItem(0);
            }
        });

        ((Button) rootView.findViewById(R.id.next_page_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScreenSlidePagerActivity) getActivity()).getPager().setCurrentItem(1);
            }
        });


        return rootView;
    }




}
