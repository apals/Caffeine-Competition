package edu.rosehulman.androidproject.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.fragments.GraphFragment;
import edu.rosehulman.androidproject.fragments.HomeFragment;
import edu.rosehulman.androidproject.fragments.ListContainerFragment;
import edu.rosehulman.androidproject.fragments.UserListFragment;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

public class MainActivity extends ActionBarActivity {

    public static final int HOME_ID = 0;
    public static final int LIST_ID = 1;
    public static final int GRAPH_ID = 2;

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    //TODO: Remove reference, use login activitys ref
    private Firebase mRef;

    //TODO: null checks on mUser when talking to firebase
    private AuthData mUser = null;

    private ArrayList<User> users;

    public static User USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        USER = (User) getIntent().getSerializableExtra(LoginActivity.KEY_EMAIL);
        users = new ArrayList<>();

        Firebase.setAndroidContext(this);
        this.mRef = new Firebase(getString(R.string.url));

        this.mRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = createUserFromSnapShot(dataSnapshot);
                if (user.getUsername() == null || user.getUsername().equals(USER.getUsername())) {
                    return;
                }
                users.add(user);
                UserListFragment.getInstance().updateList();
//                GraphFragment.getInstance().updateGraph();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User changedUser = createUserFromSnapShot(dataSnapshot);
                if (!changedUser.getUsername().equals(USER.getUsername())) {
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getUsername().equals(changedUser.getUsername())) {
                            users.remove(i);
                            break;
                        }
                    }
                    users.add(changedUser);
                    UserListFragment.getInstance().updateList();
//                GraphFragment.getInstance().updateGraph();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        this.mRef.authAnonymously(new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                MainActivity.this.mUser = authData;
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                MainActivity.this.mUser = null;
            }
        });
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);

        //TODO: if this is set to 1 (default), then when we're on page 3 (graph), page 1 (home) gets invisible and then recreated, which created page 1 twice next time. dno how to fix. this might kill performance
        mPager.setOffscreenPageLimit(2);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


        CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        titleIndicator.setRadius(12);
        titleIndicator.setFillColor(getResources().getColor(R.color.blue));
        titleIndicator.setViewPager(mPager);
    }

    public ViewPager getPager() {
        return mPager;
    }

    public Firebase getFirebaseReference() {
        return mRef;
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

    public ArrayList<User> getUsers() {
        return users;
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
            switch (position) {
                case 0:
                    return HomeFragment.getInstance();
                //return HomeContainerFragment.getInstance();
                case 1:
                    return UserListFragment.getInstance();
                //return ListContainerFragment.getInstance();
                case 2:
                    return GraphFragment.getInstance();
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

    public User createUserFromSnapShot(DataSnapshot dataSnapshot) {
        HashMap<String, Object> userData = ((HashMap<String, Object>) dataSnapshot.getValue());

        String username = (String) userData.get("username");
        System.out.println("CREATING USER WITH USERNAME: " + username);
        ArrayList<Drink> userDrinkList = new ArrayList<>();
        for(DataSnapshot d : dataSnapshot.getChildren()) {
            if (d.getKey().equals("drinkHistory")) {
                for(DataSnapshot child: d.getChildren()) {
                    HashMap<String, Object> drink = (HashMap<String, Object>) child.getValue();
                    double a = (double) drink.get("remainingCaffeine");
                    Date date = new Date((long) drink.get("dateTime"));

                    HashMap drinkType = (HashMap) drink.get("drinkType");
                    double caffeineAmount = (double) drinkType.get("caffeineAmount");
                    String drinkName = (String) drinkType.get("drinkName");
                    DrinkType dr = new DrinkType(drinkName, caffeineAmount);
                    Drink newDrink = new Drink(dr, date);
                    userDrinkList.add(newDrink);
                }
            }
        }
        return new User(username, userDrinkList);
    }
}