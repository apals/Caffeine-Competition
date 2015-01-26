package edu.rosehulman.androidproject.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.fragments.GraphFragment;
import edu.rosehulman.androidproject.fragments.HomeContainerFragment;
import edu.rosehulman.androidproject.fragments.HomeFragment;
import edu.rosehulman.androidproject.fragments.ListContainerFragment;
import edu.rosehulman.androidproject.fragments.GraphContainerFragment;
import edu.rosehulman.androidproject.fragments.UserListFragment;

public class MainActivity extends ActionBarActivity {

    public static final int HOME_ID = 0;
    public static final int LIST_ID = 1;
    public static final int GRAPH_ID = 2;

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    

    //TODO: bottom fragment is added dynamically everywhere even though it's the same fragment on every page. should probably not be that way
    //TODO: add getInstance() method to all fragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);

        //TODO: if this is set to 1 (default), then when we're on page 3 (graph), page 1 (home) gets invisible and then recreated, which created page 1 twice next time. dno how to fix. this might kill performance
        mPager.setOffscreenPageLimit(2);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        titleIndicator.setRadius(12);
        titleIndicator.setFillColor(getResources().getColor(R.color.blue));
        titleIndicator.setViewPager(mPager);
    }

    public ViewPager getPager() {
        return mPager;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new HomeFragment();
                    //return HomeContainerFragment.getInstance();
                case 1:
                    return new UserListFragment();
                    //return ListContainerFragment.getInstance();
                case 2:
                    return new GraphFragment();
                    //GraphContainerFragment gcf = GraphContainerFragment.getInstance();
                    //return gcf;

            }
            return new ListContainerFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }


    }



}