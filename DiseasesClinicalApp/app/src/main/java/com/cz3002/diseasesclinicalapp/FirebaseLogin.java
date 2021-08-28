package com.cz3002.diseasesclinicalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FirebaseLogin extends AppCompatActivity {

    private static int AUTH_REQUEST_CODE = 7192;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener!=null)firebaseAuth.removeAuthStateListener(listener);
        super.onStop();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(), // Log in using Google
                new AuthUI.IdpConfig.EmailBuilder().build(), // Log in using Email
                new AuthUI.IdpConfig.FacebookBuilder().build(), // Log in using Facebook
                new AuthUI.IdpConfig.PhoneBuilder().build() //Log in using Phone
        );

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Get user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Toast.makeText(FirebaseLogin.this, "There is an account logged in: " + user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), AUTH_REQUEST_CODE);
                }}
        };
        firebaseAuth.addAuthStateListener(listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK

    }
}