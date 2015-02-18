package edu.rosehulman.androidproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.UserUtils;
import edu.rosehulman.androidproject.fragments.HomeFragment;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActionBarActivity implements OnClickListener {

    private final static int REGISTER_REQUEST_CODE = 0;
    private static final int START_MAIN_REQUEST_CODE = 1;

    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_PASSWORD = "key_password";
    private static final String USERS_CHILD = "users";
    public static final String KEY_USERLIST = "user_list";
    public static final String KEY_HIGHEST_CAFFEINE = "highest_caffeine";
    public static boolean LOGGED_IN = false;
    private ArrayList<User> userList = new ArrayList<>();

    private Firebase mRef;
    private double highestCaffeineLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up the action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        this.mRef = new Firebase(getString(R.string.url));

        ((AutoCompleteTextView) findViewById(R.id.email)).setText("test@test.com");
        ((TextView) findViewById(R.id.password)).setText("1");

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        setRegisterButton();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.email_sign_in_button) {
            String email = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
            String password = ((TextView) findViewById(R.id.password)).getText().toString();
            authorize(email, password);
        }
    }

    private void authorize(final String email, final String password) {
        showProgressBar();
        mRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        getUserData(email);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        hideProgressBar();
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    public void acceptLogin(User user, ArrayList<User> userList) {
        LOGGED_IN = true;
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra(KEY_EMAIL, user);
        i.putExtra(KEY_USERLIST, userList);
        i.putExtra(KEY_HIGHEST_CAFFEINE, highestCaffeineLevel);
        hideProgressBar();
        startActivityForResult(i, START_MAIN_REQUEST_CODE);
    }

    public void getUserData(final String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!LOGGED_IN) {
                    getUserlistData(createUserFromSnapShot(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("COULDNT RETRIEVE USER DATA");
            }
        };
        mRef.child(USERS_CHILD + "/" + cleanEmail(email)).addValueEventListener(valueEventListener);
    }

    public void getUserlistData(final User user) {
        ValueEventListener userListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!LOGGED_IN) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        userList.add(createUserFromSnapShot(data));
                    }

                    acceptLogin(user, userList);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("COULDNT RETRIEVE USER DATA");
            }
        };

        mRef.child(USERS_CHILD + "/").addValueEventListener(userListListener);
    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    public String cleanEmail(String email) {
        email = email.replace(".", "-");
        email = email.replace("@", "-");
        return email;
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
        User user = new User(username, email, weight, gender, userDrinkList, bmpBase64, userList.size());
        if (user.getCaffeineLevel() > 0) {
            setHighestCaffeineLevel(UserUtils.prePopulatePoints(user, getHighestCaffeineLevel()));
        }
        return user;
    }

    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, REGISTER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REGISTER_REQUEST_CODE) {
            String email = data.getStringExtra(KEY_EMAIL);
            String password = data.getStringExtra(KEY_PASSWORD);


            ((AutoCompleteTextView) findViewById(R.id.email)).setText(email);
            ((TextView) findViewById(R.id.password)).setText(password);
            authorize(email, password);
        } else if (requestCode == START_MAIN_REQUEST_CODE) {
            mRef = new Firebase(getString(R.string.url));
        }
    }

    public void setRegisterButton() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        String regularText = "Not already a user? ";
        String clickableText = "Register!";
        sb.append(regularText);
        sb.append(clickableText);
        sb.setSpan(new MyClickableSpan(),
                sb.length()-clickableText.length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView tv = (TextView) findViewById(R.id.login_register_clickable);
        tv.setText(sb);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    class MyClickableSpan extends ClickableSpan{
        @Override
        public void onClick(View textView) {
            register();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.blue));
            ds.setUnderlineText(false);
        }
    }

    public void showProgressBar() {
        //TODO: disable all buttons
        ((ProgressBar) findViewById(R.id.login_progressbar)).setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        //TODO: enable all buttons
        ((ProgressBar) findViewById(R.id.login_progressbar)).setVisibility(View.INVISIBLE);
    }

    public double getHighestCaffeineLevel() {
        return highestCaffeineLevel;
    }

    public void setHighestCaffeineLevel(double highestCaffeineLevel) {
        this.highestCaffeineLevel = highestCaffeineLevel;
    }
}