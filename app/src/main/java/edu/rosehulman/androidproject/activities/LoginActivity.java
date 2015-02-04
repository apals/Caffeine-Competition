package edu.rosehulman.androidproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.Drink;
import edu.rosehulman.androidproject.models.DrinkType;
import edu.rosehulman.androidproject.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActionBarActivity implements OnClickListener {

    public static final String KEY_EMAIL = "key_email";
    private static final String USERS_CHILD = "users";

    private Firebase mRef;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        this.mRef = new Firebase(getString(R.string.url));
        this.gson = new Gson();

        ((AutoCompleteTextView)findViewById(R.id.email)).setText("test@test.com");
        ((TextView)findViewById(R.id.password)).setText("1");

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_register_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
        String password = ((TextView) findViewById(R.id.password)).getText().toString();

        if (v.getId() == R.id.email_sign_in_button) {
            authorize(email, password);
        }
        else if (v.getId() == R.id.email_register_button) {
            register(email, password);
        }
    }

    private void authorize(final String email, final String password) {
        mRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        getUserData(email);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    private void register(final String email, final String password) {
        mRef.createUser(email, password, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        createUser(email, password);
                        authorize(email, password);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    public void acceptLogin(User user) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra(KEY_EMAIL, user);
        startActivity(i);
    }

    public void getUserData(final String email) {
        mRef.child(USERS_CHILD + "/" + cleanEmail(email)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acceptLogin(createUserFromSnapShot(dataSnapshot));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("COULDNT RETRIEVE USER DATA");
            }
        });
    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    public void createUser(String email, String password) {

        //TODO: create User.toMap??

        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/drinkHistory").setValue("");
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/username").setValue(cleanEmail(email));
    }

    public String cleanEmail(String email) {
        email = email.replace(".", "-");
        email = email.replace("@", "-");
        return email;
    }

    public User createUserFromSnapShot(DataSnapshot dataSnapshot) {
        HashMap<String, Object> userData = ((HashMap<String, Object>) dataSnapshot.getValue());

        String username = (String) userData.get("username");
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