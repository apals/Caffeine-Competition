package edu.rosehulman.androidproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;

import edu.rosehulman.androidproject.R;
import edu.rosehulman.androidproject.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActionBarActivity {

    public static final String KEY_USERNAME = "KEY_USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                User user = new User(((AutoCompleteTextView) findViewById(R.id.username)).getText().toString());
                i.putExtra(KEY_USERNAME, user);
                startActivity(i);
            }
        });

    }


}



