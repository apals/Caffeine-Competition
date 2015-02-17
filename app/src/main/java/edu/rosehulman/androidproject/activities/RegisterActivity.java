package edu.rosehulman.androidproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import edu.rosehulman.androidproject.PhotoUtils;
import edu.rosehulman.androidproject.R;

/**
 * A register screen that offers registration
 */

public class RegisterActivity extends ActionBarActivity {
    private static final String USERS_CHILD = "users";
    private static final int TAKE_PHOTO_ACTIVITY_REQUEST = 1;

    private Bitmap mBitmap;

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up the action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        setContentView(R.layout.activity_register);

        final Switch switchman = ((Switch) findViewById(R.id.register_switch));

        Firebase.setAndroidContext(this);
        this.mRef = new Firebase(getString(R.string.url));

        findViewById(R.id.email_register_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.email_register_button) {
                    String email = ((AutoCompleteTextView) findViewById(R.id.register_email)).getText().toString();
                    String username = ((TextView) findViewById(R.id.register_username)).getText().toString();
                    String weight = ((TextView) findViewById(R.id.register_weight)).getText().toString();
                    String gender = switchman.isChecked() ? getString(R.string.female) : getString(R.string.male);
                    String password = ((TextView) findViewById(R.id.register_password)).getText().toString();
                    String repeatPassword = ((TextView) findViewById(R.id.register_repeat_password)).getText().toString();
                    //Bitmap bitmap = ((BitmapDrawable) ((ImageView) findViewById(R.id.mImageView)).getDrawable()).getBitmap();

                    if (password.equals(repeatPassword)) {
                        if (email.length() > 0 && username.length() > 0 && weight.length() > 0) {
                            register(email, username, weight, gender, password, mBitmap);
                        } else {
                            toast(getString(R.string.not_all_fields_filled_out_message));
                        }
                    } else {
                        toast(getString(R.string.password_does_not_match_message));
                    }
                }
            }
        });
    }

    private void register(final String email, final String username, final String weight, final String gender, final String password, final Bitmap bitmap) {
        showProgressBar();
        mRef.createUser(email, password, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        createUser(email, username, weight, gender, bitmap);
                        goBackToLogin(email, password);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        hideProgressBar();
                        toast(firebaseError.getMessage());
                    }
                }
        );
    }

    private void goBackToLogin(String email, String password) {
        Intent i = new Intent();
        i.putExtra(LoginActivity.KEY_EMAIL, email);
        i.putExtra(LoginActivity.KEY_PASSWORD, password);
        setResult(RESULT_OK, i);
        hideProgressBar();
        finish();
    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void createUser(String email, String username, String weight, String gender, Bitmap bitmap) {
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/drinkHistory").setValue("");
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/email").setValue(cleanEmail(email));
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/username").setValue(username);
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/weight").setValue(weight);
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/gender").setValue(gender);
        mRef.child(USERS_CHILD + "/" + cleanEmail(email) + "/picture").setValue(encodeTobase64(bitmap));

    }

    public String cleanEmail(String email) {
        email = email.replace(".", "-");
        email = email.replace("@", "-");
        return email;
    }

    public void showProgressBar() {
        ((ProgressBar) findViewById(R.id.register_progressbar)).setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        ((ProgressBar) findViewById(R.id.register_progressbar)).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO_ACTIVITY_REQUEST) {
            Log.d("LOG", "back from taking a photo");
            // TODO: Get and show the bitmap
            mBitmap = BitmapFactory.decodeFile(PhotoUtils.getPhotoPath());
            ((ImageView) findViewById(R.id.mImageView)).setImageBitmap(mBitmap);

        }
    }

    public void takePhoto(View v) {
        Log.d("LOG", "takePhoto() started");

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = PhotoUtils.getOutputMediaUri(getString(R.string.app_name));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, TAKE_PHOTO_ACTIVITY_REQUEST);
        PhotoUtils.setPhotoPath(uri.getPath());
    }

    public String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageEncoded;
    }
}