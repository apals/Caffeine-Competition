package edu.rosehulman.androidproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActionBarActivity implements OnClickListener {

    public static final String KEY_USERNAME = "KEY_USERNAME";

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        this.mRef = new Firebase(getString(R.string.url));

        ((AutoCompleteTextView)findViewById(R.id.username)).setText("test@test.com");
        ((TextView)findViewById(R.id.password)).setText("1");

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_register_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = ((AutoCompleteTextView) findViewById(R.id.username)).getText().toString();
        String password = ((TextView) findViewById(R.id.password)).getText().toString();

        if (v.getId() == R.id.email_sign_in_button) {
            authorize(username, password);
        }
        else if (v.getId() == R.id.email_register_button) {
            register(username, password);
        }
    }

    private void authorize(final String username, final String password) {
        mRef.authWithPassword(username, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        acceptLogin();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    private void register(final String username, final String password) {
        System.out.println("BAAAAAAAAAJSAR PÅ MIG");
        mRef.createUser(username, password, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        createUser(username, password);
                        authorize(username, password);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    public void acceptLogin() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        User user = new User(((AutoCompleteTextView) findViewById(R.id.username)).getText().toString());
        i.putExtra(KEY_USERNAME, user);
        startActivity(i);
    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    public void createUser(String username, String password) {
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("username", username);
        sendMap.put("password", password);
        mRef.child("users").push().setValue(sendMap);
    }
}



