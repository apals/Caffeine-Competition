package edu.rosehulman.androidproject.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.UserUtils;
import edu.rosehulman.androidproject.fragments.GraphFragment;
import edu.rosehulman.androidproject.fragments.HomeFragment;
import edu.rosehulman.androidproject.fragments.UserListFragment;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

public class MainActivity extends ActionBarActivity {

     /* TODO:
      * use holder pattern in listadapters
      *
      * Fix prepopulation av drink history points på drink remove och resume app
      *
      * check boxes color
      * fix graph y-resizing
      *
      * OPTIONAL:
      * save login
      * */

    private static final int USER_MODEL_CHILDREN = 6;
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    //TODO: Remove reference, use login activitys ref
    private Firebase mRef2;
    ChildEventListener childListener;

    //TODO: null checks on mUser when talking to firebase
    private AuthData mUser = null;

    private ArrayList<User> users;

    public static User USER;
    private double highestCaffeineLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up the action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setContentView(R.layout.activity_screen_slide);
        USER = (User) getIntent().getSerializableExtra(LoginActivity.KEY_EMAIL);
        users = (ArrayList<User>) getIntent().getSerializableExtra(LoginActivity.KEY_USERLIST);

        setHighestCaffeineLevel((double) getIntent().getSerializableExtra(LoginActivity.KEY_HIGHEST_CAFFEINE));

        Firebase.setAndroidContext(this);
        this.mRef2 = new Firebase(getString(R.string.url));

        childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!LoginActivity.LOGGED_IN) {
                    return;
                }
                if (dataSnapshot.getChildrenCount() < USER_MODEL_CHILDREN) {
                    return;
                }
                //System.out.println("ON CHILD ADDED: " + dataSnapshot.getValue());
                User user = createUserFromSnapShot(dataSnapshot);
                boolean exists = false;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getEmail().equals(user.getEmail())) {
                        exists = true;
                    }
                }
                if (!exists) {
                    users.add(user);
                }
                UserListFragment.getInstance().updateList();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (!LoginActivity.LOGGED_IN) {
                    return;
                }
                if (dataSnapshot.getChildrenCount() < USER_MODEL_CHILDREN) {
                    return;
                }
                System.out.println("ON CHILD CHANGED: " + dataSnapshot.getValue());
                User changedUser = createUserFromSnapShot(dataSnapshot);
                boolean exists = false;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getEmail().equals(changedUser.getEmail())) {
                        if (changedUser.getDrinkHistory() == null) {
                            users.get(i).setDrinkHistory(new ArrayList<Drink>());
                        } else {
                            // REPOPULATE POINT HISTORY ON REMOVAL
                            boolean repopulate = false;
                            if (users.get(i).getDrinkHistory().size() > changedUser.getDrinkHistory().size()) {
                                repopulate = true;
                            }
                            users.get(i).setDrinkHistory(changedUser.getDrinkHistory());
                            if (repopulate) {
                                users.get(i).removeAllPoints();
                                setHighestCaffeineLevel(UserUtils.prePopulatePoints(users.get(i), getHighestCaffeineLevel()));
                                GraphFragment.getInstance().exchangeSeries(users.get(i));
                            }
                        }
                        // TODO: Repopulate users entire point history
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    users.add(changedUser);
                }
                UserListFragment.getInstance().updateList();
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
        };

        this.mRef2.child("users").addChildEventListener(childListener);

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

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof HomeFragment) {
            HomeFragment.getInstance().startUpdating();
            return;
        }


        if (fragment instanceof GraphFragment) {
            GraphFragment.getInstance().startUpdating();
            return;
        }

        if (fragment instanceof UserListFragment) {
            UserListFragment.getInstance().startUpdating();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        LoginActivity.LOGGED_IN = false;
        HomeFragment.getInstance().stopUpdating();
        UserListFragment.getInstance().stopUpdating();
        GraphFragment.getInstance().stopUpdating();
        USER = null;
        mRef2.removeEventListener(childListener);
        childListener = null;
        mRef2 = null;
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HomeFragment.getInstance().stopUpdating();
        UserListFragment.getInstance().stopUpdating();
    }

    public ViewPager getPager() {
        return mPager;
    }

    public Firebase getFirebaseReference() {
        return mRef2;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() != 0) {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setHighestCaffeineLevel(double highestCaffeineLevel) {
        this.highestCaffeineLevel = highestCaffeineLevel;
    }

    public double getHighestCaffeineLevel() {
        return highestCaffeineLevel;
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
                default:
                    return null;
                //GraphContainerFragment gcf = GraphContainerFragment.getInstance();
                //return gcf;

            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public User createUserFromSnapShot(DataSnapshot dataSnapshot) {
        HashMap<String, Object> userData = ((HashMap<String, Object>) dataSnapshot.getValue());

        String username = (String) userData.get("username");
        String email = (String) userData.get("email");
        int weight = Integer.parseInt((String) userData.get("weight"));
        String gender = (String) userData.get("gender");

        String bmpBase64 = (String) userData.get("picture");

        ArrayList<Drink> userDrinkList = new ArrayList<>();
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            if (d.getKey().equals("drinkHistory")) {
                for (DataSnapshot child : d.getChildren()) {
                    HashMap<String, Object> drink = (HashMap<String, Object>) child.getValue();
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
        return new User(username, email, weight, gender, userDrinkList, bmpBase64);
    }
}